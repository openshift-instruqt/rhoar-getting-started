package org.acme.person.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Entity
public class Person extends PanacheEntity {
    // the person's name
    public String name;

    // the person's birthdate
    public LocalDate birth;

    // the person's eye color
    @Enumerated(EnumType.STRING)
    @Column(length = 8)
    public EyeColor eyes;

    public Person() {
    }

    public Person(String name, LocalDate birth, EyeColor eyes) {
        this.name = name;
        this.birth = birth;
        this.eyes = eyes;
    }

    public static Uni<List<Person>> findByColor(EyeColor color) {
        return list("eyes", color);
    }

    public static Multi<Person> getBeforeYear(int year) {
        return Person.<Person>streamAll()
          .filter(p -> p.birth.getYear() <= year);
    }
}
