package com.api.pagamento.domain.exception.handler.http.graphql;

import com.api.pagamento.domain.exception.handler.util.graphql.GraphqlExceptionUtil;
import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * Classe responsável por capturar exceções de erros GraphQl
 *
 * @author Euller Henrique
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GraphqlExceptionHandler extends DataFetcherExceptionResolverAdapter {

	private final GraphqlExceptionUtil graphqlExceptionUtil;

	@Override
	protected GraphQLError resolveToSingleError(@NonNull Throwable ex, @NonNull DataFetchingEnvironment env) {
		log.error(ex.getMessage(), ex);
		return graphqlExceptionUtil.obterMessagerErrorResponse(ex, env);
	}

}


