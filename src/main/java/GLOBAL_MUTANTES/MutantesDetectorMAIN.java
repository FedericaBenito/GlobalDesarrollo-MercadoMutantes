package GLOBAL_MUTANTES;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MutantesDetectorMAIN {

    public static void main(String[] args) {
        SpringApplication.run(MutantesDetectorMAIN.class, args);

        System.out.println("Se ha inicializado la aplicacion");
    }

}
