package tn.esprit.natationproject.services;

import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.Entite.Inscription;

import java.util.List;

public interface IInscriptionService {

    List<Inscription> getInscriptions();
    Inscription getInscription(int id);
    Inscription addInscription(Inscription inscription);
    Inscription updateInscription(Inscription inscription);
    void deleteInscription(int id);
    void inscrireUtilisateurACompetition(int idCompetition, Long idUtilisateur);
    List<Inscription> getInscriptionsByUserId(Long userId);
     Competition getCompetitionByInscriptionId(int inscriptionId);
}
