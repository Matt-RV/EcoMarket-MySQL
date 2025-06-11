package com.gestionPedidos.GestionPedidos.Repository;

import com.gestionPedidos.GestionPedidos.Model.Cliente;
import com.gestionPedidos.GestionPedidos.Model.Pedido;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

    /*  CRUD (Create, Read, Update, Delete):
    o save(S entity): Guarda una entidad.
    o findById(ID id): Encuentra una entidad por su ID.
    o existsById(ID id): Verifica si una entidad con un ID dado existe.
    o findAll(): Encuentra todas las entidades.
    o findAllById(Iterable<ID> ids): Encuentra todas las entidades por sus IDs.
    o count(): Cuenta todas las entidades.
    o deleteById(ID id): Borra una entidad por su ID.
    o delete(S entity): Borra una entidad.
    o deleteAll(): Borra todas las entidades.
    Paginación y Ordenación:
            • findAll(Pageable pageable): Encuentra todas las entidades con paginación.
            • findAll(Sort sort): Encuentra todas las entidades con ordenación.
    */

    /**
     * Método para buscar un cliente por su ID.
     * 
     * @param idCliente ID del cliente a buscar
     * @return Cliente encontrado o null si no existe
     */
    Cliente findByIdCliente(Integer idCliente);

    // Método para buscar un cliente por su nombre
    Cliente findByNombreConstraingIgnoreCase(String nombre); // Método para buscar un cliente por su nombre, ignorando mayúsculas y minúsculas

    // Método para buscar un cliente por su email
    Cliente findByEmailConstraingIgnoreCase(String email); // Método para buscar un cliente por su email, ignorando mayúsculas y minúsculas
    
    List<Pedido> findByCliente_IdCliente(Integer idCliente);
}
