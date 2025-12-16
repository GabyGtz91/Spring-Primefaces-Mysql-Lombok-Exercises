package com.shoppingcart.ShoppingCart.controlador;

import com.shoppingcart.ShoppingCart.modelo.Categoria;
import com.shoppingcart.ShoppingCart.modelo.Producto;
import com.shoppingcart.ShoppingCart.servicio.ICategoriaServicio;
import com.shoppingcart.ShoppingCart.servicio.IProductoServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.file.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Component
@Data
@ViewScoped
public class ProductoOA {
    @Autowired
    IProductoServicio productoServicio;
    private List<Producto> productos;
    private Producto productoSeleccionado;
    @Autowired
    ICategoriaServicio categoriaServicio;
    private List<Categoria> categorias;
    private UploadedFile imagen;
    private static final String RUTA_FISICA_IMAGENES =
            "C:\\GitPersonal\\images\\";
    private static final String RUTA_WEB_IMAGENES = "productos/";
    private String rutaImagenSeleccionada;
    private boolean soloLectura;

    @PostConstruct
    public void inicializar() {
        cargarProductos();
    }

    public void cargarProductos() {
        this.productos = this.productoServicio.listarProductos();
    }

    public void agregarProducto() {
        this.productoSeleccionado = new Producto();
        this.soloLectura = false;
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
        } /*else if(this.imagen == null) {
            mostrarMensaje("Agrega una imagen para el producto");
        }*/ else {
            if (this.productoSeleccionado.getId() == null) {
                guardarImagen();
                this.productoServicio.guardarProducto(this.productoSeleccionado);

                //probar despues con el converter
                Categoria categoria = this.categoriaServicio.buscarCategoriaPorId(this.productoSeleccionado.getCategoria().getId());
                this.productoSeleccionado.setCategoria(categoria);
                this.productos.add(this.productoSeleccionado);

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto agregado"));
            } else {
                if(this.imagen != null) {
                    eliminarImagen();
                    guardarImagen();
                }

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
            if(productoSeleccionado.getImagen() != null) {
                eliminarImagen();
            }
            this.productoServicio.eliminarProducto(this.productoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Producto Eliminado"));
            this.productos.remove(this.productoSeleccionado);
            PrimeFaces.current().ajax().update("productosFrm:mensajes", "productosFrm:productos-data-table");
            this.productoSeleccionado = null;
        }
    }

    public void mostrarImagen(Producto producto) {
        if (producto != null) {
            this.rutaImagenSeleccionada = producto.getImagen();
        }
    }

    public void eliminarImagen() {
        if (this.productoSeleccionado == null) {
            return;
        }

        try {
            Path path = Paths.get(RUTA_FISICA_IMAGENES).resolve(this.productoSeleccionado.getImagen());

            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (Exception e) {
            log.error("Error eliminando imagen ", e);
        }
    }

    public void guardarImagen() {
        try {
            String nombreArchivo = System.currentTimeMillis() + "_" + imagen.getFileName();
            Path directorio = Paths.get(RUTA_FISICA_IMAGENES+RUTA_WEB_IMAGENES).resolve(nombreArchivo);
            Files.copy(imagen.getInputStream(), directorio);

            this.productoSeleccionado.setImagen(RUTA_WEB_IMAGENES + nombreArchivo);
        } catch (Exception e) {
            log.error("Error eliminando imagen ", e);
        }
    }

    public void editarProducto(){
        this.soloLectura = false;
    }

    public void verProducto() {
        this.soloLectura = true;
    }
}

