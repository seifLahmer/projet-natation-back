package tn.esprit.natationproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.natationproject.Entite.*;
import tn.esprit.natationproject.repositories.CompetitionRepository;
import tn.esprit.natationproject.repositories.InscriptionRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InscriptionService implements IInscriptionService {

    InscriptionRepository inscriptionRepository;
    CompetitionRepository competitionRepository;
    @Override
    public List<Inscription> getInscriptions() {
        return inscriptionRepository.findAll();
    }

    @Override
    public Inscription getInscription(int id) {
        return inscriptionRepository.findById(id).get();
    }

    @Override
    public Inscription addInscription(Inscription inscription) {
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Inscription updateInscription(Inscription inscription) {
            inscriptionRepository.save(inscription);
        return inscription;
    }

    @Override
    public void deleteInscription(int id) {
        inscriptionRepository.deleteById(id);
    }

    @Override
    public void inscrireUtilisateurACompetition(int idCompetition, Long idUtilisateur) {
        Optional<Competition> competitionOpt = competitionRepository.findById(idCompetition);


        Inscription inscription = new Inscription();
        inscription.setCompetition(competitionOpt.get());
        Utilisateurs utilisateurs = new Utilisateurs();
        utilisateurs.setId(idUtilisateur);
        inscription.setUtilisateurs(utilisateurs);
        inscription.setDateInscription(new Date());
        inscription.setStatut(StatutInscription.EN_ATTENTE);

        inscriptionRepository.save(inscription);

    }

    @Override
    public List<Inscription> getInscriptionsByUserId(Long userId) {
        return inscriptionRepository.findInscriptionByUtilisateurs_Id(userId);
    }

    @Override
    public Competition getCompetitionByInscriptionId(int inscriptionId) {
        Inscription inscription = inscriptionRepository.findById(inscriptionId)
                .orElseThrow(() -> new RuntimeException("Inscription introuvable"));

        return inscription.getCompetition();
    }

    // Nouvelle méthode pour récupérer les utilisateurs inscrits à une compétition
    @Override
    public List<Utilisateurs> getUtilisateursByCompetitionId(int competitionId) {
        List<Inscription> inscriptions = inscriptionRepository.findInscriptionByCompetition_IdReservation(competitionId);

        return inscriptions.stream()
                .map(Inscription::getUtilisateurs)
                .collect(Collectors.toList());
    }

    // Méthode alternative pour récupérer les inscriptions d'une compétition
    @Override
    public List<Inscription> getInscriptionsByCompetitionId(int competitionId) {
        return inscriptionRepository.findInscriptionByCompetition_IdReservation(competitionId);
    }


}
