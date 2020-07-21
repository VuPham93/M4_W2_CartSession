package cart.controller;

import cart.model.Cart;
import cart.model.Product;
import cart.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes("cart")
@RequestMapping("/cart")
public class CartController {
    @ModelAttribute("cart")
    public Cart setUpCart() {
        return new Cart();
    }

    @Autowired
    private IProductService productService;

    @GetMapping
    public ModelAndView listProduct() {
        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("listProduct", productService.findAll());
        return modelAndView;
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("/detail");
        modelAndView.addObject("product", productService.findById(id));
        return modelAndView;
    }

    @GetMapping("/getList")
    public ResponseEntity<Iterable<Product>> getList() {
        Iterable<Product> products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> create(@RequestBody Product product) {
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> edit(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        productService.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/detail/addToCart/{id}/")
    public String addToCart(@ModelAttribute("cart") Cart cart, @PathVariable Long id, @RequestParam int number) {
        cart.setSelectedProduct(id, number);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{productId}")
    public String removeFromCart(@ModelAttribute("cart") Cart cart, @PathVariable Long productId) {
        cart.removeProduct(productId);
        return "redirect:/cart/toCart";
    }

    @GetMapping("/toCart")
    public ModelAndView getCartList(@ModelAttribute("cart") Cart cart) {
        ModelAndView modelAndView = new ModelAndView("/cart");
        Map<Long, Integer> hashMap = cart.getSelectedProduct();
        Map<Product, Integer> selectedProduct = new HashMap<>();

        for (Map.Entry<Long, Integer> product: hashMap.entrySet()) {
            selectedProduct.put(productService.findById(product.getKey()), product.getValue());
        }

        modelAndView.addObject("selectedProduct", selectedProduct);
        return modelAndView;
    }

    @GetMapping("/changeNumber/{id}")
    public ModelAndView changeNumber(@ModelAttribute("cart") Cart cart, @PathVariable Long id, @RequestParam int number) {
        cart.setSelectedProduct(id, number);
        return new ModelAndView("redirect:/cart/toCart");
    }

}
