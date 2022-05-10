package co.edu.umb.littlecommerce.domain.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product implements Cloneable{
  private String name;
  private Double price;
  private Integer quantity;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
