package com.shoppingcart.ShoppingCart.repositorio;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepositorio extends JpaRepository<Categoria, Integer> {
}
