package com.gestionPedidos.GestionPedidos.Assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Controller.PedidoControllerV2;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    public EntityModel<Pedido> toModel(Pedido pedido) { 
        return EntityModel.of(
            pedido,
            linkTo(methodOn(PedidoControllerV2.class).getPedidoById(pedido.getIdPedido())).withSelfRel(),
            linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withRel("pedidos")
        );
    }
}
