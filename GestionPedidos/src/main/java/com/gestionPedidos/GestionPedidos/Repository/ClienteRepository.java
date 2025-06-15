package com.gestionPedidos.GestionPedidos.Repository;

import com.gestionPedidos.GestionPedidos.Model.Cliente;

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


    /*
     * Método que busca un cliente por su nombre.
     * Ignorando mayúsculas y minúsculas.
     */
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    /*
     * Método que busca un cliente por su mail.
     * Ignorando mayúsculas y minúsculas.
     */
    List<Cliente> findByEmailContainingIgnoreCase(String emailCliente);
    
}
