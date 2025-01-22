package com.api.pagamento.domain.enumeration.transacao.forma_pagamento;

import lombok.NoArgsConstructor;

/**
 * Enumeração responsável por armazenar os tipos de pagamento possíveis de uma transação
 *
 * @author Euller Henrique
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum TipoPagamentoTransacaoEnum {
    AVISTA, PARCELADO_LOJA, PARCELADO_EMISSOR
}
