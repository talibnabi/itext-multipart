package org.example.repository;

import org.example.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByIdAndNameAndUsernameAndEmailAndDescription(Long id, String name, String username, String Email, String description);


}
