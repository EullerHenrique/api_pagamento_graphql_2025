package com.api.pagamento.domain.model.transacao;

import com.api.pagamento.domain.model.transacao.descricao.DescricaoTransacao;
import com.api.pagamento.domain.model.transacao.forma_pagamento.FormaPagamentoTransacao;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

/**
 * Entidade responsável por representar a tabela Transacao
 *
 * @author Euller Henrique
 */
@Data
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "transacao")
public class Transacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "seq_transacao")
    @SequenceGenerator(name = "seq_transacao", sequenceName = "seq_transacao", allocationSize=1)
    private Long id;

    @NotNull
    @Column(length = 16)
    private String cartao;

    @Valid
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade= CascadeType.PERSIST)
    private DescricaoTransacao descricao;

    @Valid
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    private FormaPagamentoTransacao formaPagamento;

}