package com.example.ari.api_rest.service;

import java.util.List;

import com.example.ari.api_rest.model.dto.clienteDto;
import com.example.ari.api_rest.model.entity.cliente;

public interface IClienteService {

    List<cliente> listAll();

    cliente save(clienteDto Cliente);

    cliente findById(Integer id);

    void delete(cliente Cliente);

    boolean existsById(Integer id);
} 
