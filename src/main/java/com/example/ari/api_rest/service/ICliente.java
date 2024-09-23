package com.example.ari.api_rest.service;

import com.example.ari.api_rest.model.dto.clienteDto;
import com.example.ari.api_rest.model.entity.cliente;

public interface ICliente {

    cliente save(clienteDto Cliente);

    cliente findById(Integer id);

    void delete(cliente Cliente);
} 
