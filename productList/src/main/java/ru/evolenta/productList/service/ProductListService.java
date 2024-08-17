package ru.evolenta.productList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.evolenta.productList.model.ProductList;
import ru.evolenta.productList.repository.ProductListRepository;

import java.util.*;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    // Получить все заказы
    public Iterable<ProductList> getProductList() {
        return productListRepository.findAll();
    }

    // Получить заказы по id пользователя
    public Iterable<ProductList> getProductListPerson(int idPerson) {

        Iterator<ProductList> iterator = productListRepository.findAll().iterator();
        List<ProductList> iterator2 = new ArrayList<>();

        while (iterator.hasNext()) {
            ProductList tempProductList =  iterator.next();
            if (tempProductList.getIdPerson() == idPerson)  iterator2.add(tempProductList);
        }

        return iterator2;
    }

    // Получить конкретный заказ по id(idProductList)
    public Optional<ProductList> getProductListById(int idProductList) {
        return productListRepository.findById(idProductList);
    }

    // Добавить новый заказ
    public ProductList addProductList(ProductList productList) {
        productListRepository.save(productList);
        return productList;
    }

    // Проверка существования заказа по его id(idProductList)
    public boolean existProductListById(int idProductList) {
        return productListRepository.existsById(idProductList);
    }

    // Обновление заказа по его id(idProductList)
    public void updateProductList(int id, ProductList productList) {
        productListRepository.findById(id).get().setDateProductList(productList.getDateProductList());
        productListRepository.findById(id).get().setStatus(productList.getStatus());
        productListRepository.findById(id).get().setProductListMap(productList.getProductListMap());
        productListRepository.findById(id).get().setIdPerson(productList.getIdPerson());
        productListRepository.save(productListRepository.findById(id).get());
    }

    // Добавление в существующий заказ тавара(idGood) и его количества(idAmount)
    public void updateProductListAddGood(int idProductList, int idGood, int idAmount) {
        productListRepository.findById(idProductList).get().getProductListMap().put(idGood, idAmount);
        productListRepository.save(productListRepository.findById(idProductList).get());
    }

    // Добавить новый заказ по конкретному пользователю (idPerson) с добавлением товара (idGood) и его количества (idAmount)
    public ProductList addProductListWithPerson(int idPerson, int idGood, int idAmount) {

        HashMap<Integer, Integer> tempHashMap = new HashMap<>();
        tempHashMap.put(idGood,idAmount);

        ProductList productList = new ProductList(
                new Date(),"В пути",
                idPerson, tempHashMap
        );

        productListRepository.save(productList);
        return productList;
    }

    // Удаление заказа по его id(idProductList)
    public void deleteProductListById(int idProductList) {
        productListRepository.deleteById(idProductList);
    }

}
