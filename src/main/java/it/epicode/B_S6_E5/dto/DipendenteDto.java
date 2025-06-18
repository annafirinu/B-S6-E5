package it.epicode.B_S6_E5.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class DipendenteDto {
    @NotEmpty(message = "E' obbligatorio inserire l'username")
    private String username;
    @NotEmpty(message = "E' obbligatorio inserire il nome")
    private String nome;
    @NotEmpty(message = "E' obbligatorio inserire il cognome")
    private String cognome;
    @Email(message = " Inserisci un indirizzo email valido (indirizzo@epicode.it)")
    private String email;
}