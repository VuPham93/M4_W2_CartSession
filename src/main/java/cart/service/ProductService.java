package cart.service;

import cart.model.Product;
import cart.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductService implements IProductService {
    @Autowired
    private IProductRepository cartRepository;

    @Override
    public Iterable<Product> findAll() {
        return cartRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return cartRepository.findOne(id);
    }

    @Override
    public void save(Product product) {
        cartRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        cartRepository.delete(id);
    }
}
