package com.api.pagamento.domain.dto.response.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto responsável por armazenar a estrutura de uma mensagem de erro
 *
 * @author Euller Henrique
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageErrorResponseDto {
	private int status;
	private String error;
	private String message;
}


