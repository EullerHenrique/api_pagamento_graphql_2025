package com.api.pagamento.domain.exception.http;

/**
 * Classe que representa uma exceção de erro de dados não encontrado
 *
 * @author Euller Henrique
 */
public class NotFoundException extends RuntimeException{

    /**
     * Construtor da exceção
     *
     * @param message
     *         Mensagem de erro
     */
    public NotFoundException(String message) {
        super(message);
    }
}
