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


    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Pedido> getPedidoById(@PathVariable Integer id) { 
        Pedido pedido = pedidoService.findById(id);
        return pedidoModelAssembler.toModel(pedido);
    }


    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> createPedido(@RequestBody Pedido pedido) { 
        Pedido createdPedido = pedidoService.save(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(createdPedido.getIdPedido())).toUri())
                .body(pedidoModelAssembler.toModel(createdPedido));
    }


    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) { 
        pedido.setIdPedido(id);
        Pedido updatedPedido = pedidoService.save(pedido);
        return ResponseEntity
            .ok(pedidoModelAssembler.toModel(updatedPedido));
    }


    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deletePedido(@PathVariable Integer id) { 
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
