package com.example.GlobalMutantes.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ADNValidator {

    //VERIFICAR SI LA SECUENCIA NO ES NULA
    public boolean isValid(String[] adn) {
        if (adn == null || adn.length == 0) {
            return false;
        }

        //VERIFICAR QUE LA MATRIZ ES NXN
        int n = adn.length;
        for (String row : adn) {
            if (row == null || row.length() != n) {
                return false;
            }
            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false;
                }
            }
        }
        return true;
    }
}
