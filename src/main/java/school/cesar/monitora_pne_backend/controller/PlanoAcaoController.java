package school.cesar.monitora_pne_backend.controller;

import org.springframework.web.bind.annotation.*;
import school.cesar.monitora_pne_backend.model.PlanoAcao;
import school.cesar.monitora_pne_backend.service.PlanoAcaoService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/planos")
public class PlanoAcaoController {

    private final PlanoAcaoService planoAcaoService;

    public PlanoAcaoController(PlanoAcaoService planoAcaoService) {
        this.planoAcaoService = planoAcaoService;
    }

    @GetMapping
    public List<PlanoAcao> listarPlanos() throws IOException {
        return planoAcaoService.listarPlanos();
    }

    @PostMapping
    public void adicionarPlano(@RequestBody PlanoAcao plano) throws IOException {
        planoAcaoService.adicionarPlano(plano);
    }

    @PutMapping("/{index}")
    public void atualizarPlano(@PathVariable Long index, @RequestBody PlanoAcao plano) throws IOException {
        planoAcaoService.atualizarPlano(index, plano);
    }

    @DeleteMapping("/{index}")
    public void excluirPlano(@PathVariable Long index) throws IOException {
        planoAcaoService.excluirPlano(index);
    }
}
