package tn.esprit.natationproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendValidationEmail(String toEmail, String nom, String prenom, String nomClub) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("eyatrabelsi868@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Validation de votre compte Chef d'Équipe");

        String body = "Cher " + prenom + " " + nom + ",\n\n" +
                "Votre compte Chef d'Équipe pour le club " + nomClub +
                " a été validé par l'administrateur de la Fédération Tunisienne de Natation.\n\n" +
                "Vous pouvez maintenant vous connecter à votre espace personnel.\n\n" +
                "Cordialement,\n" +
                "La Fédération Tunisienne de Natation";

        message.setText(body);

        mailSender.send(message);
    }
    public void sendResetPasswordEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Réinitialisation de mot de passe - Fédération Tunisienne de Natation");

        String emailContent = "Bonjour,\n\n"
                + "Vous avez demandé à réinitialiser votre mot de passe. Cliquez sur le lien suivant pour définir un nouveau mot de passe :\n"
                + resetLink + "\n\n"
                + "Ce lien expirera dans 24 heures.\n\n"
                + "Si vous n'avez pas fait cette demande, veuillez ignorer cet email.\n\n"
                + "Cordialement,\n"
                + "L'équipe de la Fédération Tunisienne de Natation";

        message.setText(emailContent);
        mailSender.send(message);
    }
}