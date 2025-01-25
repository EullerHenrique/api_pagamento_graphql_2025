package com.api.pagamento.controller.transacao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import com.api.pagamento.domain.dto.request.transacao.TransacaoRequestDto;
import com.api.pagamento.domain.dto.response.transacao.TransacaoResponseDto;
import com.api.pagamento.domain.exception.http.BadRequestException;
import com.api.pagamento.domain.exception.http.InternalServerErrorException;
import com.api.pagamento.domain.exception.http.NotFoundException;
import com.api.pagamento.service.dto.transacao.TransacaoDtoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 * Controlador responsável por expor os endpoints relacionados a transação
 *
 * @author Euller Henrique
 */
@Controller
@RequiredArgsConstructor
public class TransacaoController {
	private final TransacaoDtoService transacaoDtoService;

	/**
	 * Busca uma transação pelo id
	 *
	 * @param id
	 * 		Id da transação
	 * @return ResponseEntity<Object>
	 *     ResponseEntity com a transação encontrada
	 * @author Euller Henrique
	 */
	@QueryMapping
	public TransacaoResponseDto buscarTransacaoPeloId(@Argument  Long id) {
		try {
			return transacaoDtoService.buscarTransacao(id);
		} catch (NotFoundException | BadRequestException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerErrorException(ex);
		}
	}

	/**
	 * Busca todas as transações
	 *
	 * @return ResponseEntity<Object>
	 *     ResponseEntity com todas as transações encontradas
	 * @author Euller Henrique
	 */
	@QueryMapping
	public List<TransacaoResponseDto> listarTransacoes() {
		try {
			return transacaoDtoService.listarTransacoes();
		} catch (NotFoundException | BadRequestException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerErrorException(ex);
		}
	}


	/**
	 * Realiza um pagamento
	 *
	 * @param transacao
	 * 		Objeto que contém os dados da transação
	 * @return ResponseEntity<Object>
	 *     	ResponseEntity com a transação realizada
	 * @author Euller Henrique
	 */
	@MutationMapping
	public TransacaoResponseDto pagar(@Argument TransacaoRequestDto transacao) {
		try {
			return transacaoDtoService.pagar(transacao);
		} catch (NotFoundException | BadRequestException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerErrorException(ex);
		}
	}

	/**
	 * Realiza um estorno
	 *
	 * @param id
	 * 		Id da transação
	 * @return ResponseEntity<Object>
	 *     ResponseEntity com a transação estornada
	 * @author Euller Henrique
	 */
	@MutationMapping
	public TransacaoResponseDto estornar(@Argument Long id) {
		try {
			return transacaoDtoService.estornar(id);
		} catch (NotFoundException | BadRequestException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerErrorException(ex);
		}
	}

}
