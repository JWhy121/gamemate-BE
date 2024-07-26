package com.example.gamemate.domain.user.controller;

import com.example.gamemate.domain.user.User;
import com.example.gamemate.domain.user.dto.JoinRequest;
import com.example.gamemate.domain.user.dto.LoginRequest;
import com.example.gamemate.global.auth.JwtTokenUtil;
import com.example.gamemate.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class JwtLoginApiController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest) {

        //eamil 중복 체크
        if(userService.checkEmailDuplicate(joinRequest.getEmail())) {
            return "이미 사용중인 이메일입니다.";
        }

        //닉네임 중복 체크
        if(userService.checkNicknameDuplicate(joinRequest.getNickname())) {
            return "이미 사용중인 닉네임입니다.";
        }

        //password와 passwordCheck가 같은지 체크
        if(!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            return "비밀번호가 일치하지 않습니다.";
        }

        userService.join2(joinRequest);
        return "회원가입 성공";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

        User user = userService.login(loginRequest);

        //이메일이나 비밀번호가 틀린 경우 global error return
        if(user == null) {
            return "이메일 또는 비밀번호가 틀렸습니다.";
        }

        //로그인 성공 시 Jwt Token 발급
        String secretKey = "my-secret-key-123123";
        long expireTimeMs = 1000 * 60 * 60; //Token 유효 시간 = 60분

        String jwtToken = JwtTokenUtil.createToken(user.getEmail(), secretKey, expireTimeMs);

        return jwtToken;
    }

    @GetMapping("/info")
    public String userInfo(Authentication auth) {
        User loginUser = userService.getLoginUserByEmail(auth.getName());

        return String.format("email : %s\nnickname : %s\nrole : %s",
            loginUser.getEmail(), loginUser.getNickname(), loginUser.getRole().name());
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }

}
