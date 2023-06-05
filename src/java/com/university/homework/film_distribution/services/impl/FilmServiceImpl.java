package com.university.homework.film_distribution.services.impl;

import com.university.homework.film_distribution.entities.ActorEntity;
import com.university.homework.film_distribution.entities.FilmEntity;
import com.university.homework.film_distribution.entities.GenreEntity;
import com.university.homework.film_distribution.exceptions.AlreadyExistsException;
import com.university.homework.film_distribution.exceptions.NotFoundException;
import com.university.homework.film_distribution.exceptions.RequiredFieldsException;
import com.university.homework.film_distribution.models.Film;
import com.university.homework.film_distribution.repositories.ActorRepo;
import com.university.homework.film_distribution.repositories.FilmRepo;
import com.university.homework.film_distribution.repositories.GenreRepo;
import com.university.homework.film_distribution.services.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements BasicService<FilmEntity, Film> {

    private final FilmRepo filmRepo;
    private final GenreRepo genreRepo;
    private final ActorRepo actorRepo;

    @Autowired
    public FilmServiceImpl(FilmRepo filmRepo, GenreRepo genreRepo,
                           ActorRepo actorRepo) {
        this.filmRepo = filmRepo;
        this.genreRepo = genreRepo;
        this.actorRepo = actorRepo;
    }

    @Override
    public Film getById(Long id) throws NotFoundException {
        Optional<FilmEntity> filmDTO = filmRepo.findById(id);

        if (filmDTO.isEmpty()) {
            throw new NotFoundException("Фильм с таким id не найден!");
        }

        return Film.toFilm(filmDTO.get());
    }

    @Override
    public Film getByName(String name) throws NotFoundException {
        FilmEntity filmDTO = filmRepo.findByName(name);

        if (filmDTO == null) {
            throw new NotFoundException("Фильм с таким id не найден!");
        }

        return Film.toFilm(filmDTO);
    }

    @Override
    public List<Film> getAll() {
        return Film.toListFilms(filmRepo.findAll());
    }

    @Override
    public void deleteById(Long id) throws NotFoundException {
        if (!filmRepo.existsById(id)) {
            throw new NotFoundException("Фильм с таким id не найден!");
        }

        filmRepo.deleteById(id);
    }

    @Override
    public Film add(FilmEntity film) throws RequiredFieldsException, AlreadyExistsException, NotFoundException {

        checkNewFilm(film);
        if (filmRepo.existsByName(film.getName())) {
            throw new AlreadyExistsException("Такой фильм уже есть в системе!");
        }

        film.setActive(true);
        film.setDateOfCreation(new Date());
        film.setDateOfLastUpdated(new Date());
        film.setRating(0);
        film.setNumberOfReviews(0);

        List<GenreEntity> genres = film.getGenres();
        if (genres != null && genres.size() != 0) {
            for (GenreEntity genre : genres) {
                Optional<GenreEntity> genreDTO = genreRepo.findByName(genre.getName());
                if (genreDTO.isEmpty()) {
                    throw new NotFoundException("Жанр не найден!");
                }
                genres.set(genres.indexOf(genre), genreDTO.get());
            }
            film.setGenres(genres);
        }

        List<ActorEntity> actors = film.getActors();
        if (actors != null && actors.size() != 0) {
            for (ActorEntity actor : actors) {
                Optional<ActorEntity> actorDTO = actorRepo.findById(actor.getId());
                if (actorDTO.isEmpty()) {
                    throw new NotFoundException("Актер не найден!");
                }
                actors.set(actors.indexOf(actor), actorDTO.get());
            }
            film.setActors(actors);
        }

        return Film.toFilm(filmRepo.save(film));
    }

    @Override
    public void update(long id, FilmEntity updates) throws NotFoundException, RequiredFieldsException {
        if (updates == null) {
            throw new RequiredFieldsException("Тело запроса не может пустым!");
        }

        Optional<FilmEntity> filmDTO = filmRepo.findById(id);
        if (filmDTO.isEmpty()) {
            throw new NotFoundException("Жанр с таким id не найден!");
        }

        // Update name
        if (checkUpdates(filmDTO.get().getName(), updates.getName())) {
            filmDTO.get().setName(updates.getName());
        }

        // Update anons
        if (checkUpdates(filmDTO.get().getAnons(), updates.getAnons())) {
            filmDTO.get().setAnons(updates.getAnons());
        }

        // Update description
        if (checkUpdates(filmDTO.get().getDescription(), updates.getDescription())) {
            filmDTO.get().setDescription(updates.getDescription());
        }

        // Update Image URL
        if (checkUpdates(filmDTO.get().getImageUrl(), updates.getImageUrl())) {
            filmDTO.get().setImageUrl(updates.getImageUrl());
        }

        // Update price
        if (checkUpdates(String.valueOf(filmDTO.get().getPrice()), String.valueOf(updates.getPrice()))) {
            filmDTO.get().setPrice(updates.getPrice());
        }

        // Update age restriction
        if (checkUpdates(String.valueOf(filmDTO.get().getAgeRestriction()), String.valueOf(updates.getAgeRestriction()))) {
            filmDTO.get().setAgeRestriction(updates.getAgeRestriction());
        }

        // Update country of production
        if (checkUpdates(filmDTO.get().getCountryOfProduction(), updates.getCountryOfProduction())) {
            filmDTO.get().setCountryOfProduction(updates.getCountryOfProduction());
        }

        // Update rating
        if (checkUpdates(String.valueOf(filmDTO.get().getRating()), String.valueOf(updates.getRating()))) {
            filmDTO.get().setRating(updates.getRating());
        }

        // Update number of reviews
        if (checkUpdates(String.valueOf(filmDTO.get().getNumberOfReviews()), String.valueOf(updates.getNumberOfReviews()))) {
            filmDTO.get().setNumberOfReviews(updates.getNumberOfReviews());
        }

        // Update duration of the film
        if (checkUpdates(String.valueOf(filmDTO.get().getDurationOfTheFilm()), String.valueOf(updates.getDurationOfTheFilm()))) {
            filmDTO.get().setDurationOfTheFilm(updates.getDurationOfTheFilm());
        }

        // Update number of episodes
        if (checkUpdates(String.valueOf(filmDTO.get().getNumberOfEpisodes()), String.valueOf(updates.getNumberOfEpisodes()))) {
            filmDTO.get().setNumberOfEpisodes(updates.getNumberOfEpisodes());
        }

        // Update number of seasons
        if (checkUpdates(String.valueOf(filmDTO.get().getNumberOfSeasons()), String.valueOf(updates.getNumberOfSeasons()))) {
            filmDTO.get().setNumberOfSeasons(updates.getNumberOfSeasons());
        }

        // Update active
        if (updates.isActive() != filmDTO.get().isActive()) {
            filmDTO.get().setActive(updates.isActive());
        }

        // Update actors
        if (updates.getActors() != null && !updates.getActors().isEmpty()) {
            filmDTO.get().setActors(checkListActors(filmDTO.get().getActors(), updates.getActors()));
            updateActors(filmDTO.get().getActors());
        }

        // Update genres
        if (updates.getGenres() != null && !updates.getGenres().isEmpty()) {
            filmDTO.get().setGenres(checkListGenres(filmDTO.get().getGenres(), updates.getGenres()));
        }

        filmDTO.get().setDateOfLastUpdated(new Date());
        filmRepo.save(filmDTO.get());
    }

    private boolean checkUpdates(String saved, String updates) {
        return updates != null && !updates.equals("") && !saved.equals(updates);
    }

    private List<ActorEntity> checkListActors(List<ActorEntity> actorsDTO, List<ActorEntity> actors) {
        return actors.stream()
                .map(actor -> actorRepo.findById(actor.getId()).orElseThrow())
                .filter(actor -> !actorsDTO.contains(actor))
                .collect(Collectors.toList());
    }

    private List<GenreEntity> checkListGenres(List<GenreEntity> genresDTO, List<GenreEntity> genres) {
        return genres.stream()
                .map(genre -> genreRepo.findByName(genre.getName()).orElseThrow())
                .filter(genre -> !genresDTO.contains(genre))
                .collect(Collectors.toList());
    }

    private void updateActors(List<ActorEntity> actors) {
        for (ActorEntity a: actors) {
            a.setNumberOfFilms(a.getFilms().size());
            actorRepo.save(a);
        }
    }
}
