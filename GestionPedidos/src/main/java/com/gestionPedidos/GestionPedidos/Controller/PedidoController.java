package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

     // Lista todos los pedidos disponibles, retorna una lista de pedidos o un 204 si no hay contenido.
     /*
      * Este método
      */
    @GetMapping
    public ResponseEntity<List<Pedido>> listar() { 
        List<Pedido> pedidos = pedidoService.findAll(); // Obtiene todos los pedidos almacenados.
        if(pedidos.isEmpty()) { // Verifica si la lista está vacía.
            return ResponseEntity.noContent().build(); // Devuelve un HTTP 204 No Content.
        }
        return ResponseEntity.ok(pedidos); // Devuelve un HTTP 200 OK con la lista de pedidos.
    }

    // Guarda un nuevo pedido, retorna pedido creado o un 400 si hay un error.
    @PostMapping
    public ResponseEntity<Pedido> guardar(@RequestBody Pedido pedido) {
        Pedido nuevoPedido = pedidoService.save(pedido); // Guarda el pedido en la base de datos.
        // Importante, no colocar idPedido en el JSON.
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido); // Devuelve un HTTP 201 Created con el nuevo pedido.
    }

    // Actualiza un pedido existente, con el ID del pedido para actualizar, retorna pedido actualizado o un 404 si no existe.
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) { 
                                    // Pide un cuerpo con el tipo pedido, importante colocar idPedido en el JSON. 
        try { 
            Pedido tmp = pedidoService.findById(id); // Busca el pedido por ID.
            // Actualiza los datos del pedido.
            tmp.setIdPedido(id);
            tmp.setFechaCreacion(pedido.getFechaCreacion());
            tmp.setEstado(pedido.getEstado());
            tmp.setTotal(pedido.getTotal());
            tmp.setIdCliente(pedido.getIdCliente());
            
            // Guarda los cambios.
            pedidoService.save(tmp);
            return ResponseEntity.ok(pedido);
        } catch (Exception e) { 
            return ResponseEntity.notFound().build(); // Si no se encuentra el pedido, devuelve un HTTP 404 Not Found.
        }
    }

    // Método para eliminar un pedido por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            pedidoService.delete(id); // Elimina el pedido por ID.
            return ResponseEntity.ok().build(); // Devuelve un HTTP 200 OK.
        } catch (Exception e) { 
            return ResponseEntity.notFound().build(); // Si no se encuentra, devuelve un HTTP 404 Not Found.
        }
    }

    // método para buscar por ID
    @GetMapping("/{id}") 
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Integer id) { 
        Pedido pedido = pedidoService.findById(id); // Busca por su ID
        if (pedido == null) { 
            return ResponseEntity.notFound().build(); // Si no se encuentra, devuelve un HTTP 404 Not Found.
            // Devuelve un HTTP 500 ¿?
        }
        return ResponseEntity.ok(pedido); // Devuelve el pedido encontrado.
    }

    // Método para contar pedidos
    @GetMapping("/count")
    public ResponseEntity<Long> contar() { 
        Long count = pedidoService.count(); 
        return ResponseEntity.ok(count); // Devuelve el conteo de pedidos.
    }

    // Método para buscar pedidos por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) { 
        List<Pedido> pedidos = pedidoService.findByEstado(estado);
        if (pedidos.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve un HTTP 404 Not Found si no hay pedidos con ese estado.
        }
        return ResponseEntity.ok(pedidos); // Devuelve un HTTP 200 OK con la lista de pedidos encontrados.
    }    

    // Método para buscar pedidos por fecha de creación
    // Buscar como hacerlo
    @GetMapping("/fechaCreacion/{fechaCreacion}")
    public ResponseEntity<List<Pedido>> buscarPorFechaCreacion(
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaCreacion) { // Darle un formato a la fecha.
        List<Pedido> pedidos = pedidoService.findByFechaCreacion(fechaCreacion); // Busca por fecha de creación.
        if (pedidos.isEmpty()) { 
            return ResponseEntity.notFound().build(); // Devuelve un HTTP 404 Not Found si no hay pedidos con esa fecha.
        }
        return ResponseEntity.ok(pedidos); // Devuelve un HTTP 200 OK con la lista de pedidos encontrados.
    }

    // Método para buscar pedidos por ID de cliente
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Integer idCliente) { 
        List<Pedido> pedidos = pedidoService.findByIdCliente(idCliente);
        if(pedidos.isEmpty()) { 
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    // Método para actualizar el estado de un pedido
    // Método no funciona, no se porque.
    @PutMapping("/{id}/estado/{estado}")
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
