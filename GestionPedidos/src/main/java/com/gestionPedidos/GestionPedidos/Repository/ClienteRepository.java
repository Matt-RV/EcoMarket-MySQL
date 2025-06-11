package com.gestionPedidos.GestionPedidos.Repository;

import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Model.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    // Método para buscar un cliente por su nombre
    Cliente findByNombreConstraingIgnoreCase(String nombre); // Método para buscar un cliente por su nombre, ignorando mayúsculas y minúsculas

    // Método para buscar un cliente por su email
    Cliente findByEmailConstraingIgnoreCase(String email); // Método para buscar un cliente por su email, ignorando mayúsculas y minúsculas
    
    List<Pedido> findByCliente_IdCliente(Integer idCliente);
}
