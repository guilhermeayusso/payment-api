package br.com.fiap.paymentapi.service;

import br.com.fiap.paymentapi.entity.Cartao;
import br.com.fiap.paymentapi.entity.Pagamento;
import br.com.fiap.paymentapi.repository.CartaoRepository;
import br.com.fiap.paymentapi.repository.ClienteRepository;
import br.com.fiap.paymentapi.repository.PagamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PagamentoService {

    private final CartaoRepository cartaoRepository;
    private final PagamentoRepository pagamentoRepository;

    public Pagamento create(Pagamento pagamento){

        Cartao cartao = cartaoRepository.findByNumero(pagamento.getNumero()).orElseThrow(
                () -> new RuntimeException("Cartão inexistente")
        );

        if(pagamento.getValor().compareTo(cartao.getLimite())> 0){
            throw new RuntimeException("Valor da compra não pode ser maior que o Limite");
        }else{
            cartao.setLimite(cartao.getLimite().subtract(pagamento.getValor()));
            cartaoRepository.save(cartao);
           return pagamentoRepository.save(pagamento);
        }

    }
}
