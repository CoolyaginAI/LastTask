package ru.evolenta.goods.model;

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

    @NonNull String nameGood;
    @NonNull String descriptionGood;
    @NonNull int cost;
    @NonNull int amount;

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
