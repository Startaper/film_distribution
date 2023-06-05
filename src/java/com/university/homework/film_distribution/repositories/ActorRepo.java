package com.university.homework.film_distribution.repositories;

import com.university.homework.film_distribution.entities.ActorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepo extends JpaRepository<ActorEntity, Long> {

    boolean existsByFirstNameAndLastNameAndMiddleName(String firstName, String lastName, String middleName);

}
