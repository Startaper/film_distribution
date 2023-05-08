package com.university.homework.film_distribution.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@ToString
public class BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    //  System fields
    @Column(name = "date_of_last_updated", nullable = false)
    private Date dateOfLastUpdated;

    @Column(name = "date_of_creation", nullable = false, updatable = false)
    private Date dateOfCreation;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

}
