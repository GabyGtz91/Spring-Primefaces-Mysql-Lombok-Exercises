package com.shoppingcart.ShoppingCart.servicio;

import com.shoppingcart.ShoppingCart.modelo.Categoria;

import java.util.List;

public interface ICategoriaServicio {
    public List<Categoria> listarCategorias();
    public Categoria buscarCategoriaPorId(Integer idCategoria);
    public void guardarCategoria(Categoria categoria);
    public void eliminarCategoria(Categoria categoria);
}
