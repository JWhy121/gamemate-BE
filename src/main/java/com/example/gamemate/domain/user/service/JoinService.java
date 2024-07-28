package com.example.gamemate.domain.user.service;

import com.example.gamemate.domain.user.User;
import com.example.gamemate.domain.user.User.RoleType;
import com.example.gamemate.domain.user.dto.JoinDTO;
import com.example.gamemate.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

    }

    public void joinProcess(JoinDTO joinDTO) {

        String email = joinDTO.getEmail();
        String password = joinDTO.getPassword();
        String nickname = joinDTO.getNickname();

        Boolean isExist = userRepository.existsByEmail(email);

        if(isExist) {

            return;

        }

        User data = new User();

        data.setEmail(email);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setNickname(nickname);

        userRepository.save(data);

    }
}
