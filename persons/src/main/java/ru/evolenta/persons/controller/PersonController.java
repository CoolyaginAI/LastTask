package ru.evolenta.persons.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.persons.model.Person;
import ru.evolenta.persons.model.ProductList;
import ru.evolenta.persons.service.PersonService;

import java.util.Optional;

@RestController
@RequestMapping
public class PersonController {

    @Autowired
    public PersonService personService;

    @Autowired
    public RestTemplate restTemplate;

    @Value("${url.productList}")
    String urlProductList;

    // Получение всех пользователей
    @GetMapping("/person")
    public Iterable<Person> getPerson() {
        return personService.getPerson();
    }


    // Получение конкретного пользователя по его id
    @GetMapping("/person/{idPerson}")
    public Optional<Person> getPersonById(@PathVariable int idPerson) {
        return personService.getPersonById(idPerson);
    }


    // Добавление пользователя
    @PostMapping("/person")
    public Person addPerson(@RequestBody Person person) {
        return personService.addPerson(person);
    }


    // Обновление пользователя
    @PutMapping("/person/{idPerson}")
    public HttpStatus uptadePerson(@PathVariable int idPerson, @RequestBody Person person) {

        HttpStatus status = personService.existPersonById(idPerson)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            personService.updatePerson(idPerson, person);

        return status;
    }


    // Удаление пользователя
    @DeleteMapping("/person/{idPerson}")
    public HttpStatus deletePerson(@PathVariable int idPerson) {

        HttpStatus status = personService.existPersonById(idPerson)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            personService.deletePersonById(idPerson);

        return status;
    }


    // Получение всех заказов конкретного пользователя
    @GetMapping("/allproductlist/person/{idPerson}")
    public ResponseEntity<Iterable<ProductList>> getAllProductListByPerson(@PathVariable int idPerson) {

        if (personService.existPersonById(idPerson)) {
            String request = String.format("http://" + urlProductList + "/productlist/personTrue/" + idPerson);
            Iterable<ProductList> productList = restTemplate.getForObject(request, Iterable.class);
            return new ResponseEntity<>(productList,HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
