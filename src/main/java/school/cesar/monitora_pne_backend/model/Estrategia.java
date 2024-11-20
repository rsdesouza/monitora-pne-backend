package school.cesar.monitora_pne_backend.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class Estrategia {

    private Long id;
    private String nome;
    private String descricao;
    private String indicador;
    private Status status;
    private List<PlanoAcao> planos;

    enum Status {
        CONCLUIDO, EM_ANDAMENTO, ATRASADO
    }

    // Construtor padrão
    public Estrategia() {
        this.planos = new ArrayList<>();
    }

    // Construtor com argumentos
    public Estrategia(Long id, String nome, String descricao, String indicador, Status status) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.indicador = indicador;
        this.status = status;
        this.planos = new ArrayList<>();
    }

//    // Métodos de negócios
//    public void adicionarPlano(PlanoAcao plano) {
//        if (plano != null) {
//            planos.add(plano);
//            plano.setEstrategia(this); // Estabelece a relação bidirecional
//        }
//    }
//
//    public void removerPlano(PlanoAcao plano) {
//        if (plano != null && planos.contains(plano)) {
//            planos.remove(plano);
//            plano.setEstrategia(null); // Remove a relação bidirecional
//        }
//    }

    public boolean isConcluido() {
        return Status.CONCLUIDO.equals(this.status);
    }

    public void concluir() {
        this.status = Status.CONCLUIDO;
    }

    public void atrasar() {
        this.status = Status.ATRASADO;
    }

    public void iniciar() {
        this.status = Status.EM_ANDAMENTO;
    }

}
