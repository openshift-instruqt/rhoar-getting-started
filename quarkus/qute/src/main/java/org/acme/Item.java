package org.acme;

import java.math.BigDecimal;

public class Item {
    public String name;
    public BigDecimal price;

    public Item(BigDecimal price, String name) {
        this.price = price;
        this.name = name;
    }
}
