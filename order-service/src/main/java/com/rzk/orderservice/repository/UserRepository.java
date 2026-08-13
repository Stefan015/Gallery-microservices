package com.rzk.orderservice.repository;

import com.rzk.orderservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}