package com.example.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

    @Query("FROM Account WHERE username = :username")
    Account findUserByUserName(@Param("username") String username);

    @Query("FROM Account WHERE username = :username AND password = :password")
    Account loginUser(@Param("username") String username, @Param("password") String password);

    @Query("FROM Account WHERE accountId = :accountId")
    Account findUserById(@Param("accountId") int accountId);
}
