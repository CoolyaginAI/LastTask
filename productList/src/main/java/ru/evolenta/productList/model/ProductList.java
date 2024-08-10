package ru.evolenta.productList.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.Map;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductList {

    @Id @GeneratedValue
    private int id;

    @NonNull Date dateOrder; // Дата заказа
    @NonNull String status; // Статус заказа -> "В пути"/"Доставлен"
    @NonNull int idUser; // id Пользователя офрмившего заказ
    @NonNull Map<Integer, Integer> orderMap; // Для хранения <номер товара, количество товара> в заказе

    public ProductList(@NonNull Date dateOrder,
                 @NonNull String status,
                 @NonNull int idUser,
                 @NonNull Map<Integer, Integer> orderMap) {
        this.dateOrder = dateOrder;
        this.status = status;
        this.idUser = idUser;
        this.orderMap = orderMap;
    }

}
