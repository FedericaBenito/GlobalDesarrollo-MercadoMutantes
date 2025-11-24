package com.example.GlobalMutantes.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ADNRequest {
    // La clase ADNRequest sirve para encapsular y manejar el array de Strings correctamente
    // (ya que en la base de datos H2, el atributo es un String, no un array)
    private String[] secuencia;

    // sí, estos comentarios son reales y los hice a mano, re divertido!
}