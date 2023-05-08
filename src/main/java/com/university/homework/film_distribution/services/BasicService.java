package com.university.homework.film_distribution.services;

import com.university.homework.film_distribution.entities.ActorEntity;
import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BasicService<T, M> {

    M getById(Long id) throws NotFoundException;

    M getByName(String name) throws NotFoundException;

    List<M> getAll();

    void deleteById(Long id) throws NotFoundException;

    M add(T t) throws RequiredFieldsException, AlreadyExistsException, NotFoundException;

    void update(long id, T updates) throws RequiredFieldsException, NotFoundException;

    default void checkNewFilm(FilmEntity film) throws RequiredFieldsException {
        if (film == null) {
            throw new RequiredFieldsException("Тело запроса не может пустым!");
        }
        if (film.getName() == null) {
            throw new RequiredFieldsException("Поле name не может быть пустым!");
        }
        if (film.getAnons() == null) {
            throw new RequiredFieldsException("Поле anons не может быть пустым!");
        }
        if (film.getImageUrl() == null) {
            throw new RequiredFieldsException("Поле imageUrl не может быть пустым!");
        }
        if (film.getTypeOfFilm() == null) {
            throw new RequiredFieldsException("Поле typeOfFilm не может быть пустым!");
        }
        if (film.getCountryOfProduction() == null) {
            throw new RequiredFieldsException("Поле countryOfProduction не может быть пустым!");
        }
        if (film.getDateRelease() == null) {
            throw new RequiredFieldsException("Поле dateRelease не может быть пустым!");
        }
    }

    default void checkNewActor(ActorEntity actor) throws RequiredFieldsException {
        if (actor == null) {
            throw new RequiredFieldsException("Тело запроса не может пустым!");
        }
        if (actor.getFirstName() == null) {
            throw new RequiredFieldsException("Поле firstName не может быть пустым!");
        }
        if (actor.getLastName() == null) {
            throw new RequiredFieldsException("Поле lastName не может быть пустым!");
        }
        if (actor.getBirthDate() == null) {
            throw new RequiredFieldsException("Поле birthDate не может быть пустым!");
        }
        if (actor.getPlaceOfBirth() == null) {
            throw new RequiredFieldsException("Поле placeOfBirth не может быть пустым!");
        }
        if (actor.getCareer() == null) {
            throw new RequiredFieldsException("Поле career не может быть пустым!");
        }
    }

}
