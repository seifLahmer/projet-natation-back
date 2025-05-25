package tn.esprit.natationproject.services;

import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.Entite.Inscription;
import tn.esprit.natationproject.Entite.Utilisateurs;

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

    List<Utilisateurs> getUtilisateursByCompetitionId(int competitionId);
    List<Inscription> getInscriptionsByCompetitionId(int competitionId);

}
