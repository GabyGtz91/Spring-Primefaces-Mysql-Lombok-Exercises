package com.example.empleados.controlador;

import com.example.empleados.modelo.Empleado;
import com.example.empleados.servicio.IEmpleadoServicio;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Data;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@ViewScoped
public class IndexControlador { //Controlador de la vista
    @Autowired
    IEmpleadoServicio empleadoServicio;
    private List<Empleado> empleados;
    private Empleado empleadoSeleccionado;

    @PostConstruct
    public void inicializar() {
        cargarDatos();
    }

    public void cargarDatos() {
        this.empleados = empleadoServicio.listaEmpleados();
    }

    public void agregarEmpleado() {
        empleadoSeleccionado = new Empleado();
    }

    public void guardarEmpleado(){
        if(this.empleadoSeleccionado.getId() == null) {
            this.empleadoServicio.guardarEmpleado(this.empleadoSeleccionado);
            this.empleados.add(empleadoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Empleado Agregado"));
        }

        //Ocultar la ventana modal
        PrimeFaces.current().executeScript("PF('ventanaModalEmpleado').hide()");
        //Actualizar la tabla usando ajax
        PrimeFaces.current().ajax().update("forma-empleados:mensajes", "forma-empleados:empleados-data-table");
        //Reset del objeto clientes seleccionado
        this.empleadoSeleccionado = null;
    }
}
