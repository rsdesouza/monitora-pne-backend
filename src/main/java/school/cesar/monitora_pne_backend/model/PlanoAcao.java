package school.cesar.monitora_pne_backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

@Data
public class PlanoAcao {
    private Long index;
    private String indicador;
    private String estrategia;
    private String planoDeAcao;
    private String observacao;
    private Status status;
    private String dataInicio;
    private String dataFim;

    public enum Status {
        CONCLUIDO, EM_ANDAMENTO, ATRASADO, DESCONTINUADO;

        @JsonCreator
        public static Status fromValue(String value) {
            return Status.valueOf(value.toUpperCase().replace(" ", "_"));
        }

        @JsonValue
        public String toValue() {
            return this.name().toLowerCase().replace("_", " ");
        }
    }
}
