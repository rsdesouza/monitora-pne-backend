package school.cesar.monitora_pne_backend.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "estrategias")
public class Estrategia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String indicador;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "estrategia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlanoAcao> planos;

    // Getters and setters...
}

enum Status {
    CONCLUIDO, EM_ANDAMENTO, ATRASADO
}
