<%--
    Document   : index
    Created on : 06 may. 2022, 13:38:01
    Author     : dev_manuel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>JSP Page</title>
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

  <!-- Bootstrap CSS -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body>
<%-- jsp comment --%>
<div class="container-fluid">
  <div class="row">
    <h1>Simple Home tools e-commerce</h1>
  </div>
  <div class="row">
    <table class="table">
      <thead>
      <tr>
        <th>Name</th>
        <th>Price</th>
        <th>Quantity</th>
        <th>Buy</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="product" items="${products}">
        <tr>
          <td><c:out value="${product.name}"/></td>
          <td><c:out value="${product.price}"/></td>
          <td><c:out value="${product.quantity}"/></td>
          <td><a href="/little-commerce/commerce/select?name=${product.name}" class="btn btn-primary">Buy</a></td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
  <div class="row">
    <div class="col-12">
      <h3>Shopping car</h3>
    </div>
    <c:if test="${!sessionScope.productCar.isEmpty()}">
      <div class="col-12">
        <table class="table">
          <thead>
          <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="productInCar" items="${sessionScope.productCar}">
            <tr>
              <td><c:out value="${productInCar.name}"/></td>
              <td><c:out value="${productInCar.price}"/></td>
              <td><c:out value="${productInCar.quantity}"/></td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
      <div class="col-12">
        <a href="/little-commerce/commerce/discard" class="btn btn-success">Clear shopping car</a>
      </div>
      <div class="col-12">
        <a href="/little-commerce/commerce/buy" class="btn btn-warning">Pay Items</a>
      </div>
    </c:if>
  </div>
</div>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
        integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>
