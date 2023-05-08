package com.university.homework.film_distribution.controllers;

import com.university.homework.film_distribution.entities.GenreEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import com.university.homework.film_distribution.models.Genre;
import com.university.homework.film_distribution.models.Response;
import com.university.homework.film_distribution.services.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreServiceImpl genreService;

    @Autowired
    public GenreController(GenreServiceImpl genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneGenre(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(genreService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.status(404).body(
                    new Response.ResponseBuilder()
                            .setStatus(1)
                            .setMessage(e.getMessage())
                            .setDescription("Проверьте корректно ли введен id").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - getOneGenre()").build());
        }
    }

    @GetMapping()
    public ResponseEntity getAllGenres() {
        try {
            List<Genre> genres = genreService.getAll();
            return ResponseEntity.ok(
                    new Response.ResponseBuilder()
                            .setStatus(200)
                            .setCount(genres.size())
                            .setValues(Response.toMapByGenres(genres))
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - getAllGenres()").build());
        }
    }

    @PostMapping
    public ResponseEntity addGenre(@RequestBody GenreEntity genre) {
        try {
            return ResponseEntity.ok(genreService.add(genre));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(new Response.ResponseBuilder()
                            .setStatus(2)
                            .setMessage(e.getMessage())
                            .setDescription("").build());
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(new Response.ResponseBuilder()
                            .setStatus(1)
                            .setMessage(e.getMessage())
                            .setDescription("Проверьте корректно ли введен id").build());
        } catch (RequiredFieldsException e) {
            return ResponseEntity.badRequest()
                    .body(new Response.ResponseBuilder()
                            .setStatus(3)
                            .setMessage(e.getMessage())
                            .setDescription("Необходимо заполнить все обязательные поля и отправить запрос по новой.").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - addGenre()").build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateGenre(@PathVariable long id, @RequestBody GenreEntity genre) {
        try {
            genreService.update(id, genre);
            return ResponseEntity.ok("");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(new Response.ResponseBuilder()
                            .setStatus(1)
                            .setMessage(e.getMessage())
                            .setDescription("Проверьте корректно ли введен id")
                            .build());
        } catch (RequiredFieldsException e) {
            return ResponseEntity.badRequest()
                    .body(new Response.ResponseBuilder()
                            .setStatus(3)
                            .setMessage(e.getMessage())
                            .setDescription("Необходимо заполнить все обязательные поля и отправить запрос по новой.")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - updateGenre()")
                            .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGenre(@PathVariable long id) {
        try {
            genreService.deleteById(id);
            return ResponseEntity.ok("");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(1)
                            .setMessage(e.getMessage())
                            .setDescription("Проверьте корректно ли введен id").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - deleteGenre()").build());
        }
    }

}
