package ru.evolenta.productList.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Good {

    @Id @GeneratedValue
    private int id;

    @NonNull String nameGood; // Наименование товара
    @NonNull String descriptionGood; // Описание товара
    @NonNull int cost; // Цена за одиницу товара
    @NonNull int amount; // количество товара на складе

    public Good(@NonNull String nameGood,
                @NonNull String descriptionGood,
                @NonNull int cost,
                @NonNull int amount) {
        this.nameGood = nameGood;
        this.descriptionGood = descriptionGood;
        this.cost = cost;
        this.amount = amount;
    }

}
