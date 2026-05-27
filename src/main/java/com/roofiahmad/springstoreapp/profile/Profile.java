package com.roofiahmad.springstoreapp.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roofiahmad.springstoreapp.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name="profiles")
public class Profile {
    @Id
    private long id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
   private LocalDate dateOfBirth;

    @Column(name = "loyalty_points")
    private int loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    @ToString.Exclude
    @JsonIgnore
    private User user;
}
