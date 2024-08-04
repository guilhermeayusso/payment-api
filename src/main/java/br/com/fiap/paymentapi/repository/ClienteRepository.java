package br.com.fiap.paymentapi.repository;

import br.com.fiap.paymentapi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}