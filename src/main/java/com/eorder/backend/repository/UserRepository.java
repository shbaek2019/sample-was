package com.eorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eorder.backend.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //
    User getByUid(String uid);
    User getByEmail(String email);
}
