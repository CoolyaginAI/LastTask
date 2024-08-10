package ru.evolenta.goods.repository;

import org.springframework.data.repository.CrudRepository;
import ru.evolenta.goods.model.Good;

public interface GoodRepository extends CrudRepository<Good, Integer> {
}
