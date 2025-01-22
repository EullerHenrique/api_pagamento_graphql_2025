package com.api.pagamento.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do Swagger
 *
 * @author Euller Henrique
 */
@Configuration
public class SwaggerConfig {

	/**
	 * Cria um grupo de documentação para a API de transação
	 *
	 * @return GroupedOpenApi
	 * 		Objeto com as informações do grupo de documentação da API de transação
	 * @author Euller Henrique
	 */
	@Bean
	public GroupedOpenApi criarDocGrupoTransacao() {
		return GroupedOpenApi.builder().group("transacao").packagesToScan("com.api.pagamento.controller.transacao")
				.pathsToMatch("/transacao/**").build();
	}

	/**
	 * Cria as informações da API
	 *
	 * @return OpenAPI
	 * 		Objeto com as informações da API
	 * @author Euller Henrique
	 */
	@Bean
	public OpenAPI criarInfoApi() {
		return new OpenAPI().info(new Info().title("API De Pagamento").description("Data de entrega: 06/08/24").version("2.0.0")
				.contact(new Contact().name("Euller Henrique").url("https://github.com/EullerHenrique").email("eullerhenrique@outlook.com"))
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

}
