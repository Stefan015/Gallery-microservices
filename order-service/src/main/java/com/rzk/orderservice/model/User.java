package com.rzk.orderservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", nullable = false, length = 50)
    private String username;


}