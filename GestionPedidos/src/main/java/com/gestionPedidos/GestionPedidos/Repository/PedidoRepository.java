package com.gestionPedidos.GestionPedidos.Repository;

import com.gestionPedidos.GestionPedidos.Model.Pedido;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    // Método para buscar pedidos por estado
    List<Pedido> findByEstado(String estado); 

    // Método para buscar pedidos por fecha de creación
    List<Pedido> findByFechaCreacion(Date fechaCreacion); 

    // Método para buscar pedidos por ID del cliente
    List<Pedido> findByIdCliente(Integer idCliente); 
    


}
