package com.api.pagamento.domain.dto.builder.request.transacao;

import com.api.pagamento.domain.dto.builder.request.transacao.descricao.DescricaoTransacaoRequestDtoBuilder;
import com.api.pagamento.domain.dto.builder.request.transacao.forma_pagamento.FormaPagamentoTransacaoRequestDtoBuilder;
import com.api.pagamento.domain.dto.request.transacao.TransacaoRequestDto;
import com.api.pagamento.domain.dto.request.transacao.descricao.DescricaoTransacaoRequestDto;
import com.api.pagamento.domain.dto.request.transacao.forma_pagamento.FormaPagamentoTransacaoRequestDto;
import lombok.Builder;

/**
 * Classe responsável por definir valores padrões para a request da transação
 *
 * @author Euller Henrique
 */
@Builder
public class TransacaoRequestDtoBuilder {

    @Builder.Default()
    private String cartao = "4444********1234";

    @Builder.Default()
    private DescricaoTransacaoRequestDto descricao = new DescricaoTransacaoRequestDtoBuilder().obterDescricaoTransacaoRequestDto();

    @Builder.Default()
    private FormaPagamentoTransacaoRequestDto formaPagamento = new FormaPagamentoTransacaoRequestDtoBuilder().obterFormaPagamentoTransacaoRequestDto();

    /**
     * Obtém um objeto TransacaoRequestDto com valores padrões
     *
     * @return TransacaoRequestDto
     *       Objeto com valores padrões
     * @author Euller Henrique
     */
    public TransacaoRequestDto obterTransacaoRequestDto() {
        return new TransacaoRequestDto(cartao, descricao, formaPagamento);
    }

}
