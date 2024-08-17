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
        return productListService.getProductList();
    }


    // Просмотр конкретного заказа по его id
    @GetMapping("/productlist/{idProductList}")
    public Optional<ProductList> getProductListById(@PathVariable int idProductList) {
        return  productListService.getProductListById(idProductList);
    }


    // Добавление нового заказа
    // происходит проверка на существование пользователя и товара в нужном количестве
    @PostMapping("/productlist")
    public ResponseEntity<ProductList> addProductList(@RequestBody ProductList productList) {

        if (existPersonById(productList.getIdPerson())) {

            boolean existGoodAndAmount = true;
            for (HashMap.Entry<Integer, Integer> temp: productList.getProductListMap().entrySet()) {
                if (! existGoodById(temp.getKey(), temp.getValue())) {
                    existGoodAndAmount = false;
                    break;
                }
            }
            if (existGoodAndAmount) return new ResponseEntity<>(
                    productListService.addProductList(productList),
                    HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Полное обновление заказа по его id(idProductList)
    // происходит проверка на существование пользователя, товара в нужном количестве
    @PutMapping("/productlist/{idProductList}")
    public ResponseEntity<ProductList> updateProductList(@PathVariable int idProductList,
                                        @RequestBody ProductList productList) {

        if (! productListService.existProductListById(idProductList))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            if (existPersonById(productList.getIdPerson())) {

                boolean existGoodAndAmount = true;
                for (HashMap.Entry<Integer, Integer> temp: productList.getProductListMap().entrySet()) {
                    if (! existGoodById(temp.getKey(), temp.getValue())) {
                        existGoodAndAmount = false;
                        break;
                    }
                }
                if (existGoodAndAmount) {
                    productListService.updateProductList(idProductList, productList);
                    return new ResponseEntity<>(
                            productList,
                            HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Добавление товара idGood и его количества idAmount по id(idProductList) заказа
    // происходит проверка на существование товара в нужном количестве
    @PutMapping("/productlistaddgood/{idProductList}/{idGood}/{idAmount}")
    public ResponseEntity<ProductList> updateProductListAddGood(@PathVariable int idProductList,
                                  @PathVariable int idGood,
                                  @PathVariable int idAmount) {

        if (productListService.existProductListById(idProductList) && existGoodById(idGood, idAmount)) {
            productListService.updateProductListAddGood(idProductList, idGood, idAmount);
            return new ResponseEntity<>(
                        productListService.getProductListById(idProductList).get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Добавление заказа по id(idPerson) пользователя, товара idGood и его количества idAmount
    @PostMapping("/productlistaddPersonGoods/{idPerson}/{idGood}/{idAmount}")
    public ResponseEntity<ProductList> addProductListWithPerson(@PathVariable int idPerson,
                                               @PathVariable int idGood,
                                               @PathVariable int idAmount) {

        if (existPersonById(idPerson) && existGoodById(idGood, idAmount))
            return new ResponseEntity<>(
                    productListService.addProductListWithPerson(idPerson, idGood, idAmount),
                    HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Iterable<ProductList>> getPerson(@PathVariable int id) {
        if (existPersonById(id))
            return new ResponseEntity<>(productListService.getProductListPerson(id),HttpStatus.OK);
         else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Запрос на проверку существования пользователя
    public boolean existPersonById(int id) {
        String request = String.format("http://" + urlPerson + "/person/" + id);
        Optional<Person> person = restTemplate.getForObject(request, Optional.class);

        if (person.isEmpty()) return false;
        else return true;
    }


    // Запрос на проверку существования товара и в необходимом количестве
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
