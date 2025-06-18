package it.epicode.B_S6_E5.dto;

import it.epicode.B_S6_E5.enumeration.Stato;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ViaggioDto {
    @NotEmpty(message = " La sezione destinazione non può essere vuota o null")
    private String destinazione;
    @NotNull(message = "La data non può essere null")
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Lo stato non può essere null")
    private Stato stato;
}
