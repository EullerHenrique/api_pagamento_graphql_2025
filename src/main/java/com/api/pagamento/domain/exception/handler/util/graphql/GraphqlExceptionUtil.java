package com.api.pagamento.domain.exception.handler.util.graphql;

import com.api.pagamento.domain.exception.http.BadRequestException;
import com.api.pagamento.domain.exception.http.ForbiddenException;
import com.api.pagamento.domain.exception.http.NotFoundException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por armaenar métodos utilitários para exceções
 *
 * @author Euller Henrique
 */
@Component
public class GraphqlExceptionUtil {

	public GraphQLError obterMessagerErrorResponse(Throwable ex, DataFetchingEnvironment env) {

		ErrorType errorType;
		if (ex instanceof NotFoundException) {
			errorType = ErrorType.NOT_FOUND;
		}else if(ex instanceof BadRequestException) {
			errorType = ErrorType.BAD_REQUEST;
		}else if(ex instanceof ForbiddenException) {
			errorType = ErrorType.FORBIDDEN;
		}else {
			errorType = ErrorType.INTERNAL_ERROR;
		}

		return GraphqlErrorBuilder.newError()
				.errorType(errorType)
				.message(ex.getMessage())
				.path(env.getExecutionStepInfo().getPath())
				.location(env.getField().getSourceLocation())
				.build();
	}
}
