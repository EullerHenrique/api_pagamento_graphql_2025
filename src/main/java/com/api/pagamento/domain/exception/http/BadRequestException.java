package com.api.pagamento.domain.exception.http;

/**
 * Classe que representa uma exceção de erro de requisição inválida
 *
 * @author Euller Henrique
 */
public class BadRequestException extends RuntimeException{

    /**
     * Construtor da exceção
     *
     * @param message
     *          Mensagem de erro
     */
    public BadRequestException(String message) {
        super(message);
    }
}
