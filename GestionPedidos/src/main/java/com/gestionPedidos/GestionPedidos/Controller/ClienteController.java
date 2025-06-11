package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Repository.ClienteRepository;
import com.gestionPedidos.GestionPedidos.Service.ClienteService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Service
@Transactional
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // Método para listar a todos los clientes
    @GetMapping("/api/v1/clientes")
    public List<Cliente> listar() { 
        return clienteRepository.findAll();
    }

    // Método para buscar un cliente por su ID.
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id) { 
        try{ 
            Cliente cliente = clienteRepository.findById(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) { 
            return ResponseEntity.notFound().build();
        }
    }
    

}
