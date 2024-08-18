package ru.evolenta.goods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.goods.model.Good;
import ru.evolenta.goods.service.GoodService;

import java.util.Optional;

@RestController
@RequestMapping
public class GoodController {

    @Autowired
    public GoodService goodService;

    @Autowired
    public RestTemplate restTemplate;


    // Просмотр всех товаров
    @GetMapping("/good")
    public Iterable<Good> getGood() {
        return goodService.getGood();
    }


    // Просмотр конкретного товара по его id
    @GetMapping("/good/{id}")
    public Optional<Good> getGoodById(@PathVariable int id) {
        return goodService.getGoodById(id);
    }


    // Добавление нового товара
    @PostMapping("/good")
    public Good addGood(@RequestBody Good good) {
        return goodService.addGood(good);
    }


    // Обновление товара по его id
    @PutMapping("/good/{id}")
    public HttpStatus updateGood(@PathVariable int id, @RequestBody Good good) {

        HttpStatus status = goodService.existGoodById(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            goodService.updateGood(id, good);

        return status;
    }


    // Удаление товара по его id
    @DeleteMapping("/good/{id}")
    public HttpStatus deleteUser(@PathVariable int id) {

        HttpStatus status = goodService.existGoodById(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            goodService.deleteGoodById(id);

        return status;
    }

}
