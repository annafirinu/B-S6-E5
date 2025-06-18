package it.epicode.B_S6_E5.controller;

import it.epicode.B_S6_E5.dto.DipendenteDto;
import it.epicode.B_S6_E5.exception.NotFoundException;
import it.epicode.B_S6_E5.exception.ValidationException;
import it.epicode.B_S6_E5.model.Dipendente;
import it.epicode.B_S6_E5.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente saveDipendente(@RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return dipendenteService.saveDipendente(dipendenteDto);
    }

    @GetMapping("")
    public Page<Dipendente> getAllDipendenti(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size,
                                         @RequestParam(defaultValue = "id") String sortBy){
        return dipendenteService.getAllDipendenti(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Dipendente getDipendente(@PathVariable Long id) throws NotFoundException {
        return dipendenteService.getDipendente(id);
    }

    @PutMapping("/{id}")
    public Dipendente updateDipendente(@PathVariable Long id, @RequestBody @Validated DipendenteDto dipendenteDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));}
        return dipendenteService.updateDipendente(id, dipendenteDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDipendente(@PathVariable Long id) throws NotFoundException {
        dipendenteService.deleteDipendente(id);
    }

    @PatchMapping("/{id}")
    public Dipendente uploadImmagine(@PathVariable Long id, @RequestBody MultipartFile file)
            throws NotFoundException, IOException {
        return dipendenteService.patchDipendente(id, file);
    }
}
