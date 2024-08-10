package ru.evolenta.persons.repository;

import org.springframework.data.repository.CrudRepository;
import ru.evolenta.persons.model.Person;

public interface PersonRepository extends CrudRepository<Person, Integer> {
}
