package com.university.homework.film_distribution.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "actor")
public class ActorEntity extends BasicEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @Column(name = "place_of_berth", nullable = false)
    private String placeOfBirth;

    @Column(name = "career", nullable = false)
    private String career;

    @Column(name = "number_of_films")
    private int numberOfFilms;

    @ManyToMany(mappedBy = "actors")
    private List<FilmEntity> films;

}
