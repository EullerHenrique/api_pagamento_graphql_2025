package com.api.pagamento.domain.enumeration.transacao.descricao;

import lombok.NoArgsConstructor;

/**
 * Enumeração responsável por armazenar os status possíveis de uma transação
 *
 * @author Euller Henrique
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public enum StatusTransacaoEnum {
   AUTORIZADO, NEGADO, CANCELADO
}
