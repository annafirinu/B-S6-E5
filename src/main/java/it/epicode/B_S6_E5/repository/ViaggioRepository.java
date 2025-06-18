package it.epicode.B_S6_E5.repository;

import it.epicode.B_S6_E5.model.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ViaggioRepository extends JpaRepository<Viaggio, Long>,
        PagingAndSortingRepository<Viaggio, Long> {
}
