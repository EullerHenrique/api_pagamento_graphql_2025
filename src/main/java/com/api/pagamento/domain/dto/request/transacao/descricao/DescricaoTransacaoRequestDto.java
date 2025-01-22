package com.api.pagamento.domain.dto.request.transacao.descricao;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.api.pagamento.domain.constant.divider.DividerConstants.ESPACO;
import static com.api.pagamento.domain.constant.http.message.error.word.WordErrorConstants.*;
import static com.api.pagamento.domain.constant.pattern.PatternConstants.PATTERN_DATA_HORA_PT_BR;

/**
 * Dto responsável por armazenar os dados de requisição da descrição de uma transação
 *
 * @author Euller Henrique
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DescricaoTransacaoRequestDto {

	@NotNull(message = EH_OBRIGATORIO)
	@Min(value = 1, message = DEVE_SER_MAIOR_QUE + 0)
	private BigDecimal valor;

	@NotNull(message = EH_OBRIGATORIO)
	@JsonFormat(pattern = PATTERN_DATA_HORA_PT_BR)
	private LocalDateTime dataHora;

	@NotBlank(message = EH_OBRIGATORIO)
	@Size(min = 1, max = 100, message = DEVE_TER + ESPACO + ENTRE + ESPACO + 1 + ESPACO + E + ESPACO + 100 + ESPACO + CARACTERES)
	private String estabelecimento;

}
