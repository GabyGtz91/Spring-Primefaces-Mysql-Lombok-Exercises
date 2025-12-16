package com.shoppingcart.ShoppingCart.controlador;

import com.shoppingcart.ShoppingCart.modelo.Usuario;
import com.shoppingcart.ShoppingCart.servicio.IUsuarioServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
@ViewScoped
public class UsuarioOA {
    @Autowired
    IUsuarioServicio usuarioServicio;

    private Usuario usuario;

    @PostConstruct
    public void inicializar() {
        this.usuario = new Usuario();
    }

    public void guardarUsuario() {
        boolean existe = this.usuarioServicio.existeUsuarioPorCorreo(this.usuario.getCorreo());
        if(existe) {
            mostrarMensaje("El correo ya se encuentra registrado");
        } else {
            this.usuario.setPassword(hashPassword(this.usuario.getPassword()));
            this.usuarioServicio.guardarUsuario(this.usuario);
        }
    }

    public void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,mensaje,mensaje));
        PrimeFaces.current().ajax().update("usuariosFrm:mensajes");
    }

    public void limpiarFormulario() {
        this.usuario = new Usuario();
        PrimeFaces.current().ajax().update("usuariosFrm");
    }

    public void iniciarSesion() {
        if (this.usuario.getCorreo().isEmpty() || this.usuario.getPassword().isEmpty()) {
            mostrarMensaje("Indica usuario y contraseña");
        } else {
            boolean existe = this.usuarioServicio.existeUsuarioPorCorreo(this.usuario.getCorreo());

            if(existe) {
                Usuario userBD = this.usuarioServicio.buscarUsuarioPorCorreo(this.usuario.getCorreo());

                if(checkPassword(this.usuario.getPassword(), userBD.getPassword())) {
                    try {
                        FacesContext.getCurrentInstance().getExternalContext()
                                .redirect("carrito2.xhtml");
                    } catch (Exception e) {
                        log.info("Error: " + e);
                    }
                } else {
                    mostrarMensaje("Usuario o contraseña invalidos");
                }
            } else {
                mostrarMensaje("Usuario o contraseña invalidos");
            }
        }
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }
}
