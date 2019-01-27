package com.bookstore.security;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roleId;
    private String roleName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
    private Set<UserRole> userRoles = new HashSet<>();

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Role(String roleName, Set<UserRole> userRoles) {
        this.roleName = roleName;
        this.userRoles = userRoles;
    }
}
