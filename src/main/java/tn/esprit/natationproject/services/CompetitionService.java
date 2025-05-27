package tn.esprit.natationproject.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.natationproject.Entite.Competition;
import tn.esprit.natationproject.Entite.Piscine;
import tn.esprit.natationproject.repositories.CompetitionRepository;
import tn.esprit.natationproject.repositories.PiscineRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class CompetitionService implements ICompetitionService {
    CompetitionRepository competitionRepository;
    PiscineRepository piscineRepository;

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition getCompetition(int id) {
        return competitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Competition avec ID " + id + " non trouvée"));

    }

    @Override
    public Competition addCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Override
    public Competition updateCompetition(Competition competition) {
        return competitionRepository.save(competition);
    }

    @Override
    public void deleteCompetition(int id) {
         competitionRepository.deleteById(id);
    }

    public Competition createAndAffectCompetition(Competition competition, int idPiscine) {
        // Sauvegarder la compétition sans piscine
        competition.setPiscine(null); // ou assurez-vous qu'elle ne soit pas en double
        Competition saved = competitionRepository.save(competition);

        // Puis affecter la piscine
        Piscine piscine = piscineRepository.findById(idPiscine)
                .orElseThrow(() -> new RuntimeException("Piscine non trouvée"));
        saved.setPiscine(piscine);

        return competitionRepository.save(saved); // Réenregistrer avec la piscine
    }
}
