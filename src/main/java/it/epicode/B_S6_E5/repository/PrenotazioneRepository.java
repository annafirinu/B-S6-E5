package it.epicode.B_S6_E5.repository;

import it.epicode.B_S6_E5.model.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long>,
        PagingAndSortingRepository<Prenotazione, Long> {

    List<Prenotazione> findByDipendenteIdAndDataRichiesta(Long dipendenteId, LocalDate dataRichiesta);
}
