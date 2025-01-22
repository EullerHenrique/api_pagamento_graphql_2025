package com.api.pagamento.domain.model.transacao.descricao;

import com.api.pagamento.domain.enumeration.transacao.descricao.StatusTransacaoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade responsável por representar a tabela Descricao
 *
 * @author Euller Henrique
 */
@Data
@Entity
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "descricao_transacao")
public class DescricaoTransacao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "seq_descricao")
    @SequenceGenerator(name = "seq_descricao", sequenceName = "seq_descricao", allocationSize=1)
    private Long id;

    @NotNull
    @Column(precision = 10, scale = 2)
    private BigDecimal valor;

    @NotNull
    private LocalDateTime dataHora;

    @NotNull
    @Column(length = 100)
    private String estabelecimento;

    @NotNull
    @Column(length = 9)
    private String nsu;

    @NotNull
    @Column(length = 9)
    private String codigoAutorizacao;

    @NotNull
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private StatusTransacaoEnum status;

}