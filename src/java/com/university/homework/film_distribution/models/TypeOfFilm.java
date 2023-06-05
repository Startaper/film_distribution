package com.university.homework.film_distribution.models;

public enum TypeOfFilm {

    MOVIE("Фильм"),

    SERIALFILM("Сериал");

    private final String title;

    TypeOfFilm(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "TypeOfFilm{" +
                "title='" + title + '\'' +
                '}';
    }
}
