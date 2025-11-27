package com.example.empleados.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private String correo;
    //private Integer edad;
    private Character genero;
    private String telefono;
    private Date fechaNacimiento;
    private String direccion;


    @Transient
    public String getGeneroDesc() {
        if (genero == null) return "";
        return genero == 'M' ? "Masculino" : "Femenino";
    }
}
