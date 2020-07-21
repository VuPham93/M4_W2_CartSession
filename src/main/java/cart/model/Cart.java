package cart.model;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    private final Map<Long, Integer> selectedProduct = new HashMap<>();

    public Map<Long, Integer> getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Long productId, int number) {
        selectedProduct.put(productId, number);
    }

    public void removeProduct(Long productId) {
        selectedProduct.remove(productId);
    }
}
