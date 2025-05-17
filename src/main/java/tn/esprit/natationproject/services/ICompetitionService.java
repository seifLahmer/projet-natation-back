package tn.esprit.natationproject.services;

import tn.esprit.natationproject.Entite.Competition;

import java.util.List;


public interface ICompetitionService {

    List<Competition> getAllCompetitions();
    Competition getCompetition(int id);
    Competition addCompetition(Competition competition);
    Competition updateCompetition(Competition competition);
    void deleteCompetition(int id);
    Competition createAndAffectCompetition(Competition competition, int idPiscine);
}
