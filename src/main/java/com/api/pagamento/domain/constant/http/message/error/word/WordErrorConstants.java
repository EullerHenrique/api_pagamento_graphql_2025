package com.api.pagamento.domain.constant.http.message.error.word;

import com.api.pagamento.domain.constant.divider.DividerConstants;
import com.api.pagamento.domain.constant.formatter.FormatterConstants;
import lombok.NoArgsConstructor;

/**
 * Constantes responsáveis por armazenar as palavras para mensagens de erro
 *
 * @author Euller Henrique
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class WordErrorConstants {

	public static final String DEVE_SER = "deve ser";
	public static final String DEVE_TER = "deve ter";
	public static final String MAIOR_QUE = "maior que";

	public static final String EH_OBRIGATORIO = "é obrigatório!";
	public static final String E = "e";
	public static final String ENTRE = "entre";
	public static final String CARACTERES = "caracteres!";

	public static final String O_CAMPO_XXX = "O campo" + DividerConstants.ESPACO + FormatterConstants.FORMATTER_STRING;
	public static final String NO_FORMATO_YYY = "no formato" + DividerConstants.ESPACO + FormatterConstants.FORMATTER_STRING;
	public static final String DO_TIPO_YYY = "do tipo" + DividerConstants.ESPACO + FormatterConstants.FORMATTER_STRING;
	public static final String UM_DOS_VALORES_YYY = "um dos valores:" + DividerConstants.ESPACO + FormatterConstants.FORMATTER_STRING;
	public static final String DEVE_SER_MAIOR_QUE = DEVE_SER + DividerConstants.ESPACO + MAIOR_QUE;

}
