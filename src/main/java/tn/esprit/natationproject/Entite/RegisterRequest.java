package tn.esprit.natationproject.Entite;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterRequest {
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private String confirmPassword;
    private String telephone;
    private String nomClub;
    private String adresseClub;
    private MultipartFile documentJustificatif;


}