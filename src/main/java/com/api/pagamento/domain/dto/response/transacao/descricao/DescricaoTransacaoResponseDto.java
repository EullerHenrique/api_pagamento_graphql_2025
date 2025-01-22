package com.api.pagamento.domain.dto.response.transacao.descricao;

import com.api.pagamento.domain.enumeration.transacao.descricao.StatusTransacaoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.api.pagamento.domain.constant.pattern.PatternConstants.PATTERN_DATA_HORA_PT_BR;

/**
 * Dto responsável por armazenar os dados de resposta da descrição de uma transação
 *
 * @author Euller Henrique
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DescricaoTransacaoResponseDto {

    private String valor;
    @JsonFormat(pattern = PATTERN_DATA_HORA_PT_BR)
    private LocalDateTime dataHora;
    private String estabelecimento;
    private String nsu;
    private String codigoAutorizacao;
    private StatusTransacaoEnum status;

}
