package com.shoppingcart.ShoppingCart.repositorio;

import com.shoppingcart.ShoppingCart.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Integer> {
    public boolean existsByCorreo(String correo);
    public Usuario findUsuarioByCorreo(String correo);
}
