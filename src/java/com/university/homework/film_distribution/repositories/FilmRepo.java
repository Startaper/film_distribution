package com.university.homework.film_distribution.repositories;

import com.university.homework.film_distribution.entities.FilmEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepo extends JpaRepository<FilmEntity, Long> {

    FilmEntity findByName(String name);

    boolean existsByName(String name);

}
