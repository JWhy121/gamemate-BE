package com.example.gamemate.domain.user.service;

import com.example.gamemate.domain.user.User;
import com.example.gamemate.user.dto.JoinRequest;
import com.example.gamemate.user.dto.LoginRequest;
import com.example.gamemate.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // PasswordEncoder 인터페이스로 주입받기
    private final PasswordEncoder passwordEncoder;

    //Spring Security를 사용한 로그인 구현 시 사용
    private final BCryptPasswordEncoder encoder;

    /**
     * email 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkEmailDuplicate(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * nickname 중복 체크
     * 회원가입 기능 구현 시 사용
     * 중복되면 true return
     */
    public boolean checkNicknameDuplicate(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    /**
     * 회원가입 기능 1
     * 화면에서 JoinRequest(email, password, nickname)을 입력받아 User로 변환 후 저장
     * email, nickname 중복 체크는 Controller에서 진행 => 에러 메세지 출력을 위해
     */
    public void join(JoinRequest req) {
        userRepository.save(req.toEntity());
    }

    /**
     * 회원가입 기능 2
     * 화면에서 JoinRequest(email, password, nickname)를 입력받아 User로 변환 후 저장
     * 회원가입 1과는 달리 비밀번호를 암호화해서 저장
     * email, nickname 중복 체크는 Controller에서 저장 => 에러 메세지 출력을 위해
     */
    public void join2(JoinRequest req) {
        userRepository.save(req.toEntity(passwordEncoder.encode(req.getPassword())));
    }

    /**
     * 로그인 기능
     * 화면에서 LoginRequest(email, password)를 입력받아 email과 password가 일치하면 User return
     * eamil이 존재하지 않거나 password가 일치하지 않으면 null return
     */
    public User login(LoginRequest req) {
        Optional<User> optionalUser = userRepository.findByEmail(req.getEmail());

        // email과 일치하는 User가 없으면 null return
        if(optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        // 입력된 비밀번호와 저장된 비밀번호를 비교
        if(!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return null;
        }
        return user;
    }

    /**
     * userId(Long)를 입력받아 User를 return해주는 기능
     * 인증, 인가 시 사용
     * userId가 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
     * userId로 찾아온 User가 존재하면 User return
     */
    public User getLoginUserById(Long userId) {
        if(userId == null) return null;

        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    /**
     * email(String)를 입력받아 User를 return 해주는 기능
     * 인증, 인가 시 사용
     * email이 null이거나(로그인 X) userId로 찾아온 User가 없으면 null return
     * email로 찾아온 User가 존재하면 User return
     */
    public User getLoginUserByEmail(String email) {
        if(email == null) return null;

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}
