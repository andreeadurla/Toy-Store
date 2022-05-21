package ro.sd.a2.service;

import ro.sd.a2.dto.ShoppingCartDetailsDTO;

import java.util.List;

public class ShoppingCart {

    public static float computeTotalPrice(List<ShoppingCartDetailsDTO> shoppingCartDetailsDTOs) {

        float price = shoppingCartDetailsDTOs.stream()
                .map(item -> item.getPrice())
                .reduce(0.0f, (price1, price2) -> price1 + price2);

        return price;
    }

}
