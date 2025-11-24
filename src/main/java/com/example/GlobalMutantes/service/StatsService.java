package com.example.GlobalMutantes.service;

import com.example.GlobalMutantes.model.Stats;
import com.example.GlobalMutantes.repository.ADNRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class StatsService {
    //inyección de dependencia
    @Autowired
    private ADNRepository adnRepository;

    public Stats getStats() {
        long contadorADNMutante = adnRepository.countByEsMutante(true);
        long contadorADNHumano = adnRepository.countByEsMutante(false);
        double ratio;
        if (contadorADNHumano > 0) {
            ratio = (double)contadorADNMutante / contadorADNHumano;
        } else{
            ratio = 0;
        }
        return new Stats(contadorADNMutante, contadorADNHumano, ratio);
    }
}