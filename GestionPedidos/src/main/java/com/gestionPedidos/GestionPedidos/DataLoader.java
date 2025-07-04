package com.gestionPedidos.GestionPedidos;

import com.gestionPedidos.GestionPedidos.Model.*;
import com.gestionPedidos.GestionPedidos.Repository.*;
import net.datafaker.Faker;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner{


    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar clientes
        for (int i = 0; i < 5; i++) { 
            Cliente cliente = new Cliente();
            cliente.setIdCliente(i+1);
            cliente.setNombreCliente(faker.name().firstName());
            cliente.setApellidosCliente(faker.name().lastName() + " " + faker.name().lastName());
            cliente.setEmailCliente(faker.internet().emailAddress());
            cliente.setDireccionCliente(faker.address().fullAddress());
            clienteRepository.save(cliente);
        }
        List<Cliente> listaClientes = clienteRepository.findAll();

        // Generar tipos de pedidos
        for (int i = 0; i < 5; i++)  {
            Pedido pedido = new Pedido();
            pedido.setFechaCreacion(faker.date().past(30, java.util.concurrent.TimeUnit.DAYS));
            pedido.setEstado(faker.options().option("Pendiente", "Entregado", "Cancelado"));
            pedido.setTotal(faker.number().randomDouble(2, 10, 500));
            pedido.setCliente(listaClientes.get(random.nextInt(listaClientes.size())));
            pedidoRepository.save(pedido);
            
        }
    }
    
}
