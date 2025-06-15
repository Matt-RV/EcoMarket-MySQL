 package com.gestionPedidos.GestionPedidos;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestExecutionListeners;

import com.gestionPedidos.GestionPedidos.Service.ClienteService;
import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Repository.ClienteRepository;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;
    
    @MockBean
    private ClienteRepository clienteRepository;

    @Test
    public void testFindAll() { 
        
        when(clienteRepository.findAll()).thenReturn(List.of(new Cliente()));
        List<Cliente> listaClientes = clienteService.findAll();

        assertNotNull(listaClientes);
        assertEquals(1, listaClientes.size());
    }

    @Test
    public void testFindById() { 
        Integer id = 1;

        Cliente cliente = new Cliente(id, "John", "Marston", "jo.marst@email.com", "123 Main St");
        
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.findByCliente(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getIdCliente());
    }

    @Test
    public void testSave() { 
        Cliente cliente = new Cliente(1, "John", "Marston", "jo.marst@email.com", "123 Main St");

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente savedCliente = clienteService.save(cliente);
        assertNotNull(savedCliente);
        assertEquals("John", savedCliente.getNombreCliente());

    }

    @Test
    public void testDelete() { 
        Integer id = 1;

        doNothing().when(clienteRepository).deleteById(id);

        verify(clienteRepository, times(1)).deleteById(id);
    }
}
