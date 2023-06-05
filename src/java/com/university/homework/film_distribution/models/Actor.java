package com.university.homework.film_distribution.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.university.homework.film_distribution.entities.ActorEntity;
import com.university.homework.film_distribution.entities.FilmEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Actor {

    private  long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthDate;
    private String placeOfBirth;
    private String career;
    private int numberOfFilms;
    private String films;
    private boolean isActive;

    public static Actor toActor(ActorEntity actorDTO) {
        return Actor.builder()
                .id(actorDTO.getId())
                .firstName(actorDTO.getFirstName())
                .lastName(actorDTO.getLastName())
                .middleName(actorDTO.getMiddleName())
                .birthDate(actorDTO.getBirthDate())
                .placeOfBirth(actorDTO.getPlaceOfBirth())
                .career(actorDTO.getCareer())
                .numberOfFilms(actorDTO.getNumberOfFilms())
                .films(toStringFilms(actorDTO.getFilms()))
                .isActive(actorDTO.isActive())
                .build();
    }

    private static Actor toActorByFilm(ActorEntity actorDTO) {
        if (actorDTO.isActive()) {
            return Actor.builder()
                    .id(actorDTO.getId())
                    .firstName(actorDTO.getFirstName())
                    .lastName(actorDTO.getLastName())
                    .middleName(actorDTO.getMiddleName())
                    .birthDate(actorDTO.getBirthDate())
                    .placeOfBirth(actorDTO.getPlaceOfBirth())
                    .career(actorDTO.getCareer())
                    .numberOfFilms(actorDTO.getNumberOfFilms())
                    .isActive(actorDTO.isActive())
                    .build();
        } else {
            return null;
        }
    }

    public static List<Actor> toListActors(boolean b, List<ActorEntity> actors) {
        return actors
                .stream()
                .map(actor -> b ? toActorByFilm(actor) : toActor(actor))
                .collect(Collectors.toList());
    }

    private static String toStringFilms(List<FilmEntity> films) {
        if (films == null) {
            return "";
        } else {
            return films.stream()
                    .map(FilmEntity::getName)
                    .collect(Collectors.joining(", "));
        }
    }

}
