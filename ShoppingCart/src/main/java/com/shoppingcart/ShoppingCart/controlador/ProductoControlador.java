package com.shoppingcart.ShoppingCart.controlador;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import com.shoppingcart.ShoppingCart.modelo.Producto;
import com.shoppingcart.ShoppingCart.servicio.ICategoriaServicio;
import com.shoppingcart.ShoppingCart.servicio.IProductoServicio;
import com.shoppingcart.ShoppingCart.servicio.ProductoServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
        listarCategorias();
    }

    public void listarCategorias() {
        this.categorias = categoriaServicio.listarCategorias();
    }
}
