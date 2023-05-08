package com.university.homework.film_distribution.models;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.HashMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private int status;
    private String message;
    private String description;
    private int count;
    private HashMap<Integer, Object> values;

    public Response(ResponseBuilder responseBuilder) {
        this.status = responseBuilder.status;
        this.message = responseBuilder.message;
        this.description = responseBuilder.description;
        this.count = responseBuilder.count;
        this.values = responseBuilder.values;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public int getCount() {
        return count;
    }

    public HashMap<Integer, Object> getValues() {
        return values;
    }

    public static class ResponseBuilder {

        private int status;
        private String message;
        private String description;
        private int count;
        private HashMap<Integer, Object> values;

        public ResponseBuilder() {
            super();
        }

        public ResponseBuilder setStatus(int status) {
            this.status = status;
            return this;
        }

        public ResponseBuilder setMessage(String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public ResponseBuilder setCount(int count) {
            this.count = count;
            return this;
        }

        public ResponseBuilder setValues(HashMap<Integer, Object> values) {
            this.values = values;
            return this;
        }

        public Response build() {
            return new Response(this);
        }

    }

    public static HashMap<Integer, Object> toMapByGenres(List<Genre> genres) {
        HashMap<Integer, Object> objectMap = new HashMap<>();

        for (int i = 0; i < genres.size(); i++) {
            objectMap.put(i + 1, genres.get(i));
        }

        return objectMap;
    }

    public static HashMap<Integer, Object> toMapByActors(List<Actor> actors) {
        HashMap<Integer, Object> objectMap = new HashMap<>();

        for (int i = 0; i < actors.size(); i++) {
            objectMap.put(i + 1, actors.get(i));
        }

        return objectMap;
    }

    public static HashMap<Integer, Object> toMapByFilms(List<Film> films) {
        HashMap<Integer, Object> objectMap = new HashMap<>();

        for (int i = 0; i < films.size(); i++) {
            objectMap.put(i + 1, films.get(i));
        }

        return objectMap;
    }

}
