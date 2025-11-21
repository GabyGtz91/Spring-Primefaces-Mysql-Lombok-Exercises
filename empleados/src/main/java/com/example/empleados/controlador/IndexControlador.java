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
