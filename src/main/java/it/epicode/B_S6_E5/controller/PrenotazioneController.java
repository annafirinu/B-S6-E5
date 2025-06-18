package it.epicode.B_S6_E5.controller;

import it.epicode.B_S6_E5.dto.PrenotazioneDto;
import it.epicode.B_S6_E5.exception.NotFoundException;
import it.epicode.B_S6_E5.exception.ValidationException;
import it.epicode.B_S6_E5.model.Prenotazione;
import it.epicode.B_S6_E5.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione savePrenotazione(@RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult)
            throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return prenotazioneService.savePrenotazione(prenotazioneDto);
    }

    @GetMapping("")
    public Page<Prenotazione> getAllPrenotazioni(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size,
                                             @RequestParam(defaultValue = "id") String sortBy){
        return prenotazioneService.getAllPrenotazioni(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Prenotazione getPrenotazione(@PathVariable Long id) throws NotFoundException {
        return prenotazioneService.getPrenotazione(id);
    }

    @PutMapping("/{id}")
    public Prenotazione updatePrenotazione(@PathVariable Long id, @RequestBody @Validated PrenotazioneDto prenotazioneDto, BindingResult bindingResult)
            throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return prenotazioneService.updatePrenotazione(id, prenotazioneDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePrenotazione(@PathVariable Long id) throws NotFoundException {
        prenotazioneService.deletePrenotazione(id);
    }

}
