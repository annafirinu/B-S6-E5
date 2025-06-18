package it.epicode.B_S6_E5.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.epicode.B_S6_E5.enumeration.Stato;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Viaggio {
    @Id
    @GeneratedValue
    private Long id;
    private String destinazione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private Stato stato;

    @JsonIgnore
    @OneToMany(mappedBy = "viaggio")
    private List<Prenotazione> prenotazioni;
}
