package ru.evolenta.productList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.productList.model.ProductList;
import ru.evolenta.productList.service.ProductListService;

import java.util.Optional;

@RestController
@RequestMapping
public class ProductListController {

    @Autowired
    public ProductListService productListService;

    @Autowired
    public RestTemplate restTemplate;

    @GetMapping("/productlist")
    public Iterable<ProductList> getProductList() {
        return  productListService.getProductList();
    }

    @GetMapping("/productlist/{id}")
    public Optional<ProductList> getProductListById(@PathVariable int idProductList) {
        return  productListService.getProductListById(idProductList);
    }

    @PostMapping("/productlist")
    public ProductList addProductList(@RequestBody ProductList productList) {
        return productListService.addProductList(productList);
    }

    @PutMapping("/productlist/{id}")
    public HttpStatus updateOrder(@PathVariable int idProductList, @RequestBody ProductList productList) {

        HttpStatus status = productListService.existProductListById(idProductList)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            productListService.updateProductList(idProductList, productList);

        return status;
    }

    @DeleteMapping("productlist/{id}")
    public HttpStatus deleteProductList(@PathVariable int idProductList) {

        HttpStatus status = productListService.existProductListById(idProductList)
                ? HttpStatus.OK
                : HttpStatus.NOT_FOUND;

        if (status == HttpStatus.OK)
            productListService.deleteProductListById(idProductList);

        return status;
    }

}
