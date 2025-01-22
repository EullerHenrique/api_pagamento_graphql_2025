package com.api.pagamento.service.valid.transacao;

import com.api.pagamento.domain.dto.request.transacao.TransacaoRequestDto;
import com.api.pagamento.domain.enumeration.transacao.descricao.StatusTransacaoEnum;
import com.api.pagamento.domain.enumeration.transacao.forma_pagamento.TipoPagamentoTransacaoEnum;
import com.api.pagamento.domain.exception.http.BadRequestException;
import com.api.pagamento.domain.model.transacao.Transacao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.api.pagamento.domain.constant.http.message.error.ErrorConstants.*;

/**
 * Serviço responsável por armazenar métodos validadores
 *
 * @author Euller Henrique
 */
@Component()
@RequiredArgsConstructor
public class TransacaoValidService {

	/**
	 * Valida o tipo de pagamento ao pagar
	 * <p>
	 * Caso o tipo de pagamento seja à vista, não é permitido mais de uma parcela
	 * </p>
	 *
	 * @param request
	 * 		Dto com os dados de request da transação
	 * @throws BadRequestException
	 * 		Exceção lançada caso o tipo de pagamento seja inválido
	 */
	public void validarTipoPagamentoAoPagar(TransacaoRequestDto request) {
		TipoPagamentoTransacaoEnum tipoPagamento = request.getFormaPagamento().getTipo();
		int parcelas = request.getFormaPagamento().getParcelas();

		if (TipoPagamentoTransacaoEnum.AVISTA.equals(tipoPagamento) && parcelas > 1) {
			throw new BadRequestException(ERROR_400_PAGAMENTO_AVISTA_MAIS_DE_UMA_PARCELA);
		}
	}

	/**
	 * Valida o status da transação ao estornar
	 * <p>
	 * Se a transação já foi estornada, não é permitido estornar novamente
	 * </p>
	 * <p>
	 * Se a transação foi negada, não é permitido estornar
	 * </p>
	 *
	 * @param transacao
	 * 		Model com os dados da transação
	 * @throws BadRequestException
	 * 		Exceção lançada caso o status da transação seja inválido
	 */
	public void validarStatusTransacaoAoEstornar(Transacao transacao) {
		StatusTransacaoEnum status = transacao.getDescricao().getStatus();

		if (StatusTransacaoEnum.CANCELADO.equals(status)) {
			throw new BadRequestException(ERROR_400_TRANSACAO_JA_FOI_ESTORNADA);
		} else if (StatusTransacaoEnum.NEGADO.equals(status)) {
			throw new BadRequestException(ERROR_400_TRANSACAO_NEGADA_NAO_PODE_SER_ESTORNADA);
		}
	}
}
