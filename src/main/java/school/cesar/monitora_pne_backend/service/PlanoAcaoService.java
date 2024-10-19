package school.cesar.monitora_pne_backend.service;

import org.springframework.stereotype.Service;
import school.cesar.monitora_pne_backend.model.PlanoAcao;
import school.cesar.monitora_pne_backend.repository.PlanoAcaoRepository;

import java.util.List;

@Service
public class PlanoAcaoService {

    private final PlanoAcaoRepository repository;

    public PlanoAcaoService(PlanoAcaoRepository repository) {
        this.repository = repository;
    }

    public List<PlanoAcao> findAll() {
        return repository.findAll();
    }

    public PlanoAcao save(PlanoAcao plano) {
        return repository.save(plano);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
