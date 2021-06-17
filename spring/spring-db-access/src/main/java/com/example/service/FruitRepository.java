package com.example.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitRepository extends CrudRepository<Fruit, Long> {
    List<Fruit> findByName(String name);

    default List<Fruit> findAllFruitsByName(String name) {
        return findByName(name);
    }

    @Query("select f from Fruit f where f.name like %?1")
    List<Fruit> findByNameLike(String name);
}
