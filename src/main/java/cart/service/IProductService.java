package cart.service;

import cart.model.Product;

public interface IProductService {
    Iterable<Product> findAll();

    Product findById(Long id);

    void save(Product product);

    void deleteProduct(Long id);
}
