package com.gestionPedidos.GestionPedidos.Controller;

import com.gestionPedidos.GestionPedidos.Model.Pedido;
import com.gestionPedidos.GestionPedidos.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/pedidos")
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
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) { 
        try { 
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
        } catch (Exception e) { 
            return ResponseEntity.notFound().build(); 
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
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
        // Primero verificar si el pedido existe
        Pedido pedido = pedidoService.findById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }
        // Si existe, eliminarlo
        pedidoService.delete(id);
        return ResponseEntity.ok().build();
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}



    /*
     * Busca un pedido por su ID.
     * Busca el pedido a través de su ID.
     * Si el pedido es encontrado, devuelve un HTTP 200 OK con el pedido.
     * Si no se encuentra el pedido, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/1
     */
    // método para buscar por ID
    @GetMapping("/{id}") 
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
    // Método para contar pedidos
    @GetMapping("/count")
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
    public ResponseEntity<List<Pedido>> buscarPorEstado(@PathVariable String estado) { 
        List<Pedido> pedidos = pedidoService.findByEstado(estado);
        if (pedidos.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve un HTTP 404 Not Found si no hay pedidos con ese estado.
        }
        return ResponseEntity.ok(pedidos); // Devuelve un HTTP 200 OK con la lista de pedidos encontrados.
    }        
    
    /*
     * Busca pedidos por ID de cliente.
     * Se busca por ID de cliente.
     * Retorna un HTTP 200 OK con la lista de pedidos encontrados por ID de cliente.
     * Si no encuentra pedidos con ese ID de cliente, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/cliente/1
     */
    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Integer idCliente) { 
        List<Pedido> pedidos = pedidoService.findByIdCliente(idCliente);
        if(pedidos.isEmpty()) { 
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    
    
    
    /*
     * Actualiza el estado de un pedido.
     * Se busca el pedido por ID y se actualiza su estado.
     * Guarda los cambios y devuelve un HTTP 200 OK con el pedido actualizado.
     * Si no encuentra el pedido, devuelve un HTTP 404 Not Found.
     * Ejemplo: /api/v1/pedidos/1/estado/Entregado
     */
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
