package tn.esprit.natationproject.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SmsEmailService {
    private final JavaMailSender mailSender;

    public SmsEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendSmsViaEmail(String phoneNumber, String nom, String prenom, String nomClub) {
        String message = String.format(
                "Cher %s %s, votre compte Chef d'Équipe pour le club %s a été validé.",
                prenom, nom, nomClub
        );

        try {
            String carrierEmail = mapPhoneNumberToEmail(phoneNumber);

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(carrierEmail);
            email.setSubject("");
            email.setText(message);

            mailSender.send(email);
        } catch (Exception e) {
            throw new RuntimeException("Échec d'envoi SMS par email: " + e.getMessage());
        }
    }

    private String mapPhoneNumberToEmail(String phoneNumber) {
        // Supprime tout sauf les chiffres
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

        // Si le numéro commence par 216 (indicatif Tunisie), on le retire
        if (cleanedNumber.startsWith("216") && cleanedNumber.length() > 3) {
            cleanedNumber = cleanedNumber.substring(3);
        }

        // Vérifie que le numéro a 8 chiffres (format local Tunisien)
        if (cleanedNumber.length() != 8) {
            throw new IllegalArgumentException("Numéro tunisien invalide");
        }

        String prefix = cleanedNumber.substring(0, 2);

        return switch (prefix) {
            case "20", "21", "22", "23", "24", "25", "26", "27", "28", "29" -> cleanedNumber + "@ooredoo.tn";
            case "50", "51", "52", "53", "54", "55", "56", "57" -> cleanedNumber + "@orange.tn";
            case "90", "91", "92", "93", "94", "95", "96", "97" -> cleanedNumber + "@tt.tn";
            default -> throw new IllegalArgumentException("Opérateur inconnu pour: " + phoneNumber);
        };
    }}