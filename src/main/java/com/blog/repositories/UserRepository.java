package com.blog.repositories;


import com.blog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);//Spring Boot will automatically write the SQL Query for this method and get the record for email id.
    Optional<User> findByUsernameOrEmail(String username,String email);// SQL Query is select * from user where email = "" or username = "".
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String Username);
    Boolean existsByEmail(String email);


}
