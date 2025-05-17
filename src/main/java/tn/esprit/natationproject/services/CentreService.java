package tn.esprit.natationproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.natationproject.Entite.Centre;
import tn.esprit.natationproject.repositories.CentreRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CentreService implements ICentreService{

    CentreRepository centreRepository;


    @Override
    public List<Centre> getCentres() {
        return centreRepository.findAll();
    }

    @Override
    public Centre getCentre(int id) {
        return centreRepository.findById(id).get();
    }

    @Override
    public Centre addCentre(Centre centre) {
        return centreRepository.save(centre);
    }

    @Override
    public Centre updateCentre(Centre centre) {
        return centreRepository.save(centre);
    }

    @Override
    public Centre updaCentreById(Centre c,int id) {
        return null;
    }

    @Override
    public void deleteCentre(int id) {
         centreRepository.deleteById(id);
    }
}
