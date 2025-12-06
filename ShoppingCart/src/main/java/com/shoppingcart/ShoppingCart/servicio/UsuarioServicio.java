package com.shoppingcart.ShoppingCart.servicio;

import com.shoppingcart.ShoppingCart.modelo.Usuario;
import com.shoppingcart.ShoppingCart.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements IUsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioRepositorio.save(usuario);
    }

    @Override
    public boolean buscarUsuarioPorCorreo(String correo) {
        return usuarioRepositorio.existsByCorreo(correo);
    }

    @Override
    public boolean buscarUsuarioCorreoPwd(String correo, String password) {
        return usuarioRepositorio.existsByCorreoAndPassword(correo, password);
    }


}
