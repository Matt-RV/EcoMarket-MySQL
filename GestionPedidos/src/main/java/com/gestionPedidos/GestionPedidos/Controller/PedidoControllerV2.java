package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Assemblers.PedidoModelAssembler;
import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoControllerV2 {
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pedido>> getAllPedidos() { 
        List<EntityModel<Pedido>> pedidos = pedidoService.findAll().stream()
                .map(pedidoModelAssembler::toModel)
                .collect(Collectors.toList());
        
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withSelfRel());
    }
}
