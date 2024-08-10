package ru.evolenta.productList.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.HashMap;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductList {

    @Id @GeneratedValue
    private int id;

    @NonNull Date dateProductList; // Дата заказа
    @NonNull String status; // Статус заказа -> "В пути"/"Доставлен"
    @NonNull int idPerson; // id Пользователя офрмившего заказ
    @NonNull HashMap<Integer, Integer> productListMap; // Для хранения <номер товара, количество товара> в заказе

    public ProductList(@NonNull Date dateProductList,
                 @NonNull String status,
                 @NonNull int idPerson,
                 @NonNull HashMap<Integer, Integer> productListMap) {
        this.dateProductList = dateProductList;
        this.status = status;
        this.idPerson = idPerson;
        this.productListMap = productListMap;
    }

}
