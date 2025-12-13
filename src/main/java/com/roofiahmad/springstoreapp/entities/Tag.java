package com.roofiahmad.springstoreapp.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@ToString
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER)
    @ToString.Exclude
    private Set<User> users = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }
}
