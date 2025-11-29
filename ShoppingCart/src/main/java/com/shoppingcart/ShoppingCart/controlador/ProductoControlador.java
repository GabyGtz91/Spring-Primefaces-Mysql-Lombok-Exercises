package com.shoppingcart.ShoppingCart.controlador;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import com.shoppingcart.ShoppingCart.modelo.Producto;
import com.shoppingcart.ShoppingCart.servicio.ICategoriaServicio;
import com.shoppingcart.ShoppingCart.servicio.IProductoServicio;
import com.shoppingcart.ShoppingCart.servicio.ProductoServicio;
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
public class ProductoControlador {
    @Autowired
    IProductoServicio productoServicio;
    private List<Producto> productos;
    private Producto productoSeleccionado;
    @Autowired
    ICategoriaServicio categoriaServicio;
    private List<Categoria> categorias;

    @PostConstruct
    public void inicializar() {
        cargarProductos();
    }

    public void cargarProductos() {
        this.productos = productoServicio.listarProductos();
    }

    public void agregarProducto() {
        this.productoSeleccionado = new Producto();
        this.productoSeleccionado.setCategoria(new Categoria());
        listarCategorias();
    }

    public void listarCategorias() {
        this.categorias = categoriaServicio.listarCategorias();
    }

    public void guardarProducto() {
        if (this.productoSeleccionado.getNombre().isEmpty()) {
            mostrarMensaje("Indica el nombre del producto");
        } else if (this.productoSeleccionado.getPrecio() == null) {
            mostrarMensaje("Indica el precio");
        } else if (this.productoSeleccionado.getCategoria().getId() == null) {
            mostrarMensaje("Indica la categoria");
        } else {
            if (this.productoSeleccionado.getId() == null) {
                this.productoServicio.guardarProducto(this.productoSeleccionado);

                //probar despues con el converter
                Categoria categoria = this.categoriaServicio.buscarCategoriaPorId(this.productoSeleccionado.getCategoria().getId());
                this.productoSeleccionado.setCategoria(categoria);
                this.productos.add(this.productoSeleccionado);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto agregado"));
            } else {
                this.productoServicio.guardarProducto(this.productoSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Actualizado"));

                //probar despues con el converter
                Categoria categoria = this.categoriaServicio.buscarCategoriaPorId(this.productoSeleccionado.getCategoria().getId());
                this.productoSeleccionado.setCategoria(categoria);
            }

            PrimeFaces.current().executeScript("PF('productoDlg').hide()");
            PrimeFaces.current().ajax().update("productosFrm:mensajes", "productosFrm:productos-data-table");
            this.productoSeleccionado = null;
        }
    }
    
    public void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        PrimeFaces.current().ajax().update("productosFrm:mensajes");
    }

    public void seleccionarProducto(SelectEvent<Producto> event) {
        this.productoSeleccionado = event.getObject();
        listarCategorias();
    }

    public void eliminarProducto() {
        if(this.productoSeleccionado.getId() != null) {
            this.productoServicio.eliminarProducto(this.productoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Eliminado"));
            this.productos.remove(this.productoSeleccionado);
            PrimeFaces.current().ajax().update("productosFrm:mensajes", "productosFrm:productos-data-table");
            this.productoSeleccionado = null;
        }
    }
}
