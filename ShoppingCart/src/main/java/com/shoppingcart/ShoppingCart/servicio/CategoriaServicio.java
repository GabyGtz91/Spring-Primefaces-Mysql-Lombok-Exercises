package com.shoppingcart.ShoppingCart.servicio;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import com.shoppingcart.ShoppingCart.repositorio.CategoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServicio implements ICategoriaServicio {
    @Autowired
    private CategoriaRepositorio categoriaRepositorio;

    @Override
    public List<Categoria> listarCategorias() {
        return categoriaRepositorio.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(Integer idCategoria) {
        return categoriaRepositorio.findById(idCategoria).orElse(null);
    }

    @Override
    public void guardarCategoria(Categoria categoria) {
        categoriaRepositorio.save(categoria);
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        categoriaRepositorio.delete(categoria);
    }
}
