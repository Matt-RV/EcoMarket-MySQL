package com.gestionPedidos.GestionPedidos.controller;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.gestionPedidos.GestionPedidos.Controller.ClienteController;
import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Service.ClienteService;


@SpringBootTest
public class ClienteControllerTest {
    @Autowired
    private ClienteController clienteController;

    @MockBean
    private ClienteService clienteService;

    @Test
    public void testListar() { 
        when(clienteService.findAll()).thenReturn(List.of(new Cliente()));
        ResponseEntity<List<Cliente>> response = clienteController.listar();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testBuscarPorId() { 
        Integer id = 1;
        Cliente cliente = new Cliente();
        cliente.setIdCliente(id);

        when(clienteService.findByCliente(id)).thenReturn(cliente);
        ResponseEntity<Cliente> response = clienteController.buscarPorId(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getIdCliente());
    }

    @Test
    public void testGuardar() { 
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("John");
        cliente.setApellidosCliente("Marston");
        cliente.setEmailCliente("jo.marston@email.com");

        Cliente savedCliente = new Cliente();
        savedCliente.setIdCliente(1);
        savedCliente.setNombreCliente(cliente.getNombreCliente());
        savedCliente.setApellidosCliente(cliente.getApellidosCliente());
        savedCliente.setEmailCliente(cliente.getEmailCliente());

        when(clienteService.save(any(Cliente.class))).thenReturn(savedCliente);

        ResponseEntity<Cliente> response = clienteController.guardar(cliente);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getIdCliente());
    }

        @Test
    public void testEliminar() { 
        Integer id = 1;
        Cliente cliente = new Cliente();
        cliente.setIdCliente(id);
        when(clienteService.findByCliente(id)).thenReturn(cliente);
        doNothing().when(clienteService).delete(id);

        ResponseEntity<Void> response = clienteController.eliminar(id);
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
