package tn.esprit.natationproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.natationproject.Entite.Centre;
import tn.esprit.natationproject.Entite.Piscine;
import tn.esprit.natationproject.repositories.CentreRepository;
import tn.esprit.natationproject.repositories.PiscineRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PiscineService implements IPiscineService {

    PiscineRepository piscineRepository;
    CentreRepository centreRepository;

    @Override
    public List<Piscine> getPiscines() {
        return piscineRepository.findAll();
    }

    @Override
    public Piscine getPiscine(int id) {
        return piscineRepository.findById(id).get();
    }

    @Override
    public Piscine addPiscine(Piscine piscine) {
        return piscineRepository.save(piscine);
    }

    @Override
    public Piscine updatePiscine(Piscine piscine) {
        return piscineRepository.save(piscine);
    }

    @Override
    public void deletePiscine(int id) {
        piscineRepository.deleteById(id);
    }

    @Override
    public void affecterPiscineCentre(int idPiscine, int idCentre) {
        Centre c = centreRepository.findById(idCentre)
                .orElseThrow(() -> new RuntimeException("Centre introuvable"));
        Piscine p = piscineRepository.findById(idPiscine)
                .orElseThrow(() -> new RuntimeException("Piscine introuvable"));

        p.setCentre(c);
        piscineRepository.save(p);

    }

    @Override
    public List<Piscine> getPiscinesParCentre(int idCentre) {
        return piscineRepository.findPiscinesByCentre_IdCentre(idCentre);

    }
}
