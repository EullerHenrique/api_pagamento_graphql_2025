package com.api.pagamento.domain.dto.builder.request.transacao.forma_pagamento;

import com.api.pagamento.domain.dto.request.transacao.forma_pagamento.FormaPagamentoTransacaoRequestDto;
import com.api.pagamento.domain.enumeration.transacao.forma_pagamento.TipoPagamentoTransacaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * Classe responsável por definir valores padrões para a request da forma de pagamento da transacao
 *
 * @author Euller Henrique
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoTransacaoRequestDtoBuilder {

    @Builder.Default
    private TipoPagamentoTransacaoEnum tipo = TipoPagamentoTransacaoEnum.AVISTA;

    @Builder.Default
    private Integer parcelas = 1;

    /**
     * Obtém um objeto FormaPagamentoTransacaoRequestDto com valores padrões
     *
     * @return FormaPagamentoTransacaoRequestDto
     *      Objeto com valores padrões
     * @author Euller Henrique
     */
    public FormaPagamentoTransacaoRequestDto obterFormaPagamentoTransacaoRequestDto() {
        return new FormaPagamentoTransacaoRequestDto(tipo, parcelas);
    }

}
