package com.api.pagamento.domain.repository.transacao.forma_pagamento;

import com.api.pagamento.domain.model.transacao.forma_pagamento.FormaPagamentoTransacao;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório responsável por conectar a entidade da forma de pagamento da transacao a tabela forma_pagamento_transacao
 *
 * @author Euller Henrique
 */
public interface FormaPagamentoTransacaoRepository extends JpaRepository<FormaPagamentoTransacao, Long> { }
