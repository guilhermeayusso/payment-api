package br.com.fiap.paymentapi.service;

import br.com.fiap.paymentapi.entity.Cartao;
import br.com.fiap.paymentapi.entity.Pagamento;
import br.com.fiap.paymentapi.exception.*;
import br.com.fiap.paymentapi.repository.CartaoRepository;
import br.com.fiap.paymentapi.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PagamentoService {

    private final CartaoRepository cartaoRepository;
    private final PagamentoRepository pagamentoRepository;

    public Pagamento create(Pagamento pagamento){

        Cartao cartao = cartaoRepository.findByNumero(pagamento.getNumero()).orElseThrow(
                () -> new CardNotFoundException("Cartão inexistente. ")
        );

        if(pagamento.getValor().compareTo(cartao.getLimite())> 0){
            throw new ExceedLimitException("Valor da compra não pode ser maior que o Limite. ");
        } else if (!pagamento.getDataValidade().equals(cartao.getDataValidade())) {
            throw new InvalidCardExpiryDateException("Data da validade do cartão incorreta. ");
        } else if (!pagamento.getCpf().equals(cartao.getCpf())) {
            throw new CardholderCpfMismatchException("CPF do cartão incorreta. ");
        } else if (!pagamento.getCvv().equals(cartao.getCvv())) {
            throw new InvalidCvvException("CVV do cartão incorreta. ");
        } else{
            cartao.setLimite(cartao.getLimite().subtract(pagamento.getValor()));
            cartaoRepository.save(cartao);
           return pagamentoRepository.save(pagamento);
        }

    }

    public Pagamento getById(UUID id){
        return pagamentoRepository.findById(id).orElseThrow(
                ()-> new PaymentNotFoundException("Pagamento inexistente. ")
        );
    }
}
