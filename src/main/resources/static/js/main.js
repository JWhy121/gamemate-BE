'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageFormChat001 = document.querySelector('#messageForm-chat-001');
var messageFormChat002 = document.querySelector('#messageForm-chat-002');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

document.getElementById('subscribe-chat-001').addEventListener('click', function() {
    subscribeAndAddUser('chat-001');
    const button = document.querySelector('#btn-chat-001');
    button.disabled = false;
});

// 유저네임을 세션스토리지에 저장.
function saveUserNameTosessionStorage(event) {
    event.preventDefault();
    username = document.querySelector('#name').value.trim();
    if(username) {
        sessionStorage.setItem("username", username);
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
    }
}

// 구독하는 메서드
function subscribeAndAddUser(chatUuid) {
//1. SocketJS로 /chat 엔드포인트를 지정한다.
//2. stompClient로 연결을 위한 핸드쉐이크 한다.
//3. 연결에 성공하면 /topic/message로 구독을 요청한다.

    // /ws 경로로 새로운 SockJS 소켓을 생성
    // SockJS는 웹소켓을 지원하지 않는 브라우저에서도 동작할 수 있도록 도와주는 라이브러리
    var socket = new SockJS('/ws');

    // 생성된 SockJS 소켓을 사용하여 STOMP 클라이언트를 생성
    // STOMP는 메시징 프로토콜로, 웹소켓을 통해 메시지를 주고받을 수 있게 함
    stompClient = Stomp.over(socket);

    // stompClient.connect 메소드를 사용하여 서버에 연결
    // login 옵션으로 세션 스토리지에서 가져온 유저네임을 전달
    // 연결이 성공하면 onConnected 콜백 함수가 호출되며, 연결이 실패하면 onError 콜백 함수가 호출
    // login 메소드로 인증을 요청하는데 백에서 이 인증을 받는 부분을 구현하여 인증기능 추가 가능.
    stompClient.connect({
        login: sessionStorage.getItem("username")
    }, function () {
        onConnected(chatUuid);
    }, onError);

    let connectingElement = document.querySelector(`.connecting-${chatUuid}`);
    connectingElement.classList.add('hidden');
}

// chatUuid 채팅방을 구독, 메시지가 수신될 떄 호출될 콜백함수  onMessageReceived 지정.
function onConnected(chatUuid) {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/chat/'+chatUuid, onMessageReceived);
}

function sendMessage(event) {
    let chatUuid = event.currentTarget.name;
    let messageInput = document.querySelector(`#message-${chatUuid}`);
    // 입력된 메시지 내용을 가져오고, trim() 메서드를 사용하여 앞뒤 공백을 제거합니다.
    let messageContent = messageInput.value.trim();

    // 메시지 내용이 비어 있지 않고(messageContent가 존재) stompClient가 유효한 경우에만 메시지를 전송
    if(messageContent && stompClient) {
        let chatMessage = {
            sender: sessionStorage.getItem("username"),
            content: messageInput.value,
            chatUuid: chatUuid,
            type: 'CHAT'
        };

        // stompClient.send 메서드를 사용하여 서버로 메시지를 전송. 이때 메시지는 JSON 형식으로 문자열화(JSON.stringify)
        // 메시지는 /app/message/send/${chatUuid} 경로로 전송
        stompClient.send("/app/message/send/"+chatUuid, {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();
}

function onMessageReceived(payload) {

    // 수신된 메시지 json 파싱
    let message = JSON.parse(payload.body);
    console.log(message.chatUuid);
    console.log("this is message");
    console.log(message);

    // 새로운 리스트 아이템(li) 요소를 생성하여 메시지를 담을 컨테이너로 사용
    let messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
        messageElement.classList.add('chat-message');

        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);

        messageElement.appendChild(avatarElement);

        let usernameElement = document.createElement('span');
        let usernameText = document.createTextNode(message.sender);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    let textElement = document.createElement('p');
    let messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);
    let chatUuid = message.chatUuid;
    let messageArea = document.querySelector(`#messageArea-${chatUuid}`);
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function onError(error) {
    // connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    // connectingElement.style.color = 'red';
    // sessionStorage.removeItem("username");
}

function getAvatarColor(messageSender) {
    let hash = 0;
    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    let index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', saveUserNameTosessionStorage, true)
messageFormChat001.addEventListener('submit', sendMessage, true)
messageFormChat002.addEventListener('submit', sendMessage, true)