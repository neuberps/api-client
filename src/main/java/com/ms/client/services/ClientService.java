package com.ms.client.services;

import com.ms.client.dto.ClientDTO;
import com.ms.client.exceptions.ClientCreationException;
import com.ms.client.model.Client;
import com.ms.client.repository.ClientRepository;
import com.ms.client.exceptions.ClientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    public List<ClientDTO> findAll() {
        List<Client> list = repository.findAll();
        if (list.isEmpty()) {
            throw new ClientNotFoundException("No clients found");
        }
        return list.stream().map(ClientDTO::new).toList();
    }

    public ClientDTO create(ClientDTO clientDTO) throws Exception {
        Client entity = new Client(clientDTO);
        repository.save(entity);
        return new ClientDTO(entity);

    }

    public ClientDTO findById(String id) throws Exception {
        return repository.findById(id)
                .map(ClientDTO::new)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with ID: " + id));
    }

    public ClientDTO findByEmail(String email) throws Exception {
        return repository.findByEmail(email)
                .map(ClientDTO::new)
                .orElseThrow(() -> new ClientNotFoundException("Client not found with email: " + email));
    }
    public ClientDTO update(String id, ClientDTO clientDTO) throws Exception {
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            Client entity = optionalClient.get();
            entity.setName(clientDTO.getName());
            entity.setCel(clientDTO.getCel());
            entity.setEmail(clientDTO.getEmail());
            entity.setCpf(clientDTO.getCpf());
            repository.save(entity);
            return new ClientDTO(entity);
        } else {
            throw new ClientNotFoundException("Client not found with ID: " + id);
        }
    }

    public void delete(String id) throws Exception {
        if (!repository.existsById(id)) {
            throw new ClientNotFoundException("Client not found with ID: " + id);
        }
        repository.deleteById(id);
    }
}
