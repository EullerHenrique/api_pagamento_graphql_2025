package com.api.pagamento.domain.constant.http.code;

import lombok.NoArgsConstructor;

/**
 * Constantes responsáveis por armazenar os códigos http
 *
 * @author Euller Henrique
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CodeHttpConstants {
	public static final String OK = "200";
	public static final String NOT_FOUND = "404";
	public static final String BAD_REQUEST = "400";
	public static final String INTERNAL_SERVER_ERROR = "500";
}
