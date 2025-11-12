package com.example.empleados.repositorio;

import com.example.empleados.modelo.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

//Interfaz que tiene todos los metodos hacia la BD
public interface EmpleadoRepositorio extends JpaRepository<Empleado, Integer> {
}
