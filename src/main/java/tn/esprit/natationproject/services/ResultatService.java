package tn.esprit.natationproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.natationproject.Entite.Resultat;
import tn.esprit.natationproject.Entite.typeCompetition;
import tn.esprit.natationproject.repositories.ResultatRepository;
import java.util.List;
@Service
@AllArgsConstructor
public class ResultatService implements IResultatService {

    ResultatRepository resultatRepository;

    @Override
    public List<Resultat> getResultat() {
        return resultatRepository.findAll();
    }

    @Override
    public Resultat getResultat(int idResultat) {
        return resultatRepository.findById(idResultat).get();
    }

    @Override
    public Resultat addResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }

    @Override
    public Resultat updateResultat(Resultat resultat) {
        return resultatRepository.save(resultat);
    }

    @Override
    public void deleteResultat(int idResultat) {
        resultatRepository.deleteById(idResultat);
    }

    @Override
    public List<Resultat> getResultatsParTypeCompetition(typeCompetition typeC) {
        return resultatRepository.findByCompetition_TypeC(typeC);
    }
}
