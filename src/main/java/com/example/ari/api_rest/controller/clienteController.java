package com.example.ari.api_rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ari.api_rest.model.dto.clienteDto;
import com.example.ari.api_rest.model.entity.cliente;
import com.example.ari.api_rest.service.ICliente;

@RestController
@RequestMapping("api/v1/")
public class clienteController {

    @Autowired
    private ICliente clienteService;

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public clienteDto create(@RequestBody clienteDto ClienteDto) {
        cliente clienteSave = clienteService.save(ClienteDto);
        return clienteDto.builder()
                .idCliente(clienteSave.getIdCliente())
                .nombre(clienteSave.getNombre())
                .apellido(clienteSave.getApellido())
                .correo(clienteSave.getCorreo())
                .fecheRegistro(clienteSave.getFecheRegistro())
                .build();
    }

    @PutMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public clienteDto update(@RequestBody clienteDto ClienteDto) {
        cliente clienteUpdate = clienteService.save(ClienteDto);
        return clienteDto.builder()
                .idCliente(clienteUpdate.getIdCliente())
                .nombre(clienteUpdate.getNombre())
                .apellido(clienteUpdate.getApellido())
                .correo(clienteUpdate.getCorreo())
                .fecheRegistro(clienteUpdate.getFecheRegistro())
                .build();
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            cliente clienteDelete = clienteService.findById(id);

            if (clienteDelete == null) {
                response.put("mensaje", "Error: El cliente con ID " + id + " no existe en la base de datos.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            clienteService.delete(clienteDelete);
            response.put("mensaje", "El cliente ha sido eliminado con éxito.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);

        } catch (DataAccessException exDt) {
            response.put("mensaje", "Error al intentar eliminar el cliente de la base de datos.");
            response.put("error", exDt.getMostSpecificCause().getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("cliente/{id}")
    @ResponseStatus(HttpStatus.OK)
    public clienteDto showById(@PathVariable Integer id) {
        cliente Cliente = clienteService.findById(id);
        return clienteDto.builder()
                .idCliente(Cliente.getIdCliente())
                .nombre(Cliente.getNombre())
                .apellido(Cliente.getApellido())
                .correo(Cliente.getCorreo())
                .fecheRegistro(Cliente.getFecheRegistro())
                .build();
    }
}
