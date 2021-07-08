package org.acme;

import java.math.BigDecimal;
import io.quarkus.qute.TemplateExtension;

@TemplateExtension
public class TemplateExtensions {

    public static BigDecimal discountedPrice(Item item) {
        return item.price.multiply(new BigDecimal("0.9"));
    }
}
