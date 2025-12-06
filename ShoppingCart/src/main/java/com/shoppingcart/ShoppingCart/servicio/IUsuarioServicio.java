package com.shoppingcart.ShoppingCart.servicio;


import com.shoppingcart.ShoppingCart.modelo.Usuario;

public interface IUsuarioServicio {
    public void guardarUsuario(Usuario usuario);
    public boolean buscarUsuarioPorCorreo(String correo);
    public boolean buscarUsuarioCorreoPwd(String correo, String password);
}
