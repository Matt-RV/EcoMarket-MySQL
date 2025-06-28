package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Assemblers.PedidoModelAssembler;
import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

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
@Tag(name = "Pedidos V2", description = ("Operaciones CRUD HATEOAS para pedidos."))
public class PedidoControllerV2 {
    
    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;


    @Operation(summary = "Listar todos los pedidos.",
               description = "Obtiene una lista con todos los pedidos registrados en el sistema.")
    @ApiResponse(
        responseCode = "200",
        description = "Lista de pedidos obtenida correctamente.",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Pedido.class)
        )
    )
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Pedido>> getAllPedidos() { 
        List<EntityModel<Pedido>> pedidos = pedidoService.findAll().stream()
                .map(pedidoModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(pedidos,
                linkTo(methodOn(PedidoControllerV2.class).getAllPedidos()).withSelfRel());
    }


    @Operation(summary = "Obtener un pedido por ID.",
               description = "Devuelve un pedido específico por su ID.")
    @ApiResponses( value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Pedido encontrado correctamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pedido.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado con el ID proporcionado."
        )
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Pedido> getPedidoById(@PathVariable Integer id) { 
        Pedido pedido = pedidoService.findById(id);
        return pedidoModelAssembler.toModel(pedido);
    }

    @Operation(summary = "Crear un nuevo pedido.",
               description = "Crea un nuevo pedido y lo registra en el sistema.")
    @ApiResponses( value = { 
        @ApiResponse(
            responseCode = "201",
            description = "Pedido creado correctamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pedido.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al crear el pedido, verifique los datos enviados."
        )
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> createPedido(@RequestBody Pedido pedido) { 
        Pedido createdPedido = pedidoService.save(pedido);
        return ResponseEntity
                .created(linkTo(methodOn(PedidoControllerV2.class).getPedidoById(createdPedido.getIdPedido())).toUri())
                .body(pedidoModelAssembler.toModel(createdPedido));
    }

    @Operation(summary = "Actualizar un pedido existente.",
               description = "Actualiza un pedido existente en el sitema.")
    @ApiResponses( value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Pedido actualizado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pedido.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado en el sistema"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al actualizar el pedido, verifique los datos enviados."
        )
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Pedido>> updatePedido(@PathVariable Integer id, @RequestBody Pedido pedido) { 
        pedido.setIdPedido(id);
        Pedido updatedPedido = pedidoService.save(pedido);
        return ResponseEntity
            .ok(pedidoModelAssembler.toModel(updatedPedido));
    }


    @Operation(summary = "Eliminar un pedido.",
               description = "Elimina un pedido existente del sistema por su ID.")
    @ApiResponses( value = { 
        @ApiResponse(
            responseCode = "204",
            description = "Pedido eliminado correctamente."
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado, el ID no corresponde a ningún pedido registrado."
        )
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deletePedido(@PathVariable Integer id) { 
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
