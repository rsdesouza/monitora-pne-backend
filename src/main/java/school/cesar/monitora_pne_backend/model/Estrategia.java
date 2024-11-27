package school.cesar.monitora_pne_backend.model;

import lombok.Data;

@Data
public class Estrategia {

    private Long index;
    private String indicador;
    private String nomeEstrategia;
    private String mes;
    private Integer valor;
    private Integer ano;
    private Status status;

    public enum Status {
        CONCLUIDO, EM_ANDAMENTO, ATRASADO
    }
}
