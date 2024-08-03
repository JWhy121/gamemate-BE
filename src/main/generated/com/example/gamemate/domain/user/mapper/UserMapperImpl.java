package com.example.gamemate.domain.user.mapper;

import com.example.gamemate.domain.user.dto.MyPageResponseDTO;
import com.example.gamemate.domain.user.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-02T15:15:33+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public MyPageResponseDTO userToMyPageDto(User user) {
        if ( user == null ) {
            return null;
        }

        MyPageResponseDTO myPageResponseDTO = new MyPageResponseDTO();

        myPageResponseDTO.setId( user.getId() );
        myPageResponseDTO.setUsername( user.getUsername() );
        myPageResponseDTO.setNickname( user.getNickname() );

        return myPageResponseDTO;
    }
}
