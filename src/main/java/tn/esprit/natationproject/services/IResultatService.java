package tn.esprit.natationproject.services;

import tn.esprit.natationproject.Entite.Resultat;
import tn.esprit.natationproject.Entite.typeCompetition;

import java.util.List;

public interface IResultatService {
    List<Resultat> getResultat();
    Resultat getResultat(int idResultat);
    Resultat addResultat(Resultat resultat);
    Resultat updateResultat(Resultat resultat);
    void deleteResultat(int idResultat);
    List<Resultat> getResultatsParTypeCompetition(typeCompetition typeC);

}
