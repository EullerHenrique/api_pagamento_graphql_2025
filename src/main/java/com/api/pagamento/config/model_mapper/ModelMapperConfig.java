package com.api.pagamento.config.model_mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do ModelMapper
 *
 * @author Euller Henrique
 */
@Configuration
public class ModelMapperConfig {

	/**
	 * Cria uma instância do ModelMapper
	 *
	 * @author Euller Henrique
	 * @return ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
