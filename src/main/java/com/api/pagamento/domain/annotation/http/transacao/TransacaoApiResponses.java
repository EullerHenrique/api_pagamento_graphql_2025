package com.api.pagamento.domain.annotation.http.transacao;

import com.api.pagamento.domain.constant.http.message.error.ErrorConstants;
import com.api.pagamento.domain.constant.http.message.sucess.SucessConstants;
import com.api.pagamento.domain.dto.response.error.MessageErrorResponseDto;
import com.api.pagamento.domain.dto.response.transacao.TransacaoResponseDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.api.pagamento.domain.constant.http.code.CodeHttpConstants.*;
import static com.api.pagamento.domain.constant.http.type.TypeHttpConstants.APPLICATION_JSON;

/**
 * Anotação responsável por armazenar as respostas http do controller de transação para requisições POST e PUT
 *
 * @author Euller Henrique
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses(value = { @ApiResponse(responseCode = OK, description = SucessConstants.SUCESSO_200_OPERACAO_REALIZADA, content = {
		@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = TransacaoResponseDto.class)) }),
		@ApiResponse(responseCode = NOT_FOUND, description = ErrorConstants.ERRO_404_TRANSACAO_NAO_ENCONTRADA, content = {
				@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = MessageErrorResponseDto.class)) }),
		@ApiResponse(responseCode = BAD_REQUEST, description = ErrorConstants.ERRO_400_CAMPOS_PREENCHIDOS_INCORRETAMENTE, content = {
				@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = MessageErrorResponseDto.class)) }),
		@ApiResponse(responseCode = INTERNAL_SERVER_ERROR, description = ErrorConstants.ERRO_500_SERVIDOR_INTERNO, content = {
				@Content(mediaType = APPLICATION_JSON, schema = @Schema(implementation = MessageErrorResponseDto.class)) }) })
public @interface TransacaoApiResponses {}
