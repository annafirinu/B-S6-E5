package it.epicode.B_S6_E5.service;

import it.epicode.B_S6_E5.dto.PrenotazioneDto;
import it.epicode.B_S6_E5.exception.NotFoundException;
import it.epicode.B_S6_E5.exception.ValidationException;
import it.epicode.B_S6_E5.model.Dipendente;
import it.epicode.B_S6_E5.model.Prenotazione;
import it.epicode.B_S6_E5.model.Viaggio;
import it.epicode.B_S6_E5.repository.DipendenteRepository;
import it.epicode.B_S6_E5.repository.PrenotazioneRepository;
import it.epicode.B_S6_E5.repository.ViaggioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private ViaggioRepository viaggioRepository;

    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private ViaggioService viaggioService;


    //Metodo che aggiunge una prenotazione
    public Prenotazione savePrenotazione(PrenotazioneDto prenotazioneDto) throws NotFoundException, ValidationException {
        // Verifica doppia prenotazione
        List<Prenotazione> prenotazioniEsistenti = prenotazioneRepository.findByDipendenteIdAndDataRichiesta(prenotazioneDto.getDipendenteId(), prenotazioneDto.getDataRichiesta());
        if (!prenotazioniEsistenti.isEmpty()) {
            throw new ValidationException("Il dipendente ha già una prenotazione per questa data.");
        }

        Dipendente dipendente = dipendenteService.getDipendenteById(prenotazioneDto.getDipendenteId());
        Viaggio viaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazione.setPreferenze(prenotazioneDto.getPreferenze());
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);

        return prenotazioneRepository.save(prenotazione);
    }

    //Metodo per estrarre tutte le prenotazioni
    public Page<Prenotazione> getAllPrenotazioni(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    //Metodo che restituisce una sola prenotazione
    public Prenotazione getPrenotazione(Long id) throws NotFoundException {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione con id " + id + " non presente"));
    }

    //Metodo per aggiornare una prenotazione
    public Prenotazione updatePrenotazione(Long id, PrenotazioneDto prenotazioneDto) throws NotFoundException {
        Prenotazione prenotazioneDaAggiornare = getPrenotazione(id);

        prenotazioneDaAggiornare.setDataRichiesta(prenotazioneDto.getDataRichiesta());
        prenotazioneDaAggiornare.setPreferenze(prenotazioneDto.getPreferenze());

        if (!prenotazioneDaAggiornare.getDipendente().getId().equals(prenotazioneDto.getDipendenteId())) {
            Dipendente nuovoDipendente = dipendenteService.getDipendente(prenotazioneDto.getDipendenteId());
            prenotazioneDaAggiornare.setDipendente(nuovoDipendente);

        }

        if (!prenotazioneDaAggiornare.getViaggio().getId().equals(prenotazioneDto.getViaggioId())){
            Viaggio nuovoViaggio = viaggioService.getViaggio(prenotazioneDto.getViaggioId());
            prenotazioneDaAggiornare.setViaggio(nuovoViaggio);
        }

        return prenotazioneRepository.save(prenotazioneDaAggiornare);
    }




    //Metodo per cancellare una prenotazione
    public void deletePrenotazione(Long id) throws NotFoundException {
        Prenotazione prenotazioneDaCancellare = getPrenotazione(id);
        prenotazioneRepository.delete(prenotazioneDaCancellare);
    }
}
