package com.nocountry.messenger.controller;

import com.nocountry.messenger.dto.request.ClientRequest;
import com.nocountry.messenger.dto.response.DeleteOkResponse;
import com.nocountry.messenger.exception.custom.ClientAlreadyExist;
import com.nocountry.messenger.exception.custom.ExceptionHandler;
import com.nocountry.messenger.service.IClientService;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {
    
    @Autowired
    private IClientService clientService;
    
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(@Valid @RequestBody ClientRequest clientModel)
    throws ClientAlreadyExist {
        try {
            clientService.create(clientModel);
            return new ResponseEntity<>(clientModel , HttpStatus.CREATED);
        } catch (ClientAlreadyExist e) {
            return ExceptionHandler.throwError(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ExceptionHandler.throwError(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listClients() {
        return new ResponseEntity<>(clientService.listClients(), HttpStatus.OK);
    }
    
    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> update (@Valid @RequestBody ClientRequest request, @PathVariable Long id)
    throws NoSuchElementException{
        try {
            return new ResponseEntity<>(clientService.update(request, id), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return ExceptionHandler.throwError(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
    
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> delete(@Valid @PathVariable Long id) {
        try {
            clientService.delete(id);
            return DeleteOkResponse.deleteOk(HttpStatus.OK, id);
        } catch (NoSuchElementException e) {
            return ExceptionHandler.throwError(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
