package com.api.pagamento.domain.converter;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Conversor responsável por converter um objeto de um tipo para outro
 *
 * @author Euller Henrique
 */
@Component
@RequiredArgsConstructor
public class Converter {
	private final ModelMapper modelMapper;

	/**
	 * Converte um objeto de um tipo para outro
	 *
	 * @author Euller Henrique
	 * @param origem
	 * 		Objeto de origem
	 * @param destino
	 * 		Tipo de destino
	 * @param <T>
	 * 		Tipo de destino
	 * @return T
	 */
	public <T> T originToDestiny(Object origem, Class<T> destino) {
		return modelMapper.map(origem, destino);
	}

	/**
	 * Converte uma lista de objetos de um tipo para outro
	 *
	 * @author Euller Henrique
	 * @param origem
	 * 		Lista de objetos de origem
	 * @param destino
	 * 		Tipo de destino
	 * @param <T>
	 * 		Tipo de destino
	 * @return List<T>
	 */
	public <T> List<T> originToDestiny(List<?> origem, Class<T> destino) {
		return origem.stream().map(o -> modelMapper.map(o, destino)).toList();
	}

}
