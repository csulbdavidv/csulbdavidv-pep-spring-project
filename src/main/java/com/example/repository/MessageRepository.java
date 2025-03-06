package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface MessageRepository extends JpaRepository<Message, Long>{

    @Query("FROM Message WHERE messageId = :messageId")
    Message getMessageGivenMessageId(@Param("messageId") int messageId);

    @Modifying
    @Query("DELETE FROM Message WHERE messageId = :messageId")
    void deleteMessageGivenMessageId(@Param("messageId") int messageId);
}
