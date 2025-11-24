package com.example.GlobalMutantes.service;

import com.example.GlobalMutantes.model.ADN;
import com.example.GlobalMutantes.repository.ADNRepository;
import com.example.GlobalMutantes.utils.ADNValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ADNService {
    //inyección de dependencias
    @Autowired
    private ADNRepository adnRepository;

    private final ADNValidator adnValidator = new ADNValidator();

    /*
    METODO SAVE_ADN
    Guarda el ADN ingresado
     */
    public void saveADN(String Adn, boolean isMutant) {
        ADN adn = new ADN();
        adn.setSecuencia(String.join(",", Adn));
        adn.setEsMutante(isMutant);
        adnRepository.save(adn);
    }

    /*
    FUNCIÓN ISMUTANT
    Recibe un array de Strings y determina si es mutante o no de acuerdo a las condiciones
    (ver readme del proyecto)
     */
    public boolean isMutant(String[] adn) {
        if (!adnValidator.isValid(adn)) {
            throw new IllegalArgumentException("La secuencia de ADN es inválida");
        }
        boolean isMutant = checkMutante(adn); //llama a la función checkMutant
        ADN adnEntity = new ADN();
        adnEntity.setSecuencia(String.join(",", adn)); // convierte el array de strings en un string separado por comas y se lo asigna a la entidad ADN recién creada
        adnEntity.setEsMutante(isMutant); //asigna la verificacion (true/false) de si es o no mutante de acuerdo al resultado de la función llamada previamente
        return isMutant; //devuelve el resultado de la verificacion
    }

    /*
    FUNCIÓN CHECKMUTANTE
    Chequea si la secuencia de ADN que recibe de parámetro es mutante o no usando funciones adicionales
     */
    private boolean checkMutante(String[] adn) {
        return checkHorizontal(adn) || checkVertical(adn) || checkDiagonalIzqDer(adn) || checkDiagonalDerIzq(adn); //chequea todas las condiciones
    }

    /*
    FUNCIÓN CHECKHORIZONTAL
    Chequea si hay 4 letras iguales de forma horizontal
     */
    private boolean checkHorizontal(String[] adn) {
        int n = adn.length; //longitud de la matriz
        for (int i = 0; i < n; i++) { //iteracion sobre cada fila
            for (int j = 0; j <= n - 4; j++) { //iteracion sobre cada columna siempre y cuando la cantidad de columnas sea >4 (para poder permitir el calculo)
                if (secuenciaMutante(adn[i].substring(j, j + 4))) {
                    return true; //retorna true si la subcadena desde j hasta j+4 es de un adn mutante (secuenciaMutante(String sequence))
                }
            }
        }
        return false;
    }

    /*
    FUNCIÓN CHECKVERTICAL
    Chequea si hay 4 letras iguales de forma vertical
     */
    private boolean checkVertical(String[] adn) {
        int n = adn.length; // longitud de la matriz
        for (int j = 0; j < n; j++) { //iteracion sobre cada columna
            for (int i = 0; i <= n - 4; i++) { //iteracion sobre cada fila siempre y cuando la cantiad de filas sea >4 (idem)
                if (secuenciaMutante(adn[i].charAt(j), adn[i + 1].charAt(j), adn[i + 2].charAt(j), adn[i + 3].charAt(j))) {
                    return true; //retorna true si los 4 caracteres son iguales (secuenciaMutante(char c1, char c2, char c3, char c4))
                }
            }
        }
        return false;
    }

    /*
    FUNCIÓN CHECKDIAGONALIZQDER
    Chequea si hay 4 letras iguales en las diagonales (recorridas en dirección de izquierda a derecha)
     */
    private boolean checkDiagonalIzqDer(String[] adn) {
        int n = adn.length;
        for (int i = 0; i <= n - 4; i++) { //itera sobre cada fila siempre y cuando la cantidad de filas sea >4
            for (int j = 0; j <= n - 4; j++) { // idem pero con las columnas
                if (secuenciaMutante(adn[i].charAt(j), adn[i + 1].charAt(j + 1), adn[i + 2].charAt(j + 2), adn[i + 3].charAt(j + 3))) {
                    return true; //retorna true si los 4 caracteres son iguales (secuenciaMutante(char c1, char c2, char c3, char c4))
                }
            }
        }
        return false;
    }

    /*
    FUNCIÓN CHECKDIAGONALDERIZQ
    Chequea si hay 4 letras iguales en las diagonales (recorridas en dirección de derecha a izquierda)
     */

    private boolean checkDiagonalDerIzq(String[] adn) {
        int n = adn.length;
        for (int i = 0; i <= n - 4; i++) { //iteracion sobre las filas siempre que la cantidad de filas sea >4
            for (int j = 3; j < n; j++) { //iteracion sobre las columnas, pero empezando desde la columna 3 para que hayan suficientes letras
                if (secuenciaMutante(adn[i].charAt(j), adn[i + 1].charAt(j - 1), adn[i + 2].charAt(j - 2), adn[i + 3].charAt(j - 3))) {
                    return true; //retorna true si los 4 caracteres son iguales (secuenciaMutante(char c1, char c2, char c3, char c4))
                }
            }
        }
        return false;
    }

    /*
    FUNCIONES ADICIONALES
    SECUENCIAMUTANTE
    Chequea si la secuencia contiene 4 letras o más iguales
     */
    private boolean secuenciaMutante(String secuencia) {
        return secuencia.equals("AAAA") || secuencia.equals("TTTT") || secuencia.equals("CCCC") || secuencia.equals("GGGG");
    }

    private boolean secuenciaMutante(char c1, char c2, char c3, char c4) {
        return c1 == c2 && c2 == c3 && c3 == c4;
    }
}
