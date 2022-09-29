package com.devsuperior.dsclients.services;

import com.devsuperior.dsclients.dtos.ClientDto;
import com.devsuperior.dsclients.repositories.ClientRepository;
import com.devsuperior.dsclients.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<ClientDto> findAllPage(PageRequest pageRequest) {
        var entity = clientRepository.findAll(pageRequest);
        return entity.map(client -> new ClientDto(client));
    }

    public ClientDto findById(Long id) {
        var obj = clientRepository.findById(id);
        var entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDto(entity);
    }
}
