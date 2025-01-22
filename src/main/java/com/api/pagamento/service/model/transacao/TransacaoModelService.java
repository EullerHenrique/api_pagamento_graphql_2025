package com.api.pagamento.service.model.transacao;

import com.api.pagamento.domain.exception.http.NotFoundException;
import com.api.pagamento.domain.model.transacao.Transacao;
import com.api.pagamento.domain.repository.transacao.TransacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

import static com.api.pagamento.domain.constant.http.message.error.ErrorConstants.ERRO_404_NENHUMA_TRANSACAO_ENCONTRADA;
import static com.api.pagamento.domain.constant.http.message.error.ErrorConstants.ERRO_404_TRANSACAO_NAO_ENCONTRADA;


/**
 * Serviço responsável por retornar model(s) (se existir) ou  lançar exceção (se não existir)
 *
 * @author Euller Henrique
 */
@Component()
@RequiredArgsConstructor
public class TransacaoModelService {

    private final TransacaoRepository transacaoRepository;

    /**
     * Busca uma transação
     *
     * @param id
     * 		Id da transação
     * @return Transacao
     *     Model com os dados da transação
     * @author Euller Henrique
     */
    public Transacao buscarTransacao(Long id) {
        return transacaoRepository.findById(id).orElseThrow(() -> new NotFoundException(ERRO_404_TRANSACAO_NAO_ENCONTRADA));
    }

    /**
     * Lista as transacoes
     *
     * @return List<Transacao>
     *     Lista de models com os dados das transações
     * @author Euller Henrique
     */
    public List<Transacao> listarTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        if (transacoes.isEmpty()) {
            throw new NotFoundException(ERRO_404_NENHUMA_TRANSACAO_ENCONTRADA);
        }
        return transacoes;
    }

    /**
     * Salva uma transação
     *
     * @param transacao
     *         Model com os dados da transação
     * @return Long
     *       Id da transação salva
     * @author Euller Henrique
     */
    public Long salvarTransacao(Transacao transacao) {
        return transacaoRepository.save(transacao).getId();
    }

}
