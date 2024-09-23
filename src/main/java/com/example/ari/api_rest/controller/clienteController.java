package com.example.ari.api_rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ari.api_rest.model.dto.clienteDto;
import com.example.ari.api_rest.model.entity.cliente;
import com.example.ari.api_rest.model.entity.payload.mensajeResponse;
import com.example.ari.api_rest.service.IClienteService;

@RestController
@RequestMapping("api/v1/")
public class clienteController {

    @Autowired
    private IClienteService clienteService;

    @GetMapping("clientes")
    public ResponseEntity<?> showAll() {
        List<cliente> getList = clienteService.listAll();

        if (getList == null) {
            return new ResponseEntity<>(mensajeResponse.builder()
                    .mensaje("No hay registros de clientes.")
                    .object(null)
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity<>(
                mensajeResponse.builder()
                        .mensaje("")
                        .object(getList)
                        .build(),
                HttpStatus.OK);
    }

    @PostMapping("cliente")
    public ResponseEntity<?> create(@RequestBody clienteDto ClienteDto) {
        try {
            cliente clienteSave = clienteService.save(ClienteDto);
            return new ResponseEntity<>(mensajeResponse.builder()
                    .mensaje("Guardado correctamente")
                    .object(clienteDto.builder()
                            .idCliente(clienteSave.getIdCliente())
                            .nombre(clienteSave.getNombre())
                            .apellido(clienteSave.getApellido())
                            .correo(clienteSave.getCorreo())
                            .fecheRegistro(clienteSave.getFecheRegistro())
                            .build())
                    .build(),
                    HttpStatus.CREATED);
        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    mensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@RequestBody clienteDto ClienteDto, @PathVariable Integer id) {
        try {
            if (clienteService.existsById(id)) {
                ClienteDto.setIdCliente(id);
                cliente clienteUpdate = clienteService.save(ClienteDto);
                return new ResponseEntity<>(mensajeResponse.builder()
                        .mensaje("Guardado correctamente")
                        .object(clienteDto.builder()
                                .idCliente(clienteUpdate.getIdCliente())
                                .nombre(clienteUpdate.getNombre())
                                .apellido(clienteUpdate.getApellido())
                                .correo(clienteUpdate.getCorreo())
                                .fecheRegistro(clienteUpdate.getFecheRegistro())
                                .build())
                        .build(),
                        HttpStatus.CREATED);

            } else {
                return new ResponseEntity<>(
                        mensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra en la base de datos ")
                                .object(null)
                                .build(),
                        HttpStatus.NOT_FOUND);
            }

        } catch (DataAccessException exDt) {
            return new ResponseEntity<>(
                    mensajeResponse.builder()
                            .mensaje(exDt.getMessage())
                            .object(null)
                            .build(),
                    HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            cliente clienteDelete = clienteService.findById(id);

            if (clienteDelete == null) {
                mensajeResponse response = mensajeResponse.builder()
                        .mensaje("Error: El cliente con ID " + id + " no existe en la base de datos.")
                        .object(null)
                        .build();
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            clienteService.delete(clienteDelete);
            mensajeResponse response = mensajeResponse.builder()
                    .mensaje("El cliente ha sido eliminado con éxito.")
                    .object(clienteDelete)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException exDt) {
            mensajeResponse response = mensajeResponse.builder()
                    .mensaje("Error al intentar eliminar el cliente de la base de datos.")
                    .object(exDt.getMostSpecificCause().getMessage())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        cliente Cliente = clienteService.findById(id);

        if (Cliente == null) {
            return new ResponseEntity<>(
                    mensajeResponse.builder()
                            .mensaje("El registro que intenta buscar no existe")
                            .object(null)
                            .build(),
                    HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(
                mensajeResponse.builder()
                        .mensaje("")
                        .object(clienteDto.builder()
                                .idCliente(Cliente.getIdCliente())
                                .nombre(Cliente.getNombre())
                                .apellido(Cliente.getApellido())
                                .correo(Cliente.getCorreo())
                                .fecheRegistro(Cliente.getFecheRegistro())
                                .build())
                        .build(),
                HttpStatus.OK);

    }
}
