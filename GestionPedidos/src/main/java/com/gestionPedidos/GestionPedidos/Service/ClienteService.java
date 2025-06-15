package com.gestionPedidos.GestionPedidos.Service;

import com.gestionPedidos.GestionPedidos.Model.*;
import com.gestionPedidos.GestionPedidos.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import java.util.List;


@Service
@Transactional

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findByCliente(Integer idCliente) { 
        return clienteRepository.findById(idCliente).get();
    }

    public Cliente save(Cliente cliente) { 
        return clienteRepository.save(cliente);
    }

    public void delete(Integer idCliente) { 
        clienteRepository.deleteById(idCliente);
    }

    // Método para contar pedidos
    public Long count() {
        return clienteRepository.count();
    }

    // Método para buscar clientes por nombre
    public List<Cliente> findByNombreContainingIgnoreCase(String nombre) { 
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Método para buscar clientes por email
    public List<Cliente> findByEmailConstraingIgnoreCase(String emailCliente) { 
        return clienteRepository.findByEmailContainingIgnoreCase(emailCliente);
    }

}
