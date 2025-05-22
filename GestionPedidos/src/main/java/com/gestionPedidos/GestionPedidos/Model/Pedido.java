package com.gestionPedidos.GestionPedidos.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido; // ID del pedido

    @Column(nullable=false)
    private Date fechaCreacion; // Se coloca Date porque la base de datos funciona con este y no con LocalDate.
    // (YYYY-MM-DD)

    @Column(nullable=false)
    private String estado; // "Pendiente", "Entregado", "Cancelado".
    
    @Column(nullable=false)
    private double total; // No me interesa el decimal.

    
    @Column(name = "idCliente", nullable = false)
    private Integer idCliente; // ID del cliente que hace el pedido.



}
