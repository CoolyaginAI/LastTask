package ru.evolenta.productList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.evolenta.productList.model.ProductList;
import ru.evolenta.productList.repository.ProductListRepository;

import java.util.Optional;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    public Iterable<ProductList> getProductList() {
        return productListRepository.findAll();
    }

    public Optional<ProductList> getProductListById(int idProductList) {
        return productListRepository.findById(idProductList);
    }

    public ProductList addProductList(ProductList productList) {
        productListRepository.save(productList);
        return productList;
    }

    public boolean existProductListById(int idProductList) {
        return productListRepository.existsById(idProductList);
    }

    public void updateProductList(int id, ProductList productList) {
        productListRepository.findById(id).get().setDateProductList(productList.getDateProductList());
        productListRepository.findById(id).get().setStatus(productList.getStatus());
        productListRepository.findById(id).get().setProductListMap(productList.getProductListMap());
        productListRepository.findById(id).get().setIdPerson(productList.getIdPerson());
        productListRepository.save(productListRepository.findById(id).get());
    }

    public void deleteProductListById(int idProductList) {
        productListRepository.deleteById(idProductList);
    }

}
