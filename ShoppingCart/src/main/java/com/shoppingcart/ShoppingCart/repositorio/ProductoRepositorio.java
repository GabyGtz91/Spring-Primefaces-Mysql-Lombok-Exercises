package com.shoppingcart.ShoppingCart.repositorio;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import com.shoppingcart.ShoppingCart.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer>  {
}
