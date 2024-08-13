package ru.evolenta.productList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.productList.model.Good;
import ru.evolenta.productList.model.Person;
import ru.evolenta.productList.model.ProductList;
import ru.evolenta.productList.service.ProductListService;


import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping
public class ProductListController {

    @Autowired
    public ProductListService productListService;

    @Autowired
    public RestTemplate restTemplate;

    @Value("${url.person}")
    String urlPerson;

    @Value("${url.good}")
    String urlGood;

    // Просмотр всех заказов
    @GetMapping("/productlist")
    public Iterable<ProductList> getProductList() {
        return  productListService.getProductList();
    }

    // Просмотр конкретного заказа по его id
    @GetMapping("/productlist/{idProductList}")
    public Optional<ProductList> getProductListById(@PathVariable int idProductList) {
        return  productListService.getProductListById(idProductList);
    }

    // Добавление нового заказа
    @PostMapping("/productlist")
    public Optional<ProductList> addProductList(@RequestBody ProductList productList) {

        if (existPersonById(productList.getIdPerson())) {
            boolean existGoodAndAmount = true;
            for (HashMap.Entry<Integer, Integer> temp: productList.getProductListMap().entrySet()) {
                if (! existGoodById(temp.getKey(), temp.getValue())) {
                    existGoodAndAmount = false;
                    break;
                }
            }
            if (existGoodAndAmount) return Optional.of(productListService.addProductList(productList));
        }
        return Optional.empty();
    }

    // Полное обновление заказа по его id(idProductList)
    @PutMapping("/productlist/{idProductList}")
    public HttpStatus updateProductList(@PathVariable int idProductList,
                                        @RequestBody ProductList productList) {

        HttpStatus status = productListService.existProductListById(idProductList)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            productListService.updateProductList(idProductList, productList);

        return status;
    }

    // Добавление товара idGood и его количества idAmount по id(idProductList) заказа
    @PutMapping("/productlistaddgood/{idProductList}/{idGood}/{idAmount}")
    public HttpStatus updateProductListAddGood(@PathVariable int idProductList,
                                  @PathVariable int idGood,
                                  @PathVariable int idAmount) {

        HttpStatus status = productListService.existProductListById(idProductList)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            productListService.updateProductListAddGood(idProductList, idGood, idAmount);

        return status;
    }

    // Добавление заказа по id(idPerson) пользователя, товара idGood и его количества idAmount
    @PostMapping("/productlistaddPersonGoods/{idPerson}/{idGood}/{idAmount}")
    public HttpStatus addProductListWithPerson(@PathVariable int idPerson,
                                               @PathVariable int idGood,
                                               @PathVariable int idAmount) {

            productListService.addProductListWithPerson(idPerson, idGood, idAmount);

        return HttpStatus.OK;
    }

    // Удаление заказа по его id(idProductList)
    @DeleteMapping("/productlist/{idProductList}")
    public HttpStatus deleteProductList(@PathVariable int idProductList) {

        HttpStatus status = productListService.existProductListById(idProductList)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            productListService.deleteProductListById(idProductList);

        return status;
    }


    // Все заказы у конкретного пользователя
    @GetMapping("/productlist/person/{id}")
    public Iterable<ProductList> getPerson(@PathVariable int id) {
        if (existPersonById(id)) return null;
         else {
             return productListService.getProductListPerson(id);
        }
    }

    // Проверка на существование пользователя
    public boolean existPersonById(int id) {
        String request = String.format("http://" + urlPerson + "/person/" + id);
        Optional<Person> person = restTemplate.getForObject(request, Optional.class);

        if (person.isEmpty()) return false;
        else return true;
    }

    // Проверка на существование товара и в необходимом количестве
    public boolean existGoodById(int idGood, int amount) {
        String request = String.format("http://" + urlGood + "/good/" + idGood);
        Optional<Good> good = restTemplate.getForObject(request, Optional.class);

        if (good.isEmpty()) return false;
        else {
            if (good.get().getAmount()>=amount) return true;
                return false;
        }
    }

}
