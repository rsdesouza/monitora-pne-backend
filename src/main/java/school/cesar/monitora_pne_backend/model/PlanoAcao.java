package school.cesar.monitora_pne_backend.model;

import lombok.Data;

@Data
public class PlanoAcao {
    private String mes;
    private int valor;
    private int ano;
    private Estrategia estrategia;
}

