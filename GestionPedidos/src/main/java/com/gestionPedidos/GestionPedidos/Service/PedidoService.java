package com.gestionPedidos.GestionPedidos.Service;

import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@Transactional

public class PedidoService {
    
    @Autowired
    private PedidoRepository pedidoRepository;

    // Método para listar todos los pedidos
    public List<Pedido> findAll() { 
        return pedidoRepository.findAll();
    }

    // Método para buscar un pedido por su ID
    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).get();
    }

    // Método para guardar un pedido
    public Pedido save(Pedido pedido) { 
        return pedidoRepository.save(pedido);
    }

    // Método para eliminar un pedido por su ID
    public void delete(Integer id) { 
        pedidoRepository.deleteById(id);
    }

    // Método para contar pedidos
    public Long count() {
        return pedidoRepository.count();
    }

    // Método para buscar pedidos por estado
    public List<Pedido> findByEstado(String estado) { 
        return pedidoRepository.findByEstado(estado);
    }

    // Método para buscar pedidos por fecha de creación
    public List<Pedido> findByFechaCreacion(Date fechaCreacion) { 
        return pedidoRepository.findByFechaCreacion(fechaCreacion);
    }

    // Método para buscar pedidos por cliente
    public List<Pedido> findByIdCliente(Integer idCliente) { 
        return pedidoRepository.findByIdCliente(idCliente);
    }
}    