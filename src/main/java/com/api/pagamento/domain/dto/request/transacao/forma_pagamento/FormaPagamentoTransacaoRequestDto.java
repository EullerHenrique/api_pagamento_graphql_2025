package com.api.pagamento.domain.dto.request.transacao.forma_pagamento;

import com.api.pagamento.domain.enumeration.transacao.forma_pagamento.TipoPagamentoTransacaoEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.api.pagamento.domain.constant.http.message.error.word.WordErrorConstants.DEVE_SER_MAIOR_QUE;
import static com.api.pagamento.domain.constant.http.message.error.word.WordErrorConstants.EH_OBRIGATORIO;

/**
 * Dto responsável por armazenar os dados de requisição da forma de pagamento de uma transação
 *
 * @author Euller Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamentoTransacaoRequestDto {

	@NotNull(message = EH_OBRIGATORIO)
	private TipoPagamentoTransacaoEnum tipo;

	@NotNull(message = EH_OBRIGATORIO)
	@Min(value = 1, message = DEVE_SER_MAIOR_QUE + 0)
	private Integer parcelas;
}
