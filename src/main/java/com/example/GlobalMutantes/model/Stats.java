package com.example.GlobalMutantes.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    private long contadorADNMutante;
    private long contadorADNHumano;
    private double ratio;

}
