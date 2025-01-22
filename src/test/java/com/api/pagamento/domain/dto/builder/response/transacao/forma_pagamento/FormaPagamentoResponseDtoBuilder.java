package com.api.pagamento.domain.dto.builder.response.transacao.forma_pagamento;

import com.api.pagamento.domain.dto.response.transacao.forma_pagamento.FormaPagamentoTransacaoResponseDto;
import com.api.pagamento.domain.enumeration.transacao.forma_pagamento.TipoPagamentoTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Classe responsável por definir valores padrões para a response da forma de pagamento da transacao
 *
 * @author Euller Henrique
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoResponseDtoBuilder {

    @Builder.Default
    private TipoPagamentoTransacaoEnum tipo = TipoPagamentoTransacaoEnum.AVISTA;

    @Builder.Default
    private String parcelas = "1";

    public FormaPagamentoTransacaoResponseDto obterFormaPagamentoTransacaoResponseDto() {
        return new FormaPagamentoTransacaoResponseDto(tipo, parcelas);
    }

}
