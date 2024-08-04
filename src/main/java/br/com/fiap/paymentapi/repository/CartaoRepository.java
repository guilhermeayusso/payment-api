package br.com.fiap.paymentapi.repository;

import br.com.fiap.paymentapi.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {


    Optional<Cartao> findByNumero(String numero);
}