package com.api.pagamento.domain.dto.request.transacao;

import com.api.pagamento.domain.dto.request.transacao.descricao.DescricaoTransacaoRequestDto;
import com.api.pagamento.domain.dto.request.transacao.forma_pagamento.FormaPagamentoTransacaoRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.api.pagamento.domain.constant.http.message.error.word.WordErrorConstants.*;

/**
 * Dto responsável por armazenar os dados de requisição de uma transação
 *
 * @author Euller Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoRequestDto {

	@NotBlank(message = EH_OBRIGATORIO)
	@Size(min = 16, max = 16, message = DEVE_TER + 16 + CARACTERES)
	private String cartao;

	@Valid
	private DescricaoTransacaoRequestDto descricao;

	@Valid
	private FormaPagamentoTransacaoRequestDto formaPagamento;

}
