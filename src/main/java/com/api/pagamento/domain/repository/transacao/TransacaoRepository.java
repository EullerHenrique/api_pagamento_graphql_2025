package com.api.pagamento.domain.repository.transacao;

import com.api.pagamento.domain.model.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório responsável por conectar a entidade da transação a tabela transacao
 *
 * @author Euller Henrique
 */
public interface TransacaoRepository extends JpaRepository<Transacao, Long> { }
