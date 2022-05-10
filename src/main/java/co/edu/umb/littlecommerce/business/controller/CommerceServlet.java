package co.edu.umb.littlecommerce.business.controller;

import co.edu.umb.littlecommerce.business.lasting.ERoute;
import co.edu.umb.littlecommerce.domain.entity.Product;
import co.edu.umb.littlecommerce.domain.repository.ProductRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "commerceServlet",
  urlPatterns = {
    ERoute.Commerce.BASE,
    ERoute.Commerce.BUY,
    ERoute.Commerce.DISCARD,
    ERoute.Commerce.SELECT
  }
)
public class CommerceServlet extends HttpServlet {

  private ProductRepository productRepository = new ProductRepository();

  /**
   * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
   * methods.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  protected void processRequest(HttpServletRequest request,
                                HttpServletResponse response) {
    response.setContentType("text/html;charset=UTF-8");
    try {
      var path = switch (request.getServletPath()) {
        case ERoute.Commerce.BASE -> initPage(request);
        case ERoute.Commerce.SELECT -> selectProduct(request);
        case ERoute.Commerce.DISCARD -> discardProduct(request);
        case ERoute.Commerce.BUY -> buyProducts(request);
        default -> throw new AssertionError();
      };
      request.getRequestDispatcher(path).forward(request, response);
    } catch (IOException | ServletException | CloneNotSupportedException e) {
      e.printStackTrace(System.err);
    }
  }

  private String buyProducts(HttpServletRequest request) {
    List<Product> productsCar = (List<Product>) request.getSession().getAttribute("productCar");
    productsCar.clear();
    request.setAttribute("productCar", productsCar);
    return ERoute.Commerce.BASE;
  }

  private String discardProduct(HttpServletRequest request) {
    List<Product> productsBought = (List<Product>) request.getSession().getAttribute("productCar");
    productRepository.returnProducts(productsBought);
    request.getSession().setAttribute("productCar", new ArrayList<>());
    return ERoute.Commerce.BASE;
  }

  private String selectProduct(HttpServletRequest request) throws CloneNotSupportedException {
    List<Product> productCar = (List<Product>) request.getSession().getAttribute("productCar");
    String name = request.getParameter("name");
    Product product = getProductFromInventory(name);

    Optional<Product> productInShoppingCar = productCar.stream()
      .filter(p -> p.getName().equals(product.getName()))
      .findFirst();
    if (productInShoppingCar.isPresent()) {
      productInShoppingCar.get().setQuantity(productInShoppingCar.get().getQuantity() + 1);
      productRepository.buyProduct(name);
      request.getSession().setAttribute("productCar", productCar);
      return ERoute.Commerce.BASE;
    }
    productCar.add(product);
    productCar.forEach(p -> {
      if (p.getName().equals(product.getName())) {
        p.setQuantity(1);
      }
    });
    productRepository.buyProduct(name);
    request.getSession().setAttribute("productCar", productCar);
    return ERoute.Commerce.BASE;
  }

  private Product getProductFromInventory(String name) throws CloneNotSupportedException {
    Product product = productRepository.getProducts()
      .stream()
      .filter(p -> p.getName().equals(name))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException("Product not found"));
    return (Product) product.clone();
  }

  private String initPage(HttpServletRequest request) {
    List<Product> productCar = (List<Product>) request.getSession().getAttribute("productCar");
    if (productCar == null) {
      productCar = new ArrayList<>();
    }
    request.getSession().setAttribute("productCar", productCar);
    request.setAttribute("products", productRepository.getProducts());
    return "/index.jsp";
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

  /**
   * Handles the HTTP <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP <code>POST</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}