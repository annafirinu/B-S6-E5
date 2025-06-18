package it.epicode.B_S6_E5.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrenotazioneDto {
    @NotNull(message = "La data non può essere null")
    private LocalDate dataRichiesta;
    private String preferenze;
    @NotNull(message = "Il dipendenteId non può essere null")
    private Long dipendenteId;
    @NotNull(message = "Il viaggioId non può essere null")
    private Long viaggioId;
}
