package com.example.ari.api_rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ari.api_rest.model.dao.clienteDao;
import com.example.ari.api_rest.model.dto.clienteDto;
import com.example.ari.api_rest.model.entity.cliente;
import com.example.ari.api_rest.service.ICliente;

@Service
public class clienteImpl implements ICliente {

    @Autowired
    private clienteDao ClienteDao;

    @Transactional
    @Override
    public cliente save(clienteDto ClienteDto) {
        cliente Cliente = cliente.builder()
                .idCliente(ClienteDto.getIdCliente())
                .nombre(ClienteDto.getNombre())
                .apellido(ClienteDto.getApellido())
                .correo(ClienteDto.getCorreo())
                .fecheRegistro(ClienteDto.getFecheRegistro())
                .build();
        return ClienteDao.save(Cliente);
    }

    @Transactional(readOnly = true)
    @Override
    public cliente findById(Integer id) {
        return ClienteDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void delete(cliente Cliente) {
        ClienteDao.delete(Cliente);
    }

}
