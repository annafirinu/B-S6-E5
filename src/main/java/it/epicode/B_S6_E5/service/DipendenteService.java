package it.epicode.B_S6_E5.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import it.epicode.B_S6_E5.dto.DipendenteDto;
import it.epicode.B_S6_E5.exception.NotFoundException;
import it.epicode.B_S6_E5.model.Dipendente;
import it.epicode.B_S6_E5.repository.DipendenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private Cloudinary cloudinary;

    public List<Dipendente> getAllDipendenti() {
        return dipendenteRepository.findAll();
    }

    public Dipendente getDipendenteById(Long id) throws NotFoundException {
        return dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Dipendente non trovato con id: " + id));
    }

    //Metodo che aggiunge un dipendente
    public Dipendente saveDipendente(DipendenteDto dipendenteDto) {
        Dipendente dipendente = new Dipendente();
        dipendente.setUsername(dipendenteDto.getUsername());
        dipendente.setNome(dipendenteDto.getNome());
        dipendente.setCognome(dipendenteDto.getCognome());
        dipendente.setEmail(dipendenteDto.getEmail());
        return dipendenteRepository.save(dipendente);
    }

    //Metodo per estrarre tutti i dipendenti
    public Page<Dipendente> getAllDipendenti(int page, int size, String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    //Metodo che restituisce un solo dipendente
    public Dipendente getDipendente(Long id) throws NotFoundException{
        return dipendenteRepository.findById(id).orElseThrow(()->new NotFoundException("Dipendente con id " + id + " non presente"));
    }


    //Metodo per aggiornare un dipendente
    public Dipendente updateDipendente(Long id, DipendenteDto dipendenteDto) throws NotFoundException {
        Dipendente dipendenteDaAggiornare = getDipendenteById(id);
        dipendenteDaAggiornare.setUsername(dipendenteDto.getUsername());
        dipendenteDaAggiornare.setNome(dipendenteDto.getNome());
        dipendenteDaAggiornare.setCognome(dipendenteDto.getCognome());
        dipendenteDaAggiornare.setEmail(dipendenteDto.getEmail());
        return dipendenteRepository.save(dipendenteDaAggiornare);
    }

    //Metodo per eliminare un dipendente
    public void deleteDipendente(Long id) throws NotFoundException {
        Dipendente dipendenteDaCancellare = getDipendenteById(id);
        dipendenteRepository.delete(dipendenteDaCancellare);
    }

    //Metodo per aggiungere l'immagine del profilo
    public Dipendente patchDipendente(Long id, MultipartFile file) throws NotFoundException, IOException {
        Dipendente dipendenteDaPatchare = getDipendenteById(id);

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

        String url = (String) uploadResult.get("url");
        dipendenteDaPatchare.setImmagineProfilo(url);

        return dipendenteRepository.save(dipendenteDaPatchare);
    }

}