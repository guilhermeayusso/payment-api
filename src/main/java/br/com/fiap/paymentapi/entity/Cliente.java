package br.com.fiap.paymentapi.entity;

import br.com.fiap.paymentapi.enums.Estado;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "clientes")
@EntityListeners(AuditingEntityListener.class)
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cliente;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "telefone", nullable = false, length = 17)
    private String telefone;

    @Column(name = "rua", nullable = false, length = 100)
    private String rua;

    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 2)
    @Enumerated(EnumType.STRING)
    private Estado estado;

    @Column(name = "cep", nullable = false, length = 10)
    private String cep;

    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
    @CreatedBy
    @Column(name = "criado_por")
    private String criadoPor;
    @LastModifiedBy
    @Column(name = "modificado_por")
    private String modificadoPor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(cpf, cliente.cpf) && Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(telefone, cliente.telefone) && Objects.equals(rua, cliente.rua) && Objects.equals(cidade, cliente.cidade) && estado == cliente.estado && Objects.equals(cep, cliente.cep) && Objects.equals(pais, cliente.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpf, nome, email, telefone, rua, cidade, estado, cep, pais);
    }


}
