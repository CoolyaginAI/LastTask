package ru.evolenta.persons.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person {

    @Id @GeneratedValue
    private int id;

    @NonNull String firstName;
    @NonNull String surName;
    @NonNull String lastName;
    @NonNull LocalDate bithday;
    @NonNull String adress;

    public Person(@NonNull String firstName,
                @NonNull String surName,
                @NonNull String lastName,
                @NonNull LocalDate bithday,
                @NonNull String adress) {
        this.firstName = firstName;
        this.surName = surName;
        this.lastName = lastName;
        this.bithday = bithday;
        this.adress = adress;
    }

}
