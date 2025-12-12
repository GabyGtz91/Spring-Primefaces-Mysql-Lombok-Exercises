package com.shoppingcart.ShoppingCart.controlador;

import com.shoppingcart.ShoppingCart.modelo.Producto;
import com.shoppingcart.ShoppingCart.servicio.IProductoServicio;
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
public class CarritoOA {
    @Autowired
    IProductoServicio productoServicio;
    private List<Producto> productos;

    @PostConstruct
    public void inicializar() {
        cargarProductos();
    }

    public void cargarProductos() {
        this.productos = this.productoServicio.listarProductos();
    }
}
