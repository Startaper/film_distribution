package com.university.homework.film_distribution.services.impl;

import com.university.homework.film_distribution.entities.ActorEntity;
import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import com.university.homework.film_distribution.models.Actor;
import com.university.homework.film_distribution.repositories.ActorRepo;
import com.university.homework.film_distribution.repositories.FilmRepo;
import com.university.homework.film_distribution.services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ActorServiceImpl implements BasicService<ActorEntity, Actor> {

    private final FilmRepo filmRepo;
    private final ActorRepo actorRepo;

    @Autowired
    public ActorServiceImpl(FilmRepo filmRepo, ActorRepo actorRepo) {
        this.filmRepo = filmRepo;
        this.actorRepo = actorRepo;
    }

    @Override
    public Actor getById(Long id) throws NotFoundException {
        Optional<ActorEntity> actorDTO = actorRepo.findById(id);

        if (actorDTO.isEmpty()) {
            throw new NotFoundException("Актер с таким id не найден!");
        }

        return Actor.toActor(actorDTO.get());
    }

    @Deprecated
    @Override
    public Actor getByName(String name) {
        return null;
    }

    @Override
    public List<Actor> getAll() {
        return Actor.toListActors(false, actorRepo.findAll());
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (!actorRepo.existsById(id)) {
            throw new NotFoundException("Актер с таким id не найден!");
        }

        actorRepo.deleteById(id);
    }

    @Override
    public Actor add(ActorEntity actor) throws AlreadyExistsException, RequiredFieldsException, NotFoundException {
        checkNewActor(actor);
        if (actorRepo.existsByFirstNameAndLastNameAndMiddleName(
                actor.getFirstName(), actor.getLastName(), actor.getMiddleName())) {
            throw new AlreadyExistsException("Такой актер уже есть в системе!");
        }

        actor.setActive(true);
        actor.setDateOfCreation(new Date());
        actor.setDateOfLastUpdated(new Date());
        if (actor.getMiddleName() == null) {
            actor.setMiddleName("");
        }

        List<FilmEntity> films = actor.getFilms();
        if (films != null && films.size() != 0) {
            for (FilmEntity film : films) {
                Optional<FilmEntity> filmDTO = filmRepo.findById(film.getId());
                if (filmDTO.isEmpty()) {
                    throw new NotFoundException("Фильм не найден!");
                }
                filmDTO.get().getActors().add(actor);
            }
            actor.setNumberOfFilms(films.size());
        }

        return Actor.toActor(actorRepo.save(actor));
    }

    @Override
    public void update(long id, ActorEntity updates) throws RequiredFieldsException, NotFoundException {
        if (updates == null) {
            throw new RequiredFieldsException("Тело запроса не может пустым!");
        }

        Optional<ActorEntity> actorDTO = actorRepo.findById(id);
        if (actorDTO.isEmpty()) {
            throw new NotFoundException("Жанр с таким id не найден!");
        }

        // Update first name
        if (checkUpdates(actorDTO.get().getFirstName(), updates.getFirstName())) {
            actorDTO.get().setFirstName(updates.getFirstName());
        }

        // Update last name
        if (checkUpdates(actorDTO.get().getLastName(), updates.getLastName())) {
            actorDTO.get().setLastName(updates.getLastName());
        }

        // Update middle name
        if (checkUpdates(actorDTO.get().getMiddleName(), updates.getMiddleName())) {
            actorDTO.get().setMiddleName(updates.getMiddleName());
        }

        // Update place of birth
        if (checkUpdates(actorDTO.get().getPlaceOfBirth(), updates.getPlaceOfBirth())) {
            actorDTO.get().setPlaceOfBirth(updates.getPlaceOfBirth());
        }

        // Update career
        if (checkUpdates(actorDTO.get().getCareer(), updates.getCareer())) {
            actorDTO.get().setCareer(updates.getCareer());
        }

        // Update birthdate
        if (updates.getBirthDate() != null) {
            if (checkUpdates(actorDTO.get().getBirthDate().toString(), updates.getBirthDate().toString())) {
                actorDTO.get().setBirthDate(updates.getBirthDate());
            }
        }

        // Update active
        if (updates.isActive() != actorDTO.get().isActive()) {
            actorDTO.get().setActive(updates.isActive());
        }

        // Update films
        if (updates.getFilms() != null && !updates.getFilms().isEmpty()) {
            updateFilms(actorDTO.get(), updates.getFilms());
        }

        actorDTO.get().setDateOfLastUpdated(new Date());

        actorRepo.save(actorDTO.get());
    }

    private boolean checkUpdates(String saved, String updates) {
        return updates != null && !updates.equals("") && !saved.equals(updates);
    }

    private void updateFilms(ActorEntity actor, List<FilmEntity> filmEntities) {
        filmEntities.stream()
                .map(film -> filmRepo.findById(film.getId()).orElseThrow()).toList()
                .forEach(film -> updateFilm(actor, film));
    }

    private void updateFilm(ActorEntity actor, FilmEntity film) {
        List<ActorEntity> actorEntities = film.getActors();
        if (!actorEntities.contains(actor)) {
            actorEntities.add(actor);
        }
        film.setActors(actorEntities);
        filmRepo.save(film);
    }
}