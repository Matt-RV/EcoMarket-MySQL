package com.gestionPedidos.GestionPedidos.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {

    @Id
    private Integer idCliente;

    @Column(length = 50, nullable = false)
    private String nombreCliente;

    @Column(length = 100, nullable = false)
    private String apellidosCliente;

    @Column(length = 100, nullable = false, unique = true)
    private String emailCliente;

    @Column(length = 150, nullable = false)
    private String direccionCliente;
    
    
}
