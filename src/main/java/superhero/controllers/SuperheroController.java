package superhero.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import superhero.models.Superhero;
import superhero.models.SuperheroRepository;
import superhero.validators.IsTrueSuperhero;

@Validated
@Controller
@RequestMapping(value = "/superhero")
public class SuperheroController {

    @Autowired
    private SuperheroRepository repository;

    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Superhero> addSuperhero(@RequestBody @IsTrueSuperhero Superhero requestItem) {

        return new ResponseEntity<>(repository.save(requestItem), HttpStatus.OK);
    }

    @PreAuthorize("authenticated")
    @RequestMapping(value = {"/", ""},
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Superhero>> getAll() {
        return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Superhero> getSuperhero(@PathVariable("id") Long id) {
        return new ResponseEntity<>(repository.findOne(id), HttpStatus.OK);
    }

    @PreAuthorize("authenticated")
    @RequestMapping(method = RequestMethod.DELETE,
            value = "/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        repository.delete(id);
    }

}
