package com.example.GlobalMutantes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="ADN")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ADN {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String secuencia; // la secuencia de ADN :)
    private boolean esMutante;

    // Constructor sin ID
    public ADN(String secuencia, boolean esMutante) {
        this.secuencia = secuencia;
        this.esMutante = esMutante;
    }
}
