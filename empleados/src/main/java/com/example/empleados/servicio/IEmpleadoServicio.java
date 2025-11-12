package com.example.empleados.servicio;

import com.example.empleados.modelo.Empleado;

import java.util.List;

public interface IEmpleadoServicio { //Interface para empleados
    public List<Empleado> listaEmpleados();
    public Empleado buscarEmpleadoPorId(Integer idEmpleado);
    public void guardarEmpleado(Empleado empleado);
    public void eliminarEmpleado(Empleado empleado);
}
