package it.epicode.B_S6_E5.repository;

import it.epicode.B_S6_E5.model.Dipendente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DipendenteRepository extends JpaRepository<Dipendente, Long>,
        PagingAndSortingRepository<Dipendente, Long> {
}
