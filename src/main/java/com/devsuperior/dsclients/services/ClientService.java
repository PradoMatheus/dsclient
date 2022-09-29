package com.devsuperior.dsclients.services;

import com.devsuperior.dsclients.dtos.ClientDto;
import com.devsuperior.dsclients.entities.Client;
import com.devsuperior.dsclients.repositories.ClientRepository;
import com.devsuperior.dsclients.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Transactional(readOnly = true)
    public Page<ClientDto> findAllPage(PageRequest pageRequest) {
        var entity = clientRepository.findAll(pageRequest);
        return entity.map(client -> new ClientDto(client));
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Long id) {
        var obj = clientRepository.findById(id);
        var entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return new ClientDto(entity);
    }

    @Transactional()
    public ClientDto insert(ClientDto clientDto) {
        var client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return new ClientDto(clientRepository.save(client));
    }

    @Transactional()
    public ClientDto update(Long id, ClientDto clientDto) {
        try {
            var entity = clientRepository.getReferenceById(id);
            entity.setChildren(clientDto.getChildren());
            entity.setBirthDate(clientDto.getBirthDate());
            entity.setCpf(clientDto.getCpf());
            entity.setIncome(clientDto.getIncome());
            entity.setName(clientDto.getName());
            entity = clientRepository.save(entity);
            return new ClientDto(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(String.format("Id not found %d", id));
        }
    }

    public void delete(Long id) {
        try {
            clientRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(String.format("Id not found %d", id));
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Integrity Violation");
        }
    }
}
