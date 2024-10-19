package school.cesar.monitora_pne_backend.controller;

import org.springframework.web.bind.annotation.*;
import school.cesar.monitora_pne_backend.model.PlanoAcao;
import school.cesar.monitora_pne_backend.service.PlanoAcaoService;

import java.util.List;

@RestController
@RequestMapping("/api/planos-acao")
public class PlanoAcaoController {

    private final PlanoAcaoService service;

    public PlanoAcaoController(PlanoAcaoService service) {
        this.service = service;
    }

    @GetMapping
    public List<PlanoAcao> getAll() {
        return service.findAll();
    }

    @PostMapping
    public PlanoAcao create(@RequestBody PlanoAcao plano) {
        return service.save(plano);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
