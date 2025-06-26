package com.gestionPedidos.GestionPedidos;

import java.util.List;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import com.gestionPedidos.GestionPedidos.Controller.PedidoController;
import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;

@SpringBootTest
public class PedidoControllerTest {

    @Autowired
    private PedidoController pedidoController;

    @MockBean
    private PedidoService pedidoService;

    @Test
    public void testListar(){ 
        when(pedidoService.findAll()).thenReturn(List.of(new Pedido()));
        ResponseEntity<List<Pedido>> response = pedidoController.listar();
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testBuscarPorId() { 
        Integer id = 1;
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);

        when(pedidoService.findById(id)).thenReturn(pedido);
        
        ResponseEntity<Pedido> response = pedidoController.buscarPorId(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getIdPedido());
    }

    @Test
    public void testGuardar() { 
        Pedido pedido = new Pedido();
        pedido.setFechaCreacion(new Date());
        pedido.setEstado("Pendiente");
        pedido.setTotal(100.0);
        pedido.setCliente(new Cliente());

        Pedido savedPedido = new Pedido();
        savedPedido.setIdPedido(1);
        savedPedido.setFechaCreacion(pedido.getFechaCreacion());
        savedPedido.setEstado(pedido.getEstado());
        savedPedido.setTotal(pedido.getTotal());
        savedPedido.setCliente(pedido.getCliente());

        when(pedidoService.save(any(Pedido.class))).thenReturn(savedPedido);

        ResponseEntity<Pedido> response = pedidoController.guardar(pedido);
        assertEquals(201, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getIdPedido());
    }

    @Test
    public void testEliminar() { 
        Integer id = 1;
        Pedido pedido = new Pedido();
        pedido.setIdPedido(id);
        when(pedidoService.findById(id)).thenReturn(pedido);
        doNothing().when(pedidoService).delete(id);

        ResponseEntity<Pedido> response = pedidoController.eliminar(id);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getIdPedido());
    }
    
    
}
