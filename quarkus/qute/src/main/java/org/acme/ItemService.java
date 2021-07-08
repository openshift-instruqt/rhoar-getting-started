package org.acme;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemService {

    private Map<Integer, Item> items = Map.of(
        1, new Item(new BigDecimal(1.99), "Apple"),
        2, new Item(new BigDecimal(2.99), "Pear"),
        3, new Item(new BigDecimal(3.99), "Grape"),
        4, new Item(new BigDecimal(129.99), "Mango")
    );

    public Item findItem(int id) {
        return items.get(id);
    }
}
