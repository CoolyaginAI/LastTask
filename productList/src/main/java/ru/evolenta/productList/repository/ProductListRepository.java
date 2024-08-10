package ru.evolenta.productList.repository;

import org.springframework.data.repository.CrudRepository;
import ru.evolenta.productList.model.ProductList;

public interface ProductListRepository  extends CrudRepository<ProductList, Integer> {
}
