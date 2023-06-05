package com.university.homework.film_distribution.entities;

import com.university.homework.film_distribution.models.TypeOfFilm;
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
@Table(name = "films")
public class FilmEntity extends BasicEntity {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "anons", nullable = false)
    private String anons;

    @Column(name = "description", length = 50000)
    private String description;

    @Column(name = "image_url", nullable = false, length = 50000)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_film", nullable = false, updatable = false)
    private TypeOfFilm typeOfFilm;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "age_restriction", nullable = false)
    private int ageRestriction;

    @Column(name = "country_of_production", nullable = false)
    private String countryOfProduction;

    @Column(name = "rating", nullable = false)
    private float rating;

    @Column(name = "number_of_reviews", nullable = false)
    private long numberOfReviews;

    @Column(name = "release_date", nullable = false, updatable = false)
    private Date dateRelease;

    @Column(name = "duration_of_the_film", nullable = false)
    private long durationOfTheFilm;

    @Column(name = "number_of_episodes")
    private int numberOfEpisodes;

    @Column(name = "number_of_seasons")
    private int numberOfSeasons;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "films_genres",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<GenreEntity> genres;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "films_actors",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private List<ActorEntity> actors;

}
