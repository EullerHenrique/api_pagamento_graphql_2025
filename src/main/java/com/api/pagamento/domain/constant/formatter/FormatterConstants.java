package com.api.pagamento.domain.constant.formatter;

import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

import static com.api.pagamento.domain.constant.pattern.PatternConstants.PATTERN_DATA_HORA_PT_BR;

/**
 * Constantes responsáveis por armazenar as constantes de formatadores
 *
 * @author Euller Henrique
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class FormatterConstants {
	public static final DateTimeFormatter FORMATTER_DATA_HORA_PT_BR = DateTimeFormatter.ofPattern(PATTERN_DATA_HORA_PT_BR);
	public static final String FORMATTER_STRING = "%s";

}
