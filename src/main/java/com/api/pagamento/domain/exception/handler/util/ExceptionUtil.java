package com.api.pagamento.domain.exception.handler.util;

import com.api.pagamento.domain.dto.response.error.MessageErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por armaenar métodos utilitários para exceções
 *
 * @author Euller Henrique
 */
@Component
public class ExceptionUtil {

	/**
	 * Cria um MessageErrorResponseDto com o status, erro e mensagem de erro e retorna um ResponseEntity com ele
	 *
	 * @param status
	 * 		Status do erro
	 * @param error
	 * 		Mensagem de erro
	 * @param message
	 * 		Mensagem de erro
	 * @return ResponseEntity<Object>
	 *     Retorna um objeto ResponseEntity com o status, erro e mensagem de erro
	 * @author Euller Henrique
	 */
	public ResponseEntity<Object> obterMessagerErrorResponse(int status, String error, String message) {
		MessageErrorResponseDto messageErrorResponseDto = MessageErrorResponseDto.builder().status(status).error(error).message(message)
				.build();
		return ResponseEntity.status(status).body(messageErrorResponseDto);
	}
}
