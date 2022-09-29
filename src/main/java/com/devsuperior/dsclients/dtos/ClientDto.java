package com.devsuperior.dsclients.dtos;

import com.devsuperior.dsclients.entities.Client;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
public class ClientDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String cpf;
    private Double income;
    private Instant birthDate;
    private Integer children;

    public ClientDto() {
    }

    public ClientDto(Long id, String name, String cpf, Double income, Instant birthDate, Integer children) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.income = income;
        this.birthDate = birthDate;
        this.children = children;
    }

    public ClientDto(Client client) {
        BeanUtils.copyProperties(client, this);
    }
}
