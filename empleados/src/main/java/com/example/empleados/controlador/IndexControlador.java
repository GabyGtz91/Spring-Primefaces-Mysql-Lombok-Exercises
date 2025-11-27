package com.example.empleados.controlador;

import com.example.empleados.modelo.Empleado;
import com.example.empleados.servicio.IEmpleadoServicio;
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
public class IndexControlador { //Controlador de la vista
    @Autowired
    IEmpleadoServicio empleadoServicio;
    private List<Empleado> empleados;
    private Empleado empleadoSeleccionado;
    private static final String EMAIL_REGEX = "^[\\w.%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    @PostConstruct
    public void inicializar() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.empleados = empleadoServicio.listaEmpleados();
    }

    public void agregarEmpleado() {
        this.empleadoSeleccionado = new Empleado();
    }

    public void guardarEmpleado(){

        if(this.empleadoSeleccionado.getNombre().isEmpty()){
            mostrarMensaje("Agrega un nombre valido");
        } else if (this.empleadoSeleccionado.getApellido().isEmpty()){
            mostrarMensaje("Agrega un apellido valido");
        } else if (this.empleadoSeleccionado.getCorreo().isEmpty() || !this.empleadoSeleccionado.getCorreo().matches(EMAIL_REGEX)){
            mostrarMensaje("Agrega un correo valido");
        } /*else if (this.empleadoSeleccionado.getEdad() == null || this.empleadoSeleccionado.getEdad() == 0) {
            mostrarMensaje("Agrega una edad valida");
        } */else if (this.empleadoSeleccionado.getGenero() == null) {
            mostrarMensaje("Selecciona el genero");
        } else if (this.empleadoSeleccionado.getTelefono().isEmpty()) {
            mostrarMensaje("Agrega un telefono valido");
        } else if (this.empleadoSeleccionado.getFechaNacimiento() == null) {
            mostrarMensaje("Selecciona la fecha de nacimiento");
        } else if (this.empleadoSeleccionado.getDireccion().isEmpty()) {
            mostrarMensaje("Agrega una direccion valida");
        } else {
            if(this.empleadoSeleccionado.getId() == null) {
                this.empleadoServicio.guardarEmpleado(this.empleadoSeleccionado);
                this.empleados.add(this.empleadoSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empleado Agregado"));
            } else {
                this.empleadoServicio.guardarEmpleado(this.empleadoSeleccionado);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empleado Actualizado"));
            }

            //Ocultar la ventana modal
            PrimeFaces.current().executeScript("PF('ventanaModalEmpleado').hide()");
            //Actualizar la tabla usando ajax
            PrimeFaces.current().ajax().update("forma-empleados:mensajes", "forma-empleados:empleados-data-table");
            //Reset del objeto empleado seleccionado
            this.empleadoSeleccionado = null;
        }
    }

    public void mostrarMensaje(String mensaje) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(mensaje));
        PrimeFaces.current().ajax().update("forma-empleados:mensajes");
    }

    public void seleccionarEmpleado(SelectEvent<Empleado> event) {
        this.empleadoSeleccionado = event.getObject();
        //System.out.println("Empleado seleccionado: " + empleadoSeleccionado.getId());
        log.info("Empleado seleccionado: ", this.empleadoSeleccionado.getId());
    }

    public void eliminarEmpleado() {
        if(this.empleadoSeleccionado.getId() != null) {
            this.empleadoServicio.eliminarEmpleado(this.empleadoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empleado Eliminado"));
            this.empleados.remove(this.empleadoSeleccionado);
            //Actualizar la tabla usando ajax
            PrimeFaces.current().ajax().update("forma-empleados:mensajes", "forma-empleados:empleados-data-table");
            //Reset del objeto empleado seleccionado
            this.empleadoSeleccionado = null;
        }
    }
}
