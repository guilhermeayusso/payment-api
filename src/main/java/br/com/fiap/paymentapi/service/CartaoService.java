package br.com.fiap.paymentapi.service;

import br.com.fiap.paymentapi.entity.Cartao;
import br.com.fiap.paymentapi.exception.CpfNotFoundException;
import br.com.fiap.paymentapi.exception.CpfUniqueViolationException;
import br.com.fiap.paymentapi.repository.CartaoRepository;
import br.com.fiap.paymentapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ClienteRepository clienteRepository;

    public Cartao create(Cartao cartao) {

        if(!clienteRepository.existsByCpf(cartao.getCpf())){
            throw new CpfNotFoundException(String.format("CPF '%s' não existe no sistema", cartao.getCpf()));
        }

        try {
            return cartaoRepository.save(cartao);
        } catch (DataIntegrityViolationException e) {
            throw new CpfUniqueViolationException(String.format("CPF '%s' já tem cartão cadastrado", cartao.getCpf()));
        }

    }
}
