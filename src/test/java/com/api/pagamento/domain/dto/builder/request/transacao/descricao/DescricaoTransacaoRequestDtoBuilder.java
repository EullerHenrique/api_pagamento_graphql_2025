package com.api.pagamento.domain.dto.builder.request.transacao.descricao;

import com.api.pagamento.domain.dto.request.transacao.descricao.DescricaoTransacaoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Classe responsável por definir valores padrões para a request da descrição da transação
 *
 * @author Euller Henrique
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DescricaoTransacaoRequestDtoBuilder {

    @Builder.Default
    private BigDecimal valor = BigDecimal.valueOf(500.55);

    @Builder.Default
    private LocalDateTime dataHora = LocalDateTime.of(2021, 10, 1, 10, 10, 10);

    @Builder.Default
    private String estabelecimento = "PetShop Mundo cão";

    /**
     * Obtém um objeto DescricaoTransacaoRequestDto com valores padrões
     *
     * @return DescricaoTransacaoRequestDto
     *      Objeto com valores padrões
     * @author Euller Henrique
     */
    public DescricaoTransacaoRequestDto obterDescricaoTransacaoRequestDto() {
        return new DescricaoTransacaoRequestDto(valor, dataHora, estabelecimento);
    }

}
