package school.cesar.monitora_pne_backend.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlanoAcao {
    private Long index;
    private String indicador;
    private String estrategia;
    private String planoDeAcao;
    private String observacao;
    private String status;
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
