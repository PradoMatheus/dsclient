package com.devsuperior.dsclients.resources;

import com.devsuperior.dsclients.dtos.ClientDto;
import com.devsuperior.dsclients.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/clients")
public class ClientResource {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<Page<ClientDto>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "OrderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        var list = clientService.findAllPage(pageRequest);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> findById(@PathVariable(value = "id") Long id) {
        var dto = clientService.findById(id);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

//    @PostMapping
//    public ResponseEntity<ClientDto> insert(@RequestBody ClientDto clientDto) {
//
//    }
//
//    @PutMapping(value = "/{id}")
//    public ResponseEntity<ClientDto> update(@PathVariable(value = "id") Long id, @RequestBody ClientDto clientDto) {
//
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public ResponseEntity<Void> delete(@PathVariable(value = "id") Long id) {
//
//    }
}
