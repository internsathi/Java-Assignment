package com.example.intern.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @Column(nullable = false)
    private String name;
    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
//    @NotEmpty
//    @Size(min = 6, max = 40, message = "Password should be between 6 and 40 characters")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

}
