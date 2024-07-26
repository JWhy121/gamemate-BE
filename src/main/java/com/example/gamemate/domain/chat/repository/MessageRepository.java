package com.example.gamemate.domain.chat.repository;

import com.example.gamemate.domain.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}
