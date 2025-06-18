package it.epicode.B_S6_E5.service;

import it.epicode.B_S6_E5.dto.ViaggioDto;
import it.epicode.B_S6_E5.enumeration.Stato;
import it.epicode.B_S6_E5.exception.NotFoundException;
import it.epicode.B_S6_E5.model.Viaggio;
import it.epicode.B_S6_E5.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class ViaggioService {

    @Autowired
    private ViaggioRepository viaggioRepository;





    //Metodo che aggiunge un viaggio
    public Viaggio saveViaggio(ViaggioDto viaggioDto) {
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setData(viaggioDto.getData());
        viaggio.setStato(viaggioDto.getStato());
        return viaggioRepository.save(viaggio);
    }

    //Metodo per estrarre tutti i viaggi
    public Page<Viaggio> getAllViaggi(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }

    //Metodo che restituisce un solo viaggio
    public Viaggio getViaggio(Long id) throws NotFoundException {
        return viaggioRepository.findById(id).orElseThrow(() -> new NotFoundException("Viaggio con id " + id + " non presente"));
    }


    //Metodo per aggiornare un viaggio
    public Viaggio updateViaggio(Long id, ViaggioDto viaggioDto) throws NotFoundException {
        Viaggio viaggio = getViaggio(id);
        viaggio.setDestinazione(viaggioDto.getDestinazione());
        viaggio.setData(viaggioDto.getData());
        viaggio.setStato(viaggioDto.getStato());
        return viaggioRepository.save(viaggio);
    }

    //Metodo per cancellare un viaggio
    public void deleteViaggio(Long id) throws NotFoundException {
        Viaggio viaggioDaCancellare = getViaggio(id);
        viaggioRepository.delete(viaggioDaCancellare);
    }

    //Metodo per aggiornare lo stato del viaggio
    public Viaggio updateStatoViaggio(Long id, Stato nuovoStato) throws NotFoundException {
        Viaggio viaggio = getViaggio(id);
        viaggio.setStato(nuovoStato);
        return viaggioRepository.save(viaggio);
    }
}