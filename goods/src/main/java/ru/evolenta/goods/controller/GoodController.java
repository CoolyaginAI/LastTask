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

    @GetMapping("/good")
    public Iterable<Good> getGood() {
        return goodService.getGood();
    }

    @GetMapping("/good/{id}")
    public Optional<Good> getGoodById(@PathVariable int id) {
        return goodService.getGoodById(id);
    }

    @PostMapping("/good")
    public Good addGood(@RequestBody Good good) {
        return goodService.addGood(good);
    }

    @PutMapping("/good/{id}")
    public HttpStatus updateGood(@PathVariable int id, @RequestBody Good user) {

        HttpStatus status = goodService.existGoodById(id)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            goodService.updateGood(id, user);

        return status;
    }

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
