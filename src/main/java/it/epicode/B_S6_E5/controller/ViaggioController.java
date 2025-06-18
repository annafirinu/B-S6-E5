package it.epicode.B_S6_E5.controller;

import it.epicode.B_S6_E5.dto.ViaggioDto;
import it.epicode.B_S6_E5.enumeration.Stato;
import it.epicode.B_S6_E5.exception.NotFoundException;
import it.epicode.B_S6_E5.exception.ValidationException;
import it.epicode.B_S6_E5.model.Viaggio;
import it.epicode.B_S6_E5.service.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    @Autowired
    private ViaggioService viaggioService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio saveViaggio(@RequestBody @Validated ViaggioDto viaggioDto, BindingResult bindingResult) throws ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return viaggioService.saveViaggio(viaggioDto);
    }

    @GetMapping("")
    public Page<Viaggio> getAllViaggi(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(defaultValue = "id") String sortBy){
        return viaggioService.getAllViaggi(page, size, sortBy);
    }

    @GetMapping("/{id}")
    public Viaggio getViaggio(@PathVariable Long id) throws NotFoundException {
        return viaggioService.getViaggio(id);
    }

    @PutMapping("/{id}")
    public Viaggio updateViaggio(@PathVariable Long id, @RequestBody @Validated ViaggioDto viaggioDto, BindingResult bindingResult) throws NotFoundException, ValidationException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().
                    stream().map(objectError -> objectError.getDefaultMessage()).
                    reduce("",(e,s)->e+s));
        }
        return viaggioService.updateViaggio(id, viaggioDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteViaggio(@PathVariable Long id) throws NotFoundException {
        viaggioService.deleteViaggio(id);
    }

    @PatchMapping("/{id}/stato")
    public Viaggio updateStatoViaggio(@PathVariable Long id, @RequestParam Stato stato) throws NotFoundException {
        return viaggioService.updateStatoViaggio(id, stato);
    }
}
