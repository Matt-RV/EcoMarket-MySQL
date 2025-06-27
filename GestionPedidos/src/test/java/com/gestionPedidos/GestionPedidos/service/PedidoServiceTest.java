package com.gestionPedidos.GestionPedidos.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Repository.PedidoRepository;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;

@SpringBootTest
public class PedidoServiceTest {

    @Autowired
    private PedidoService pedidoService;

    @MockBean
    private PedidoRepository pedidoRepository;

    @Test
    public void testFindAll() {
        when(pedidoRepository.findAll()).thenReturn(List.of(new Pedido()));
        List<Pedido> listaPedidos = pedidoService.findAll();

        assertNotNull(listaPedidos);
        assertEquals(1, listaPedidos.size());
    }

    @Test
    public void testFindById() { 
        Integer id = 1;
        Cliente cliente = new Cliente(1, "John", "Marston", "jo.marst@email.com", "123 Main St");
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        pedido.setFechaCreacion(new Date());
        pedido.setEstado("Pendiente");
        pedido.setTotal(200.0);
        pedido.setCliente(cliente);

        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        Pedido pedidof = pedidoService.findById(id);
        assertNotNull(pedidof);
        assertEquals(id, pedidof.getIdPedido());
    }

    @Test
    public void testSave() { 
        Pedido pedido = new Pedido(1, new Date(), "Pendiente", 200.0, 
        new Cliente(1, "John", "Marston", "jo.marst@email.com", "123 Main St"));

        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido savedPedido = pedidoService.save(pedido);
        
        assertNotNull(savedPedido);
        assertEquals("Pendiente", savedPedido.getEstado());
    }

    @Test
    public void testDelete() { 
        Integer id = 1;
        pedidoService.delete(id);
        verify(pedidoRepository).deleteById(id);
    }
}
