package tn.esprit.natationproject.services;

import tn.esprit.natationproject.Entite.Centre;
import tn.esprit.natationproject.Entite.Piscine;

import java.util.List;

public interface IPiscineService {
    List<Piscine> getPiscines();
    Piscine getPiscine(int id);
    Piscine addPiscine(Piscine piscine);
    Piscine updatePiscine(Piscine piscine);
    void deletePiscine(int id);
    void affecterPiscineCentre(int idPiscine, int idCentre);
    List<Piscine> getPiscinesParCentre(int idCentre);

}
