package tn.esprit.natationproject.services;

import tn.esprit.natationproject.Entite.Centre;

import java.util.List;

public interface ICentreService {

    List<Centre> getCentres();
    Centre getCentre(int id);
    Centre addCentre(Centre centre);
    Centre updateCentre(Centre centre);
    Centre updaCentreById(Centre centre,int id);
    void deleteCentre(int id);

}
