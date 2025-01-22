package com.api.pagamento.domain.exception.handler.http.rest;

import com.api.pagamento.domain.enumeration.transacao.forma_pagamento.TipoPagamentoTransacaoEnum;
import com.api.pagamento.domain.exception.handler.util.ExceptionUtil;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.api.pagamento.domain.constant.divider.DividerConstants.DOIS_PONTOS;
import static com.api.pagamento.domain.constant.divider.DividerConstants.PONTO;
import static com.api.pagamento.domain.constant.http.message.error.ErrorConstants.*;
import static com.api.pagamento.domain.constant.http.message.error.word.WordErrorConstants.O_CAMPO_XXX;
import static com.api.pagamento.domain.constant.pattern.PatternConstants.PATTERN_DATA_HORA_PT_BR;

/**
 * Classe responsável por capturar exceções de erros REST
 *
 * @author Euller Henrique
 */
@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionHandler {

	private final ExceptionUtil httpResponseUtilService;

	/**
	 * Captura a exceção de campo inválido
	 *
	 * @param ex
	 * 		Exceção MethodArgumentNotValidException
	 * @return ResponseEntity
	 *     Retorna um objeto ResponseEntity com o status 400 e a mensagem de erro
	 * @author Euller Henrique
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		String error = ex.toString().split(DOIS_PONTOS)[0];
		String field = ex.getBindingResult().getFieldErrors().get(0).getField();
		String defaultMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

		String message = O_CAMPO_XXX.formatted(field) + defaultMessage;

		return httpResponseUtilService.obterMessagerErrorResponse(HttpStatus.BAD_REQUEST.value(), error, message);
	}


	/**
	 * Captura a exceção de conversão de json inválida
	 * <p>
	 *     1. Se a exceção for do tipo InvalidFormatException, o erro, o campo e o tipo do campo são capturados
	 * </p>
	 * <p>
	 *     1.1 Se o tipo de dado for TipoPagamentoTransacaoEnum, a mensagem de erro é montada com os valores possíveis
	 * </p>
	 * <p>
	 *     1.2 Se o tipo de dado for LocalDateTime, a mensagem de erro é montada com o formato de data e hora
	 * </p>
	 * <p>
	 *     1.3 Se o tipo de dado for diferente dos anteriores, a mensagem de erro padrão é montada
	 * </p>
	 *
	 * @param ex
	 * 		Exceção HttpMessageNotReadableException
	 * @return ResponseEntity
	 *     Retorna um objeto ResponseEntity com o status 400 e a mensagem de erro
	 * @author Euller Henrique
	 */
	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleJsonParserException(HttpMessageNotReadableException ex) {
		String error = ex.toString().split(DOIS_PONTOS)[0];
		String message = ex.getCause().getMessage();

		if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
			error = invalidFormatException.toString().split(DOIS_PONTOS)[0];

			List<JsonMappingException.Reference> path = invalidFormatException.getPath();
			String field = path.stream().map(JsonMappingException.Reference::getFieldName).collect(Collectors.joining(PONTO));
			String typeField = invalidFormatException.getTargetType().getSimpleName();

			if(TipoPagamentoTransacaoEnum.class.getSimpleName().equals(typeField)) {
				message = ERRO_400_O_CAMPO_XXX_DEVE_SER_UM_DOS_VALORES_YYY.formatted(field, Arrays.toString(TipoPagamentoTransacaoEnum.values()));
			} else if(LocalDateTime.class.getSimpleName().equals(typeField)) {
				message = ERRO_400_O_CAMPO_XXX_DEVE_SER_DO_TIPO_YYY_NO_FORMATO.formatted(field, typeField, PATTERN_DATA_HORA_PT_BR);
			} else {
				message = ERRO_400_O_CAMPO_XXX_DEVE_SER_DO_TIPO_YYY.formatted(field, typeField);
			}

		}

		return httpResponseUtilService.obterMessagerErrorResponse(HttpStatus.BAD_REQUEST.value(), error, message);
	}

}


