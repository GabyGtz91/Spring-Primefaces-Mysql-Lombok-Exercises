package com.shoppingcart.ShoppingCart.controlador;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import com.shoppingcart.ShoppingCart.servicio.ICategoriaServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Data
@ViewScoped
public class CategoriaOA {
    @Autowired
    ICategoriaServicio categoriaServicio;
    private List<Categoria> categorias;
    private Categoria categoriaSeleccionada;

    @PostConstruct
    public void inicializar() {
        cargarCategorias();
    }

    public void cargarCategorias() {
        this.categorias = categoriaServicio.listarCategorias();
    }

    public void agregarCategoria() {
        this.categoriaSeleccionada = new Categoria();
    }

    public void guardarCategoria() {
        if(this.categoriaSeleccionada.getNombre().isEmpty()){
            mostrarMensaje("Agrega un nombre de categoria valido");
        } else {
            if(this.categoriaSeleccionada.getId() == null) {
                this.categoriaServicio.guardarCategoria(this.categoriaSeleccionada);
                this.categorias.add(this.categoriaSeleccionada);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria Agregada"));
            } else {
                this.categoriaServicio.guardarCategoria(this.categoriaSeleccionada);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria Actualizada"));
            }

            PrimeFaces.current().executeScript("PF('categoriaDlg').hide()");
            PrimeFaces.current().ajax().update("categoriasFrm:mensajes", "categoriasFrm:categorias-data-table");
            this.categoriaSeleccionada = null;
        }
    }

    public void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        PrimeFaces.current().ajax().update("categoriasFrm:mensajes");
    }

    public void seleccionarCategoria(SelectEvent<Categoria> event) {
        this.categoriaSeleccionada = event.getObject();
    }

    public void eliminarCategoria() {
        if(this.categoriaSeleccionada.getId() != null) {
            this.categoriaServicio.eliminarCategoria(this.categoriaSeleccionada);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Categoria Eliminada"));
            this.categorias.remove(this.categoriaSeleccionada);
            PrimeFaces.current().ajax().update("categoriasFrm:mensajes", "categoriasFrm:categorias-data-table");
            this.categoriaSeleccionada = null;
        }
    }
}
