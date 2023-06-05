package com.university.homework.film_distribution.services.impl;

import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.entities.GenreEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import com.university.homework.film_distribution.models.Genre;
import com.university.homework.film_distribution.repositories.FilmRepo;
import com.university.homework.film_distribution.repositories.GenreRepo;
import com.university.homework.film_distribution.services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements BasicService<GenreEntity, Genre> {

    private final FilmRepo filmRepo;
    private final GenreRepo genreRepo;

    @Autowired
    public GenreServiceImpl(FilmRepo filmRepo, GenreRepo genreRepo) {
        this.filmRepo = filmRepo;
        this.genreRepo = genreRepo;
    }

    @Override
    public Genre getById(Long id) throws NotFoundException {
        Optional<GenreEntity> genreDTO = genreRepo.findById(id);

        if (genreDTO.isEmpty()) {
            throw new NotFoundException("Жанр с таким id не найден!");
        }

        return Genre.toGenre(genreDTO.get());
    }

    @Override
    public Genre getByName(String name) throws NotFoundException {
        Optional<GenreEntity> genreDTO = genreRepo.findByName(name);

        if (genreDTO.isEmpty()) {
            throw new NotFoundException("Жанр с таким id не найден!");
        }

        return Genre.toGenre(genreDTO.get());
    }

    @Override
    public List<Genre> getAll() {
        return Genre.toListGenres(false, genreRepo.findAll());
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (!genreRepo.existsById(id)) {
            throw new NotFoundException("Жанр с таким id не найден!");
        }

        genreRepo.deleteById(id);
    }

    @Override
    public Genre add(GenreEntity genre) throws AlreadyExistsException, RequiredFieldsException, NotFoundException {
        if (genre == null) {
            throw new RequiredFieldsException("Тело запроса не может пустым!");
        }

        if (genreRepo.existsByName(genre.getName())) {
            throw new AlreadyExistsException("Такой жанр уже есть в системе!");
        }

        if (genre.getName() == null) {
            throw new RequiredFieldsException("Поле name не может быть пустым!");
        }

        genre.setActive(true);
        genre.setDateOfCreation(new Date());
        genre.setDateOfLastUpdated(new Date());

        List<FilmEntity> films = genre.getFilms();
        if (films != null && films.size() != 0) {
            for (FilmEntity film : films) {
                Optional<FilmEntity> filmDTO = filmRepo.findById(film.getId());
                if (filmDTO.isEmpty()) {
                    throw new NotFoundException("Фильм не найден!");
                }
                films.set(films.indexOf(film), filmDTO.get());
            }
            genre.setFilms(films);
        }

        return Genre.toGenre(genreRepo.save(genre));
    }

    @Override
    public void update(long id, GenreEntity updates) throws RequiredFieldsException, NotFoundException {
        if (updates == null) {
            throw new RequiredFieldsException("Тело запроса не может пустым!");
        }

        Optional<GenreEntity> genreDTO = genreRepo.findById(id);
        if (genreDTO.isEmpty()) {
            throw new NotFoundException("Жанр с таким id не найден!");
        }

        // Update name
        if (updates.getName() != null && !updates.getName().equals("") && !genreDTO.get().getName().equals(updates.getName())) {
            genreDTO.get().setName(updates.getName());
        }

        // Update active
        if (updates.isActive() != genreDTO.get().isActive()) {
            genreDTO.get().setActive(updates.isActive());
        }

        // Update films
        if (updates.getFilms() != null && !updates.getFilms().isEmpty()) {
            updateFilms(genreDTO.get(), updates.getFilms());
        }

        genreDTO.get().setDateOfLastUpdated(new Date());

        genreRepo.save(genreDTO.get());
    }

    private void updateFilms(GenreEntity genre, List<FilmEntity> filmEntities) {
        filmEntities.stream()
                .map(film -> filmRepo.findById(film.getId()).orElseThrow()).toList()
                .forEach(film -> updateFilm(genre, film));
    }

    private void updateFilm(GenreEntity genre, FilmEntity film) {
        List<GenreEntity> genreEntities = film.getGenres();
        if (!genreEntities.contains(genre)) {
            genreEntities.add(genre);
        }
        film.setGenres(genreEntities);
        filmRepo.save(film);
    }

}