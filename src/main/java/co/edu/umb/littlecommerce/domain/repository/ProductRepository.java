package co.edu.umb.littlecommerce.domain.repository;

import co.edu.umb.littlecommerce.domain.entity.Product;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProductRepository {

  private List<Product> products;

  public ProductRepository() {
    this.products = new ArrayList<>();
    products.add(
      Product.builder()
        .name("Martillo")
        .price(12500.0)
        .quantity(100)
        .build()
    );
    products.add(
      Product.builder()
        .name("Destornillador Estrella")
        .price(14000.0)
        .quantity(100)
        .build()
    );
    products.add(
      Product.builder()
        .name("Destornillador Pala")
        .price(14000.0)
        .quantity(100)
        .build()
    );
    products.add(
      Product.builder()
        .name("Multimetro")
        .price(75000.0)
        .quantity(100)
        .build()
    );
  }

  public void buyProduct(String name) {
    Product productFound = products.stream()
      .filter(p -> p.getName().equals(name))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("Product not found"));
    productFound.setQuantity(productFound.getQuantity() - 1);
  }

  public void returnProducts(List<Product> productsBought) {
    productsBought.stream().forEach(pb -> {
      Product product = products.stream()
        .filter(p -> p.getName().equals(pb.getName()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Product not found"));
      if (product.getQuantity() <100) {
        product.setQuantity(100);
      }
    });
  }

}
