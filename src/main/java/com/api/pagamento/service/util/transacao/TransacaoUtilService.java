package com.api.pagamento.service.util.transacao;

import com.api.pagamento.domain.enumeration.transacao.descricao.StatusTransacaoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * Serviço responsável por armazenar métodos utilitários
 *
 * @author Euller Henrique
 */
@Component()
@RequiredArgsConstructor
public class TransacaoUtilService {

	private static final Random RANDOM = new Random();

	/**
	 * Obtêm o nsu de forma randômica
	 *
	 * @return String
	 * 		Número de identificação da transação
	 * @author Euller Henrique
	 *
	 */
	public String obterNsu() {
		return String.valueOf(RANDOM.nextInt(100000000, 999999999));
	}

	/**
	 * Obtêm o código de autorização de forma randômica
	 *
	 * @return String
	 * 		Código de autorização da transação
	 * @author Euller Henrique
	 */
	public String obterCodigoAutorizacao() {
		return String.valueOf(RANDOM.nextInt(100000000, 999999999));
	}

	/**
	 * Obtêm o status da transação ao pagar de forma randômica (AUTORIZADO ou NEGADO)
	 *
	 * @return StatusTransacaoEnum
	 * 		Status da transação ao pagar
	 * @see StatusTransacaoEnum
	 * @author Euller Henrique
	 */
	public StatusTransacaoEnum obterStatusAoPagar() {
		return StatusTransacaoEnum.values()[RANDOM.nextInt(2)];
	}

	/**
	 * Obtêm o status da transação ao estornar
	 *
	 * @return StatusTransacaoEnum
	 * 		Status da transação ao estornar
	 * @see StatusTransacaoEnum
	 * @author Euller Henrique
	 */
	public StatusTransacaoEnum obterStatusAoEstornar() {
		return StatusTransacaoEnum.CANCELADO;
	}

}
