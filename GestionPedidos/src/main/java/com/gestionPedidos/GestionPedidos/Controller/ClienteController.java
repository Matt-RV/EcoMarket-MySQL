package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")

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
     * IMPORTANTE: El ID del cliente no se debe incluir en el JSON, ya que es autogenerado.
     * Guarda el cliente en la base de datos y devuelve un HTTP 201 Created con el nuevo cliente.
     * Si ocurre un error, devuelve un HTTP 400 Bad Request.
     * Ejemplo: /api/v1/clientes
     */
    @PostMapping
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
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer idCliente, @RequestBody Cliente cliente) { 
        try { 
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
        } catch (Exception e) { 
            return ResponseEntity.notFound().build();
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
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * Metodo para buscar un cliente por su ID.
     * Si el cliente es encontrado, devuelve un HTTP 200 OK con el cliente.
     * Si no se encuentra el cliente, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/clientes/{idCliente}
     */
    @GetMapping("/{idCliente}")
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
    public ResponseEntity<List<Cliente>> buscarPorEmail(@PathVariable String emailCliente) { 
        List<Cliente> clientes = clienteService.findByEmailClienteContainingIgnoreCase(emailCliente);
        if (clientes.isEmpty()) { 
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }
}
