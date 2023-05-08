package com.university.homework.film_distribution.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.university.homework.film_distribution.entities.ActorEntity;
import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.entities.GenreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Film {

    private long id;
    private String name;
    private String anons;
    private String description;
    private String imageUrl;
    private String typeOfFilm;
    private int price;
    private int ageRestriction;
    private String countryOfProduction;
    private float rating;
    private long numberOfReviews;
    private Date dateRelease;
    private long durationOfTheFilm;
    private int numberOfSeasons;
    private int numberOfEpisodes;
    private List<Genre> genres;
    private List<Actor> actors;

    public static Film toFilm(FilmEntity film) {
        List<GenreEntity> genreEntities = film.getGenres();
        if (genreEntities == null) {
            genreEntities = new ArrayList<>();
        }
        List<ActorEntity> actorEntities = film.getActors();
        if (actorEntities == null) {
            actorEntities = new ArrayList<>();
        }
        return Film.builder()
                .id(film.getId())
                .name(film.getName())
                .anons(film.getAnons())
                .description(film.getDescription())
                .imageUrl(film.getImageUrl())
                .typeOfFilm(film.getTypeOfFilm().getTitle())
                .price(film.getPrice())
                .ageRestriction(film.getAgeRestriction())
                .countryOfProduction(film.getCountryOfProduction())
                .rating(film.getRating())
                .numberOfReviews(film.getNumberOfReviews())
                .dateRelease(film.getDateRelease())
                .durationOfTheFilm(film.getDurationOfTheFilm())
                .numberOfSeasons(film.getNumberOfSeasons())
                .numberOfEpisodes(film.getNumberOfEpisodes())
                .genres(Genre.toListGenres(true, genreEntities))
                .actors(Actor.toListActors(true, actorEntities))
                .build();
    }

    public static List<Film> toListFilms(List<FilmEntity> filmEntities) {
        return filmEntities
                .stream()
                .map(Film::toFilm)
                .collect(Collectors.toList());
    }

}
