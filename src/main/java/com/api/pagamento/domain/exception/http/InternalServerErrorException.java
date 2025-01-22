package com.api.pagamento.domain.exception.http;

/**
 * Classe que representa uma exceção de erro interno do servidor
 *
 * @author Euller Henrique
 */
public class InternalServerErrorException extends RuntimeException{

    /**
     * Construtor da exceção
     *
     * @param cause
     *         Causa do erro
     */
    public InternalServerErrorException(Throwable cause) {
        super(cause);
    }
}
