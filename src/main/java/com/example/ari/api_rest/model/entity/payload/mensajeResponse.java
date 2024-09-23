package com.example.ari.api_rest.model.entity.payload;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class mensajeResponse  implements Serializable{

    private String mensaje;
    private Object object;
}
