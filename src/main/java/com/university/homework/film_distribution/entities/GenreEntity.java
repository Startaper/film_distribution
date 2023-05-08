package com.university.homework.film_distribution.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "genres")
public class GenreEntity extends BasicEntity {

    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    @ToString.Exclude
    private List<FilmEntity> films;

}
