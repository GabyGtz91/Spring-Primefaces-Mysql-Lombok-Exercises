package com.shoppingcart.ShoppingCart.servicio;


import com.shoppingcart.ShoppingCart.modelo.Usuario;

public interface IUsuarioServicio {
    public void guardarUsuario(Usuario usuario);
    public boolean existeUsuarioPorCorreo(String correo);
    public Usuario buscarUsuarioPorCorreo(String correo);
}
