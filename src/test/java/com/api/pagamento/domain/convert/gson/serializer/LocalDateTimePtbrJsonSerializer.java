package com.api.pagamento.domain.convert.gson.serializer;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import static com.api.pagamento.domain.constant.pattern.PatternConstants.FORMATTER_DATA_HORA_PT_BR;

/**
 * Classe responsável por converter na serialização e deserialização de um json o tipo LocalDateTime para o formato pt-br
 *
 * @author Euller Henrique
 */
public class LocalDateTimePtbrJsonSerializer implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

	/**
	 * Método responsável por serializar um objeto LocalDateTime para o formato pt-br
	 *
	 * @param localDateTime
	 * 		Objeto LocalDateTime
	 * @param type
	 * 		Tipo
	 * @param jsonSerializationContext
	 * 		Contexto de serialização
	 * @return JsonElement
	 * 		Elemento json
	 * @author Euller Henrique
	 */
	@Override
	public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(localDateTime.format(FORMATTER_DATA_HORA_PT_BR));
	}

	/**
	 * Método responsável por serializar um objeto LocalDateTime para o formato pt-br
	 *
	 * @param jsonElement
	 * 		Elemento json
	 * @param type
	 * 		Tipo
	 * @param jsonDeserializationContext
	 * 		Contexto de deserialização
	 * @return LocalDateTime
	 * 		Objeto LocalDateTime
	 * @author Euller Henrique
	 */
	@Override
	public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext)
			throws JsonParseException {
		return LocalDateTime.parse(jsonElement.getAsJsonPrimitive().getAsString(), FORMATTER_DATA_HORA_PT_BR);
	}

}