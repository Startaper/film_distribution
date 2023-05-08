package com.university.homework.film_distribution.controllers;

import com.university.homework.film_distribution.entities.ActorEntity;
import com.university.homework.film_distribution.entities.GenreEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import com.university.homework.film_distribution.models.Actor;
import com.university.homework.film_distribution.models.Genre;
import com.university.homework.film_distribution.models.Response;
import com.university.homework.film_distribution.services.impl.ActorServiceImpl;
import com.university.homework.film_distribution.services.impl.GenreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actors")
public class ActorController {

    private final ActorServiceImpl actorService;

    @Autowired
    public ActorController(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneActor(@PathVariable long id) {
        try {
            return ResponseEntity.ok(actorService.getById(id));
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
                            .setDescription("Метод - getOneActor()")
                            .build());
        }
    }

    @GetMapping()
    public ResponseEntity getAllActors() {
        try {
            List<Actor> actors = actorService.getAll();
            return ResponseEntity.ok(
                    new Response.ResponseBuilder()
                            .setStatus(200)
                            .setCount(actors.size())
                            .setValues(Response.toMapByActors(actors))
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new Response.ResponseBuilder()
                            .setStatus(300)
                            .setMessage(e.getMessage())
                            .setDescription("Метод - getAllActors()")
                            .build());
        }
    }

    @PostMapping
    public ResponseEntity addActor(@RequestBody ActorEntity actor) {
        try {
            return ResponseEntity.ok(actorService.add(actor));
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
                            .setDescription("Метод - addActor()")
                            .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity updateActor(@PathVariable long id, @RequestBody ActorEntity actor) {
        try {
            actorService.update(id, actor);
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
                            .setDescription("Метод - updateActor()")
                            .build());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteActor(@PathVariable long id) {
        try {
            actorService.deleteById(id);
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
                            .setDescription("Метод - deleteActor()")
                            .build());
        }
    }



}
