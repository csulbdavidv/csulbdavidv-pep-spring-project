package com.example.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Message;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface MessageRepository extends JpaRepository<Message, Long>{

    @Query("FROM Message WHERE messageId = :messageId")
    Message getMessageGivenMessageId(@Param("messageId") int messageId);

    @Modifying
    @Query("DELETE FROM Message WHERE messageId = :messageId")
    void deleteMessageGivenMessageId(@Param("messageId") int messageId);

    @Modifying
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    void updateMessage(@Param("messageId") int messageId, @Param("messageText") String messageText);

    @Query("SELECT m FROM Message m INNER JOIN Account a on a.accountId = m.postedBy WHERE a.accountId = :accountId")
    List<Message> getMessagesForUserGivenAccountId(@Param("accountId") int accountId);

}
