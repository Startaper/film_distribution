package com.university.homework.film_distribution.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.entities.GenreEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre {

    private long id;
    private String name;
    private boolean isActive;
    private String films;

    public static Genre toGenre(GenreEntity genreDTO) {
        return Genre.builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .isActive(genreDTO.isActive())
                .films(toStringFilms(genreDTO.getFilms()))
                .build();
    }

    private static Genre toGenreByFilm(GenreEntity genreDTO) {
        return Genre.builder()
                .id(genreDTO.getId())
                .name(genreDTO.getName())
                .isActive(genreDTO.isActive())
                .build();
    }

    public static List<Genre> toListGenres(boolean b, List<GenreEntity> genreEntities) {
        return genreEntities
                .stream()
                .map(genre -> b ? toGenreByFilm(genre) : toGenre(genre))
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

