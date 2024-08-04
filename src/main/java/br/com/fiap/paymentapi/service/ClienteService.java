package br.com.fiap.paymentapi.service;

import br.com.fiap.paymentapi.entity.Cliente;
import br.com.fiap.paymentapi.exception.CpfUniqueViolationException;
import br.com.fiap.paymentapi.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf()));
        }
    }
}
