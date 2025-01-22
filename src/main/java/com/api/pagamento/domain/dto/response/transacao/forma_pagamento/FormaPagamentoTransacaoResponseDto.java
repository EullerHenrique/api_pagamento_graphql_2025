package com.api.pagamento.domain.dto.response.transacao.forma_pagamento;

import com.api.pagamento.domain.enumeration.transacao.forma_pagamento.TipoPagamentoTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto responsável por armazenar os dados de resposta da forma de pagamento de uma transação
 *
 * @author Euller Henrique
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoTransacaoResponseDto {

    private TipoPagamentoTransacaoEnum tipo;
    private String parcelas;

}
