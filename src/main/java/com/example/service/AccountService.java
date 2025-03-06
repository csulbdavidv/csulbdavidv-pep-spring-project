package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {
    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account loginUser(Account account){
        Account loginUser = accountRepository.loginUser(account.getUsername(), account.getPassword());
        if(loginUser == null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return loginUser;
    }
    

    public Account getUserByUserName(String username){
        Account foundUser = accountRepository.findUserByUserName(username);
        if(foundUser != null){
            return foundUser;
        }
        return null;
    }

    public Account registerUser(Account account){
        if(account.getUsername().equals("") || account.getPassword().length() < 4){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password length or empty username");
        }
        if(getUserByUserName(account.getUsername()) == null){
            return accountRepository.save(account);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
    }
}
