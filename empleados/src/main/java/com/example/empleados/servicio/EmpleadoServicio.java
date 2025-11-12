package com.example.empleados.servicio;

import com.example.empleados.modelo.Empleado;
import com.example.empleados.repositorio.EmpleadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoServicio implements IEmpleadoServicio { //Implementa metodos propios de empleado
    @Autowired
    private EmpleadoRepositorio empleadoRepositorio; //Se trae todos los metodos ya creados por JPA con la interface intermediaria

    @Override
    public List<Empleado> listaEmpleados() {
        return empleadoRepositorio.findAll();
    }

    @Override
    public Empleado buscarEmpleadoPorId(Integer idEmpleado) {
        return empleadoRepositorio.findById(idEmpleado).orElse(null);
    }

    @Override
    public void guardarEmpleado(Empleado empleado) {
        empleadoRepositorio.save(empleado);
    }

    @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepositorio.delete(empleado);
    }
}
