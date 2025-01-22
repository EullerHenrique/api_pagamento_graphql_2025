package com.api.pagamento.domain.repository.transacao.descricao;

import com.api.pagamento.domain.model.transacao.descricao.DescricaoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório responsável por conectar a entidade da descrição da transacao a tabela descricao_transacao
 *
 * @author Euller Henrique
 */
public interface DescricaoTransacaoRepository extends JpaRepository<DescricaoTransacao, Long> { }
