package ru.evolenta.productList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.evolenta.productList.model.Good;
import ru.evolenta.productList.model.Person;
import ru.evolenta.productList.model.ProductList;
import ru.evolenta.productList.repository.ProductListRepository;

import java.util.*;

@Service
public class ProductListService {

    @Autowired
    private ProductListRepository productListRepository;

    @Autowired
    public RestTemplate restTemplate;

    @Value("${url.person}")
    String urlPerson;

    @Value("${url.good}")
    String urlGood;


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


    // Получить стоимость всего заказа по его id(idProductList)
    public Integer getCostByIdProductList(int idProductList) {

        Integer costs = 0;
        HashMap<Integer, Integer> tempHashMap = new HashMap<>();
        tempHashMap = productListRepository.findById(idProductList).get().getProductListMap();

        for (HashMap.Entry<Integer, Integer> entry : tempHashMap.entrySet())
            costs += costGoodById(entry.getKey())*entry.getValue();

        return  costs;
    }


    // Получить стоимость всего заказа и
    // количество товаров в заказе по его id(idProductList)
    public List<Integer> getCostAndAmountByIdProductList(int idProductList) {

        Integer costs = 0;
        Integer amounts = 0;

        HashMap<Integer, Integer> tempHashMap = new HashMap<>();
        tempHashMap = productListRepository.findById(idProductList).get().getProductListMap();

        for (HashMap.Entry<Integer, Integer> entry : tempHashMap.entrySet()) {
            costs += costGoodById(entry.getKey()) * entry.getValue();
            amounts++;
        }

        return List.of(idProductList, costs, amounts);
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

    // Запрос на стоимость за единицу товара,
    // если товара не существует, то возвращяется 0
    public Integer costGoodById(int idGood) {
        String request = String.format("http://" + urlGood + "/good/" + idGood);
        Optional<Good> good = restTemplate.getForObject(request, Optional.class);

        if (good.isEmpty()) return 0;
        else return good.get().getCost();
    }


}
