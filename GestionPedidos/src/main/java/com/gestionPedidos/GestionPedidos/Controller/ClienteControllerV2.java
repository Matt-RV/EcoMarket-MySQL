package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Assemblers.ClienteModelAssembler;
import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler clienteModelAssembler;

    @Operation(summary = "Obtener todos los clientes.", 
               description = "Devuelve una lista de todos los clientes registrados.")
    @ApiResponse(
                 responseCode = "200", 
                 description = "Lista de clientes obtenida correctamente.",
                 content = @Content(mediaType = "application/json"))
    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public CollectionModel<EntityModel<Cliente>> getAllClientes() { 
        List<EntityModel<Cliente>> clientes = clienteService.findAll().stream()
                .map(clienteModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(clientes,
                linkTo(methodOn(ClienteControllerV2.class).getAllClientes()).withSelfRel());
    }

    @Operation(summary = "Obtener cliente por ID.",
               description = "Devuelve el cliente según el ID proporcionado.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Cliente.class))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado.")
    })
    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<Cliente> getClienteById(@PathVariable Integer id) { 
        Cliente cliente = clienteService.findByCliente(id);
        return clienteModelAssembler.toModel(cliente);
    }

    @Operation(summary = "Crear un nuevo cliente.", 
               description = "Registra un nuevo cliente en el sistema.")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201",
                     description = "Cliente creado correctamente.",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = Cliente.class))
                    ),
        @ApiResponse(responseCode = "400",
                     description = "Datos inválidos para crear el cliente.")
    })
    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> createCliente(@RequestBody Cliente cliente) { 
        Cliente createdCliente = clienteService.save(cliente);
        return ResponseEntity
            .created(linkTo(methodOn(ClienteControllerV2.class).getClienteById(createdCliente.getIdCliente())).toUri())
            .body(clienteModelAssembler.toModel(createdCliente));
    }

    @Operation(summary = "Obtener cliente por ID.", 
               description = "Devuelve el cliente según el ID proporcionado.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", 
                     description = "Cliente encontrado.",
                     content = @Content(mediaType = "application/json",
                     schema = @Schema(implementation = Cliente.class))
                    ),
        @ApiResponse(responseCode = "404",
                     description = "Cliente no encontrado.")
    })
    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Cliente>> updateCliente(@PathVariable Integer id, @RequestBody Cliente cliente) { 
        cliente.setIdCliente(id);
        Cliente updatedCliente = clienteService.save(cliente);
        return ResponseEntity.ok(clienteModelAssembler.toModel(updatedCliente));
    }

    @Operation(summary = "Eliminar un cliente.", 
               description = "Elimina un cliente por su ID.")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "204",
                     description = "Cliente eliminado correctamente."),
        @ApiResponse(responseCode = "404", 
                     description = "Cliente no encontrado.")
    })
    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<?> deleteCliente(@PathVariable Integer id) {
        clienteService.delete(id); 
        return ResponseEntity.noContent().build();
    }


}
