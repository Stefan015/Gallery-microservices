package com.rzk.authservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class UserRoleId implements Serializable {
    private static final long serialVersionUID = 7127742021125315825L;
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;


}