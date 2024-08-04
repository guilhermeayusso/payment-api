package br.com.fiap.paymentapi.repository;

import br.com.fiap.paymentapi.entity.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PagamentoRepository extends JpaRepository<Pagamento, UUID> {
}