package com.gestionPedidos.GestionPedidos;

import com.gestionPedidos.GestionPedidos.Model.*;
import com.gestionPedidos.GestionPedidos.Repository.*;

import net.datafaker.Faker;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("")
@Component
public class DataLoader implements CommandLineRunner{

    @Autowired
    private PedidoRepository pedidoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;

    
    
}
