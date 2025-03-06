package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
        private final AccountService accountService;
        private final MessageService messageService;

        @Autowired
        public SocialMediaController(AccountService accountService, MessageService messageService) {
            this.accountService = accountService;
            this.messageService = messageService;
        }
        @PostMapping("/register")
        public ResponseEntity<Account> registerUser(@RequestBody Account account) {
            try {
                Account registeredAccount = accountService.registerUser(account);
                return ResponseEntity.ok(registeredAccount); // 200 OK
            } catch (Exception e) {
                if (e.getMessage().contains("Username already exists")) {
                    return ResponseEntity.status(409).body(null);
                }
                return ResponseEntity.status(400).body(null);
            }
        }

        @PostMapping("/login")
        public ResponseEntity<Account> userLogin(@RequestBody Account account) {
            try {
                Account verifyLogin = accountService.loginUser(account);
                return ResponseEntity.ok(verifyLogin);
            } catch (Exception e) {
                return ResponseEntity.status(401).body(null);
            }
        }

        @PostMapping("/messages")
        public ResponseEntity<Message> createMessage(@RequestBody Message message) {
            try {
                Message createMessage = messageService.createMessage(message);
                return ResponseEntity.ok(createMessage);
            } catch (Exception e) {
                return ResponseEntity.status(400).body(null);
            }
        }

        @GetMapping("/messages")
        public ResponseEntity<List<Message>> getAllMessages() {
            return ResponseEntity.ok(messageService.getAllMessages());
        }

        @GetMapping("/messages/{message_id}")
        public ResponseEntity<Message> getMessageGivenMessageId(@PathVariable int message_id) {
            return ResponseEntity.ok(messageService.getMessageGivenMessageId(message_id));
        }

        @DeleteMapping("/messages/{message_id}")
        public ResponseEntity<Integer> deleteMessageGivenMessageId(@PathVariable int message_id) {
            int rowsDeleted = messageService.deleteMessageGivenMessageId(message_id);
            if (rowsDeleted == 0) {
                return ResponseEntity.ok().body(null);
            }
            return ResponseEntity.ok().body(rowsDeleted);
        }

        @PatchMapping("/messages/{message_id}")
        public ResponseEntity<Integer> updateMessage(@PathVariable int message_id, @RequestBody Message message) {
            try {
                messageService.updateMessage(message_id, message.getMessageText());
                return ResponseEntity.ok().body(1);
            } catch (Exception e) {
                return ResponseEntity.status(400).body(null);
            }
        }

        @GetMapping("/accounts/{account_id}/messages")
        public ResponseEntity<List<Message>> getMessagesForUserGivenAccountId(@PathVariable int account_id){
            return ResponseEntity.ok(messageService.getMessagesForUserGivenAccountId(account_id));
        }
}
