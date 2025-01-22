package com.api.pagamento.domain.exception.handler.http;

import com.api.pagamento.domain.exception.http.BadRequestException;
import com.api.pagamento.domain.exception.http.InternalServerErrorException;
import com.api.pagamento.domain.exception.http.NotFoundException;
import com.api.pagamento.domain.exception.handler.util.ExceptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.api.pagamento.domain.constant.divider.DividerConstants.DOIS_PONTOS;

/**
 * Classe responsável por capturar exceções de erros HTTP
 *
 * @author Euller Henrique
 */
@ControllerAdvice
@RequiredArgsConstructor
public class HttpExceptionHandler {
	private final ExceptionUtil httpResponseUtilService;

	/**
	 * Captura a exceção de dados não encontrados
	 *
	 * @param ex
	 * 		Exceção NotFoundException
	 * @return ResponseEntity
	 *     Retorna um objeto ResponseEntity com o status 404 e a mensagem de erro
	 * @author Euller Henrique
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		return httpResponseUtilService.obterMessagerErrorResponse(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				ex.getMessage());
	}

	/**
	 * Captura a exceção de requisição inválida
	 *
	 * @param ex
	 * 		Exceção BadRequestException
	 * @return ResponseEntity
	 *     Retorna um objeto ResponseEntity com o status 400 e a mensagem de erro
	 * @author Euller Henrique
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
		return httpResponseUtilService.obterMessagerErrorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessage());
	}

	/**
	 * Captura a exceção de erro interno do servidor
	 *
	 * @param ex
	 * 		Exceção InternalServerErrorException
	 * @return ResponseEntity
	 *     Retorna um objeto ResponseEntity com o status 500 e a mensagem de erro
	 * @author Euller Henrique
	 */
	@ExceptionHandler(InternalServerErrorException.class)
	public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException ex) {
		String[] localizedMessageSplit = ex.getLocalizedMessage().split(DOIS_PONTOS);
		return httpResponseUtilService.obterMessagerErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), localizedMessageSplit[0],
				localizedMessageSplit[1]);
	}

}
