package com.university.homework.film_distribution.controllers;

import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import com.university.homework.film_distribution.models.Film;
import com.university.homework.film_distribution.models.Response;
import com.university.homework.film_distribution.services.impl.FilmServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
public class FilmController {

    private final FilmServiceImpl filmService;

    @Autowired
    public FilmController(FilmServiceImpl filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneFilmById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(filmService.getById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(1)
                            .setMessage(e.getMessage())
                            .setDescription("Проверьте корректно ли введен id")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - getOneFilm()")
                            .build());
        }
    }

    @GetMapping
    public ResponseEntity getAllFilms() {
        try {
            List<Film> films = filmService.getAll();
            return ResponseEntity.ok(
                    new Response.ResponseBuilder()
                            .setStatus(200)
                            .setCount(films.size())
                            .setValues(Response.toMapByFilms(films))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - getAllFilms()")
                            .build());
        }
    }

    @PostMapping
    public ResponseEntity addFilm(@RequestBody FilmEntity film) {
        try {
            return ResponseEntity.ok(filmService.add(film));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.badRequest()
                    .body(new Response.ResponseBuilder()
                            .setStatus(2)
                            .setMessage(e.getMessage())
                            .setDescription("")
                            .build());
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
                            .setDescription("Метод - addFilms()")
                            .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateFilm(@PathVariable long id, @RequestBody FilmEntity film) {
        try {
            filmService.update(id, film);
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
    public ResponseEntity deleteFilm(@PathVariable long id) {
        try {
            filmService.deleteById(id);
            return ResponseEntity.ok("");
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(1)
                            .setMessage(e.getMessage())
                            .setDescription("Проверьте корректно ли введен id")
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - deleteFilm()")
                            .build());
        }
    }

}
