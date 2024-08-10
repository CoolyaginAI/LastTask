package ru.evolenta.persons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.persons.model.Person;
import ru.evolenta.persons.service.PersonService;

import java.util.Optional;

@RestController
@RequestMapping
public class PersonController {

    @Autowired
    public PersonService personService;

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("/person")
    public Iterable<Person> getPerson() {
        return personService.getPerson();
    }

    @GetMapping("/person/{id}")
    public Optional<Person> getPersonById(@PathVariable int idPerson) {
        return personService.getPersonById(idPerson);
    }

    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }

    @PutMapping("/person/{id}")
    public HttpStatus uptadePerson(@PathVariable int idPerson, @RequestBody Person person) {

        HttpStatus status = personService.existPersonById(idPerson)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            personService.updatePerson(idPerson, person);

        return status;
    }

    @DeleteMapping("/person/{id}")
    public HttpStatus deletePerson(@PathVariable int idPerson) {

        HttpStatus status = personService.existPersonById(idPerson)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            personService.deletePersonById(idPerson);

        return status;
    }

}
