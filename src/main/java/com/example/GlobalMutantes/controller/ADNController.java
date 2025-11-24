package com.example.GlobalMutantes.controller;

import com.example.GlobalMutantes.dto.ADNRequest;
import com.example.GlobalMutantes.service.ADNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mutant")
public class ADNController {
    @Autowired
    private ADNService adnService;

    //metodo POST isMutant
    @PostMapping
    public ResponseEntity<String> isMutant(@RequestBody ADNRequest adnRequest) {
        String[] adn = adnRequest.getSecuencia();  // Recibe el array de strings del request
        boolean esMutante = adnService.isMutant(adn);
        adnService.saveADN(String.join(",", adn), esMutante);
        if (esMutante) {
            System.out.println("ADN Mutante"); //para testear
            return new ResponseEntity<>("El ADN ingresado es de un Mutante", HttpStatus.OK);
        } else {
            System.out.println("ADN Humano"); //para testear
            return new ResponseEntity<>("El ADN ingresado es de un Humano", HttpStatus.FORBIDDEN);
        }
    }
}

