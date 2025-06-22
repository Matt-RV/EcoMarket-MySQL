package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
@Tag(name = "Pedidos", description = "Operaciones relacionadas con los pedidos")

public class PedidoController {

    @Autowired
    private PedidoService pedidoService;




     /*
      * Retorna un listado con todos los pedidos registrados en el sistema.
      * Se verifica si la lista está vacía, si es así, devuelve un HTTP 204 No Content.
      * Si no está vacía, devuelve un HTTP 200 OK con la lista de pedidos.
      * ejemplo: /api/v1/pedidos
      */
    @GetMapping
    @Operation(summary = "Listar todos los pedidos",
               description = "Obtiene una lista de todos los pedidos registrados registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de pedidos obtenida correctamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pedido.class)
            )
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No hay pedidos registrados en el sistema."
        )
    }
    )
    public ResponseEntity<List<Pedido>> listar() { 
        List<Pedido> pedidos = pedidoService.findAll(); 
        if(pedidos.isEmpty()) { 
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(pedidos); 
    }


    /* 
     * Crea un nuevo pedido en el sistema.
     * Se recibe un objeto Pedido en el cuerpo de la solicitud.
     * IMPORTANTE: El ID del pedido no se debe incluir en el JSON, ya que es autogenerado.
     * Guarda el pedido en la base de datos y devuelve un HTTP 201 Created con el nuevo pedido.
     * Si ocurre un error, devuelve un HTTP 400 Bad Request.
     * Ejemplo: /api/v1/pedidos
     */
    @PostMapping // Para crear uno nuevo.
    @Operation(summary = "Crear un nuevo pedido",
               description = "Crea un nuevo pedido y lo registra en el sistema.")
    @ApiResponses(value = {
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
    }
    )
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        try {
            Pedido nuevoPedido = pedidoService.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }


    /*
     * Actualiza un pedido existente del sistema.
     * Se busca el pedido por su ID.
     * Pide un cuerpo con el tipo pedido, importante colocar idPedido en el JSON.
     * Guarda los cambios y devuelve un HTTP 200 OK con el pedido actualizado.
     * Si no se encuentra el pedido, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/1
     */
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un pedido existente",
               description = "Actualiza un pedido ya existente en el sistema.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Pedido actualizado correctamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pedido.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado con el ID proporcionado"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al actualizar el pedido, verifique los datos enviados."
        )
    }
    )
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) { 
        try { 
            if (this.pedidoService.existsById(id)) { 
            Pedido tmp = pedidoService.findById(id); // Busca el pedido por ID.
            // Actualiza los datos del pedido.
            tmp.setIdPedido(id);
            tmp.setFechaCreacion(pedido.getFechaCreacion());
            tmp.setEstado(pedido.getEstado());
            tmp.setTotal(pedido.getTotal());
            tmp.setCliente(pedido.getCliente());
            // Guarda los cambios.
            pedidoService.save(tmp);
            return ResponseEntity.ok(tmp); // Devuelve el pedido actualizado.
            }
            else { 
                return ResponseEntity.notFound().build();
            }
            
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }


    /*
     * Elimina un pedido del sistema.
     * Elimina un pedido a través de su ID.
     * Si el pedido es encontrado, se elimina y devuelve un HTTP 200 OK.
     * Si no se encuentra el pedido, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/1
     */
    // Método para eliminar un pedido por ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un pedido",
               description = "Elimina un pedido existente del sistema.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Pedido eliminado correctamente."
        )
        ,
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado, el ID no corresponde a ningún pedido registrado"

        )
        ,
        @ApiResponse(
            responseCode = "400",
            description = "Error al eliminar el pedido, verifique el ID proporcionado."
        )
    }
    )
    public ResponseEntity<Pedido> eliminar(@PathVariable Integer id) {
        try {
        // Primero verificar si el pedido existe
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        // Si existe, eliminarlo
        pedidoService.delete(id);
        return ResponseEntity.ok(pedido); // Devuelve el pedido eliminado.
        } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }   


    /*
     * Busca un pedido por su ID.
     * Busca el pedido a través de su ID.
     * Si el pedido es encontrado, devuelve un HTTP 200 OK con el pedido.
     * Si no se encuentra el pedido, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/1
     */
    @GetMapping("/{id}") 
    @Operation(summary = "Buscar un pedido por ID",
               description = "Busca un pedido específico por su ID en el sistema.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Pedido encontrado correctamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Pedido.class)
            )
        )
        ,
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado con el ID proporcionado."
        )
    }
    )
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) { 
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    /*
     * Cuenta el número total de pedidos en el sistema.
     * Devuelve un HTTP 200 OK con el conteo de los pedidos.
     * Ejemplo: /api/v1/pedidos/count
     */
    @GetMapping("/count")
    @Operation(summary = "Contar el número total de pedidos",
               description = "Cuenta todos los pedidos registrados en el sistema.")
    @ApiResponse(
        responseCode = "200",
        description = "Número total de pedidos en el sistema."
    )
    public ResponseEntity<Long> contar() { 
        Long count = pedidoService.count(); 
        return ResponseEntity.ok(count); 
    }


    /*
     * Busca pedidos por estado.
     * Estados: "Cancelado" / "Entregado" / "Pendiente". Con sus respectivas mayúsculas. 
     * Retorna un HTTP 200 OK con la lista de pedidos encontrados por estado.
     * Si no encuentra pedidos con ese estado, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/estado/Pendiente
     */
    @GetMapping("/estado/{estado}")
    @Operation(summary = "Buscar pedidos por estado", 
               description = "Busca pedidos en el sistema según su estado.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Pedidos encontrados por estado."
        )
        ,
        @ApiResponse(
            responseCode = "404",
            description = "No se encontraros los pedidos con el estado proporcionado."
        )
    })
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) { 
        List<Pedido> pedidos = pedidoService.findByEstado(estado);
        if (pedidos.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve un HTTP 404 Not Found si no hay pedidos con ese estado.
        }
        return ResponseEntity.ok(pedidos); // Devuelve un HTTP 200 OK con la lista de pedidos encontrados.
    }      

    
    /*
     * Actualiza el estado de un pedido.
     * Se busca el pedido por ID y se actualiza su estado.
     * Guarda los cambios y devuelve un HTTP 200 OK con el pedido actualizado.
     * Si no encuentra el pedido, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/1/estado/Entregado
     */
    @PutMapping("/{id}/estado/{estado}")
    @Operation(summary = "Actualizar el estado de un pedido.",
               description = "Actualiza el estado de un pedido ya existente en el sistema.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Estado del pedido actualizado correctamente."
        )
        ,
        @ApiResponse(
            responseCode = "404",
            description = "Pedido no encontrado con el ID proporcionado."
        )
    }
    )
    public ResponseEntity<Pedido> actualizarEstado(@PathVariable Integer id, @PathVariable String estado) { 
        try {
            Pedido pedido = pedidoService.findById(id); // Busca el pedido por ID.
            pedido.setEstado(estado); // Actualiza el estado del pedido.
            pedidoService.save(pedido); // Guardar cambios
            return ResponseEntity.ok(pedido); // Devuelve el pedido actualizado.
        } catch (Exception e) { 
            return ResponseEntity.notFound().build(); // HTTP 404 Not Found
        }
    }


}
