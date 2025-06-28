package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Service.ClienteService;
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
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Métodos relacionados con la gestión de clientes.")

public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    /*
     * Metodo que retorna un listado con todos los clientes registrados en el sistema.
     * Se verifica si la lista está vacía, si es así, devuelve un HTTP 204 No Content.
     * Si no está vacía, devuelve un HTTP 200 OK con la lista de clientes.
     * Ejemplo: /api/v1/clientes
     */
    @GetMapping
    @Operation(summary = "Listar todos los clientes.", 
    description = "Obtiene un listado de todos los clientes registrados en el sistema.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Lista de clientes encontrada correctamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class)
            )
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No hay clientes registrados."
        )
    }
    )
    public ResponseEntity<List<Cliente>> listar() { 
        List<Cliente> clientes = clienteService.findAll();
        if(clientes.isEmpty()) { 
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }


    /*
     * Metodo para crear un nuevo cliente en el sistema.
     * Se recibe un objeto Cliente en el cuerpo de la solicitud.
     * IMPORTANTE: El ID se debe incluir en el JSON, ya que es un campo obligatorio, no es autogenerado.
     * Guarda el cliente en la base de datos y devuelve un HTTP 201 Created con el nuevo cliente.
     * Si ocurre un error, devuelve un HTTP 400 Bad Request.
     * Ejemplo: /api/v1/clientes
     */
    @PostMapping
    @Operation(summary = "Crear un nuevo cliente.", 
               description = "Crea un nuevo cliente y lo registra en el sistema.")
    @ApiResponses( value = {
        @ApiResponse(
            responseCode = "201",
            description = "Cliente creado correctamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al crear un cliente, verifique los datos enviados."
        )
    }
    )
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente) { 
        try { 
            Cliente newCliente = clienteService.save(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCliente);
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); 
        }
    }


    /*
     * Metodo para actualizar un cliente ya existente en el sistema.
     * Se busca el cliente por su ID.
     * Si el cliente no se encuentra, devuelve un HTTP 404 Not Found.
     * Si se encuentra, se actualiza el cliente con los datos proporcionados en el cuerpo de la solicitud.
     * Devuelve un HTTP 200 OK con el cliente actualizado.
     * IMPORTANTE: El ID del cliente debe ser el mismo que se pasa en la URL.
     * Ejemplo: /api/v1/clientes/{idCliente}
     */
    @PutMapping("/{idCliente}")
    @Operation(summary = "Actualizar un cliente existente.",
               description = "Actualiza los datos de un cliente existente en el sistema, identificándolo por su ID.")
    @ApiResponses( value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Cliente actualizado exitosamente.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado, el ID proporcionado no corresponde a ningún cliente registrado."
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Error al actualizar el cliente, los datos proporcionados son inválidos."
        )
    }
    )
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer idCliente, @RequestBody Cliente cliente) { 
        try { 
            if (this.clienteService.existsById(idCliente)) { 
                Cliente tmp = clienteService.findByCliente(idCliente); // Busca el cliente por su ID.
                // Actualiza los datos del cliente.
                tmp.setIdCliente(idCliente);
                tmp.setNombreCliente(cliente.getNombreCliente());
                tmp.setApellidosCliente(cliente.getApellidosCliente());
                tmp.setEmailCliente(cliente.getEmailCliente());
                tmp.setDireccionCliente(cliente.getDireccionCliente());
                // Guarda los cambios en la base de datos.
                clienteService.save(tmp);
                return ResponseEntity.ok(tmp);
            }
            else { 
                // En caso de que no exista el cliente, devuelve un HTTP 404 Not Found.
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*
     * Metodo para eliminar un cliente del sistema.
     * Se busca el cliente por su ID.
     * Si el cliente no se encuentra, devuelve un HTTP 404 Not Found.
     * Si se encuentra, se elimina el cliente y devuelve un HTTP 204 No Content.
     * Ejemplo: /api/v1/clientes/{idCliente}
     * IMPORTANTE: El ID del cliente debe ser el mismo que se pasa en la URL.
     */
    @DeleteMapping("/{idCliente}")
    @Operation(summary = "Eliminar un cliente existente.",
               description = "Elimina un cliente existente del sistema, a través de su ID.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Cliente eliminado de manera exitosa."
        )
        ,
        @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado, el ID no corresponde a ningún cliente registrado."
        )
        ,
        @ApiResponse(
            responseCode = "400",
            description = "Error al eliminar el cliente, el ID proporcionado es inválido."
        )
    }
    )
    public ResponseEntity<Cliente> eliminar(@PathVariable Integer idCliente) { 
        try { 
            Cliente cliente = clienteService.findByCliente(idCliente);
            if (cliente == null) { 
                return ResponseEntity.notFound().build();
            }
            // Si existe, elimina el cliente.
            clienteService.delete(idCliente);
            return ResponseEntity.ok(cliente); // Devuelve el cliente eliminado.
        } catch (Exception e) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /*
     * Metodo para buscar un cliente por su ID.
     * Si el cliente es encontrado, devuelve un HTTP 200 OK con el cliente.
     * Si no se encuentra el cliente, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/clientes/{idCliente}
     */
    @GetMapping("/{idCliente}")
    @Operation(summary = "Buscar un cliente.",
               description =  "Obtiene información de un cliente en específico, identificándolo por su ID.")
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Cliente encontrado de manera exitosa.",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Cliente.class)
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Cliente no encontrado, el ID no corresponde a ningún cliente registrado."
        )
    }
    )
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Integer idCliente) { 
        try { 
            Cliente cliente = clienteService.findByCliente(idCliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) { 
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Metodo para contar cuantos clientes hay en el sistema.
     * Devuelve un HTTP 200 OK con el conteo total de clientes.
     * Ejemplo: /api/v1/clientes/count
     */
    @GetMapping("/count")
    @Operation(summary = "Contar clientes.",
               description = "Cuenta el número total de clientes registrados en el sistema.")
    @ApiResponse(
        responseCode = "200",
        description = "Número total de clientes en el sistema."
    )
    public ResponseEntity<Long> contar() { 
        Long count = clienteService.count();
        return ResponseEntity.ok(count);
    }


    /*
     * Metodo para buscar clientes por nombre.
     * Se recibe un parámetro de consulta "nombre" en la URL.
     * Si no encuentra clientes, devuelve un HTTP 204 No Content.
     * Si encuentra clientes, devuelve un HTTP 200 OK con la lista de clientes.
     * El nombre se busca ignorando mayúsculas y minúsculas.
     * Ejemplo: /api/v1/clientes/search?nombre=Juan
     */
    @GetMapping("/nombre/{nombre}")
    @Operation(summary = "Buscar clientes por nombre.",
               description = "Busca clientes por su nombre, ignorando mayúsculas y minúsculas.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Clientes encontrados con el nombre proporcionado."
        )
        ,
        @ApiResponse(
            responseCode = "204",
            description = "No se encontraron clientes con el nombre proporcionado."
        )
    }
    )
    public ResponseEntity<List<Cliente>> buscarPorNombre(@PathVariable String nombreCliente) { 
        List<Cliente> clientes = clienteService.findByNombreContainingIgnoreCase(nombreCliente);
        if (clientes.isEmpty()) { 
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    /*
     * Metodo para buscar clientes por email.
     * Se recibe un parámetro de consulta "email" en la URL.
     * si no encuentra clientes, devuelve un HTTP 204 No Content.
     * Si encuentra clientes, devuelve un HTTP 200 OK con la lista de clientes.
     * El email se busca ignorando mayúsculas y minúsculas.
     * Ejemplo: /api/v1/cleintes/email/{email}
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar clientes por email.",
               description = "Busca clientes por su email, ignorando mayúsculas y minúsculas.")
    @ApiResponses(value = { 
        @ApiResponse(
            responseCode = "200",
            description = "Clientes encontrados con el email proporcionado."
        ),
        @ApiResponse(
            responseCode = "204",
            description = "No se encontraron clientes con el email proporcionado."
        )
    }
    )
    public ResponseEntity<List<Cliente>> buscarPorEmail(@PathVariable String emailCliente) { 
        List<Cliente> clientes = clienteService.findByEmailClienteContainingIgnoreCase(emailCliente);
        if (clientes.isEmpty()) { 
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }
}
