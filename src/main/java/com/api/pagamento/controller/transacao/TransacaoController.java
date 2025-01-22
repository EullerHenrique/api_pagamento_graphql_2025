package com.api.pagamento.controller.transacao;

import com.api.pagamento.domain.annotation.http.transacao.TransacaoApiResponses;
import com.api.pagamento.domain.dto.response.transacao.TransacaoResponseDto;
import com.api.pagamento.domain.dto.request.transacao.TransacaoRequestDto;
import com.api.pagamento.domain.exception.http.BadRequestException;
import com.api.pagamento.domain.exception.http.InternalServerErrorException;
import com.api.pagamento.domain.exception.http.NotFoundException;
import com.api.pagamento.service.dto.transacao.TransacaoDtoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.api.pagamento.domain.constant.http.type.TypeHttpConstants.APPLICATION_JSON;

/**
 * Controlador responsável por expor os endpoints relacionados a transação
 *
 * @author Euller Henrique
 */
@RestController
@RequestMapping("/transacao/v1")
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
	@Operation(summary = "Busca uma transação pelo id")
	@TransacaoApiResponses
	@GetMapping(value = "buscar/{id}", produces = APPLICATION_JSON)
	public ResponseEntity<Object> buscarTransacao(@PathVariable Long id) {

		try {
			TransacaoResponseDto transacaoDTO = transacaoDtoService.buscarTransacao(id);
			return ResponseEntity.ok().body(transacaoDTO);
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
	@Operation(summary = "Busca todas as transações")
	@TransacaoApiResponses
	@GetMapping(value = "/listar", produces = APPLICATION_JSON)
	public ResponseEntity<Object> listarTransacoes() {

		try {
			List<TransacaoResponseDto> transacaoDTOS = transacaoDtoService.listarTransacoes();
			return ResponseEntity.ok().body(transacaoDTOS);
		} catch (NotFoundException | BadRequestException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerErrorException(ex);
		}

	}

	/**
	 * Realiza um pagamento
	 *
	 * @param request
	 * 		Objeto que contém os dados da transação
	 * @return ResponseEntity<Object>
	 *     	ResponseEntity com a transação realizada
	 * @author Euller Henrique
	 */
	@Operation(summary = "Realiza um pagamento")
	@TransacaoApiResponses
	@PostMapping(value = "/pagar", produces = APPLICATION_JSON, consumes = APPLICATION_JSON)
	public ResponseEntity<Object> pagar(@RequestBody @Valid TransacaoRequestDto request) {

		try {
			TransacaoResponseDto transacaoDTO = transacaoDtoService.pagar(request);
			return ResponseEntity.ok().body(transacaoDTO);
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
	@Operation(summary = "Realiza um estorno")
	@TransacaoApiResponses
	@PutMapping(value = "/estornar/{id}", produces = APPLICATION_JSON)
	public ResponseEntity<Object> estornar(@PathVariable Long id) {

		try {
			TransacaoResponseDto transacaoDto = transacaoDtoService.estornar(id);
			return ResponseEntity.ok().body(transacaoDto);
		} catch (NotFoundException | BadRequestException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalServerErrorException(ex);
		}

	}

}
