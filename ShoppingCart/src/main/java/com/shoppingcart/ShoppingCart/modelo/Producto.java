package com.shoppingcart.ShoppingCart.modelo;

import jakarta.persistence.*;
import lombok.*;

import java.security.PrivateKey;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Double precio;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
}
