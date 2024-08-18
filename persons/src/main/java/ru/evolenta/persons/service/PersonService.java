package ru.evolenta.persons.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.evolenta.persons.model.Person;
import ru.evolenta.persons.repository.PersonRepository;

import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;


    // Получение всех пользователей
    public Iterable<Person> getPerson() {
        return personRepository.findAll();
    }


    // Получение конкретного пользователя по его id
    public Optional<Person> getPersonById(int idPerson) {
        return personRepository.findById(idPerson);
    }


    // Добавление пользователя
    public Person addPerson(Person person) {
        personRepository.save(person);
        return person;
    }


    // Проверка существоания пользователя
    public boolean existPersonById(int idPerson) {
        return personRepository.existsById(idPerson);
    }


    // Обновление пользователя
    public void updatePerson(int idPerson, Person person) {
        personRepository.findById(idPerson).get().setFirstName(person.getFirstName());
        personRepository.findById(idPerson).get().setSurName(person.getSurName());
        personRepository.findById(idPerson).get().setLastName(person.getLastName());
        personRepository.findById(idPerson).get().setBithday(person.getBithday());
        personRepository.findById(idPerson).get().setAdress(person.getAdress());
        personRepository.save(personRepository.findById(idPerson).get());
    }


    // Удаление пользователя
    public void deletePersonById(int idPerson) {
        personRepository.deleteById(idPerson);
    }

}
