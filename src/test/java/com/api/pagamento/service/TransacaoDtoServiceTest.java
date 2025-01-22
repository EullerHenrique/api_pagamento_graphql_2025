package com.api.pagamento.service;

import com.api.pagamento.domain.dto.builder.request.transacao.TransacaoRequestDtoBuilder;
import com.api.pagamento.domain.dto.builder.response.transacao.TransacaoResponseDtoBuilder;
import com.api.pagamento.domain.converter.Converter;
import com.api.pagamento.domain.dto.request.transacao.TransacaoRequestDto;
import com.api.pagamento.domain.dto.response.transacao.TransacaoResponseDto;
import com.api.pagamento.domain.enumeration.transacao.descricao.StatusTransacaoEnum;
import com.api.pagamento.domain.exception.http.BadRequestException;
import com.api.pagamento.domain.exception.http.NotFoundException;
import com.api.pagamento.domain.model.transacao.Transacao;
import com.api.pagamento.service.dto.transacao.TransacaoDtoService;
import com.api.pagamento.service.model.transacao.TransacaoModelService;
import com.api.pagamento.service.util.transacao.TransacaoUtilService;
import com.api.pagamento.service.valid.transacao.TransacaoValidService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

/**
 * Classe de teste responsável por realizar os testes unitários no serviço da transação
 *
 * @author Euller Henrique
 */
@ExtendWith(MockitoExtension.class)
class TransacaoDtoServiceTest {

	private final Converter CONVERTER = new Converter(new ModelMapper());
	private TransacaoRequestDto transacaoRequestDto;
	private TransacaoResponseDto transacaoResponseDto;

    @InjectMocks
    private TransacaoDtoService transacaoDtoService;
	@Mock
	private TransacaoValidService transacaoValidatorService;
	@Mock
	private TransacaoUtilService transacaoUtilService;
	@Mock
	private Converter converter;
	@Mock
	private TransacaoModelService transacaoModelService;

	/**
	 * Método que cria os objetos a cada teste
	 *
	 * @author Euller Henrique
	 */
	@BeforeEach
	void setUp() {
		transacaoRequestDto = TransacaoRequestDtoBuilder.builder().build().obterTransacaoRequestDto();
		transacaoResponseDto = TransacaoResponseDtoBuilder.builder().build().obterTransacaoResponseDto();
	}

	/**
	 * Teste que testa o retorno do serviço pagar
	 *
	 * @author Euller Henrique
	 */
    @Test
    void QuandoUmaTransacaoEhSolicitadaElaDeveSerRealizada(){

        // Dado
		//transacaoRequestDto e transacaoResponseDto já foram instanciados no setUp
		Transacao transacaoModelNaoSalva = CONVERTER.originToDestiny(transacaoResponseDto, Transacao.class);

		//Quando
		when(converter.originToDestiny(transacaoRequestDto, TransacaoResponseDto.class)).thenReturn(transacaoResponseDto);
		when(transacaoUtilService.obterNsu()).thenReturn(transacaoResponseDto.getDescricao().getNsu());
		when(transacaoUtilService.obterCodigoAutorizacao()).thenReturn(transacaoResponseDto.getDescricao().getCodigoAutorizacao());
		when(transacaoUtilService.obterStatusAoPagar()).thenReturn(transacaoResponseDto.getDescricao().getStatus());
		when(converter.originToDestiny(transacaoResponseDto, Transacao.class)).thenReturn(transacaoModelNaoSalva);
		when(transacaoModelService.salvarTransacao(transacaoModelNaoSalva)).thenReturn(1L);

        // Então
		TransacaoResponseDto transacaoResponseDtoRetornada  = transacaoDtoService.pagar(transacaoRequestDto);
		assertThat(transacaoResponseDtoRetornada.getId(), is(equalTo(transacaoResponseDto.getId())));
		assertThat(transacaoResponseDtoRetornada.getCartao(), is(equalTo(transacaoResponseDto.getCartao())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getValor(), is(equalTo(transacaoResponseDto.getDescricao().getValor())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getDataHora(), is(equalTo(transacaoResponseDto.getDescricao().getDataHora())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getEstabelecimento(), is(equalTo(transacaoResponseDto.getDescricao().getEstabelecimento())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getNsu(), is(equalTo(transacaoResponseDto.getDescricao().getNsu())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getNsu(), is(notNullValue()));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getCodigoAutorizacao(), is(equalTo(transacaoResponseDto.getDescricao().getCodigoAutorizacao())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getCodigoAutorizacao(), is(notNullValue()));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getStatus(), is(equalTo(transacaoResponseDto.getDescricao().getStatus())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getStatus(), is(StatusTransacaoEnum.AUTORIZADO));
		assertThat(transacaoResponseDtoRetornada.getFormaPagamento().getTipo(), is(equalTo(transacaoResponseDto.getFormaPagamento().getTipo())));
		assertThat(transacaoResponseDtoRetornada.getFormaPagamento().getParcelas(), is(equalTo(transacaoResponseDto.getFormaPagamento().getParcelas())));
	}

	/**
	 * Teste que testa uma exceção do serviço buscar
	 *
	 * @author Euller Henrique
	 */
	@Test
    void QuandoUmaTransacaoEhBuscadaPeloIdENaoEhEncontradaUmaExcecaoDeveSerRetornada()  {
        // Dado
        Long id = 1L;

        //Quando
		when(transacaoModelService.buscarTransacao(id)).thenThrow(NotFoundException.class);

        // Então
        assertThrows(NotFoundException.class, () -> transacaoDtoService.buscarTransacao(id));
    }

	/**
	 * Teste que testa uma exceção do serviço pagar
	 *
	 * @author Euller Henrique
	 */
	@Test
    void QuandoUmPagamentoEhSolicitadoComTipoAvistaEMaisDeUmaParcelaUmaExcecaoDeveSerRetornada()  {
		// Dado
		//transacaoRequestDto já foi instanciado no setUp

		//Quando
		doThrow(BadRequestException.class).when(transacaoValidatorService).validarTipoPagamentoAoPagar(transacaoRequestDto);

        // Então
 		assertThrows(BadRequestException.class, () -> transacaoDtoService.pagar(transacaoRequestDto));
    }

	/**
	 * Teste que testa o retorno do serviço buscar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoUmaTransacaoEhBuscadaPeloIdElaDeveSerRetornada(){
		// Dado
		Long id = 1L;
		//transacaoResponseDto já foi instanciado no setUp
		Transacao transacaoModelSalva = CONVERTER.originToDestiny(transacaoResponseDto, Transacao.class);

		//When
		when(transacaoModelService.buscarTransacao(id)).thenReturn(transacaoModelSalva);
		when(converter.originToDestiny(transacaoModelSalva, TransacaoResponseDto.class)).thenReturn(transacaoResponseDto);

		// Então
		TransacaoResponseDto transacaoResponseDtoRetornada  = transacaoDtoService.buscarTransacao(id);
		assertThat(transacaoResponseDtoRetornada.getId(), is(equalTo(transacaoResponseDto.getId())));
		assertThat(transacaoResponseDtoRetornada.getCartao(), is(equalTo(transacaoResponseDto.getCartao())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getValor(), is(equalTo(transacaoResponseDto.getDescricao().getValor())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getDataHora(), is(equalTo(transacaoResponseDto.getDescricao().getDataHora())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getEstabelecimento(), is(equalTo(transacaoResponseDto.getDescricao().getEstabelecimento())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getNsu(), is(equalTo(transacaoResponseDto.getDescricao().getNsu())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getCodigoAutorizacao(), is(equalTo(transacaoResponseDto.getDescricao().getCodigoAutorizacao())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getStatus(), is(equalTo(transacaoResponseDto.getDescricao().getStatus())));
		assertThat(transacaoResponseDtoRetornada.getFormaPagamento().getTipo(), is(equalTo(transacaoResponseDto.getFormaPagamento().getTipo())));
		assertThat(transacaoResponseDtoRetornada.getFormaPagamento().getParcelas(), is(equalTo(transacaoResponseDto.getFormaPagamento().getParcelas())));
	}

	/**
	 * Teste que testa o retorno do serviço listar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoTransacoesSaoBuscadasElasDevemSerRetornadas() {
		//Dado
		//transacaoResponseDto já foi instanciado no setUp
		List<TransacaoResponseDto> transacoesResponseDtosEsperadas = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			transacoesResponseDtosEsperadas.add(transacaoResponseDto);
		}
		List<Transacao> transacoes = CONVERTER.originToDestiny(transacoesResponseDtosEsperadas, Transacao.class);

		//Quando
		when(transacaoModelService.listarTransacoes()).thenReturn(transacoes);
		when(converter.originToDestiny(transacoes, TransacaoResponseDto.class)).thenReturn(transacoesResponseDtosEsperadas);

		//Então
		List<TransacaoResponseDto> transacoesResponseDtosRetornadas = transacaoDtoService.listarTransacoes();
		for (int i = 0; i < transacoes.size(); i++) {
			assertThat(transacoesResponseDtosRetornadas.get(i).getId(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getId())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getCartao(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getCartao())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getDescricao().getValor(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getDescricao().getValor())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getDescricao().getDataHora(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getDescricao().getDataHora())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getDescricao().getEstabelecimento(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getDescricao().getEstabelecimento())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getDescricao().getNsu(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getDescricao().getNsu())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getDescricao().getCodigoAutorizacao(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getDescricao().getCodigoAutorizacao())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getDescricao().getStatus(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getDescricao().getStatus())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getFormaPagamento().getTipo(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getFormaPagamento().getTipo())));
			assertThat(transacoesResponseDtosRetornadas.get(i).getFormaPagamento().getParcelas(), is(equalTo(transacoesResponseDtosEsperadas.get(i).getFormaPagamento().getParcelas())));
		}

	}

	/**
	 * Teste que testa o retorno do serviço estornar
	 *
	 * @author Euller Henrique
	 */
	@Test
    void QuandoUmEstornoEhSolicitadoEleEhRealizado(){
        // Dado
		Long id = 1L;
		//transacaoResponseDto já foi instanciado no setUp
		transacaoResponseDto.getDescricao().setStatus(StatusTransacaoEnum.NEGADO);
		Transacao transacaoModelNaoSalva = CONVERTER.originToDestiny(transacaoResponseDto, Transacao.class);

		// Quando
		when(transacaoModelService.buscarTransacao(id)).thenReturn(transacaoModelNaoSalva);
		when(converter.originToDestiny(transacaoModelNaoSalva, TransacaoResponseDto.class)).thenReturn(transacaoResponseDto);

        // Então
		TransacaoResponseDto transacaoResponseDtoRetornada  = transacaoDtoService.estornar(id);
		assertThat(transacaoResponseDtoRetornada.getId(), is(equalTo(transacaoResponseDto.getId())));
		assertThat(transacaoResponseDtoRetornada.getCartao(), is(equalTo(transacaoResponseDto.getCartao())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getValor(), is(equalTo(transacaoResponseDto.getDescricao().getValor())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getDataHora(), is(equalTo(transacaoResponseDto.getDescricao().getDataHora())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getEstabelecimento(), is(equalTo(transacaoResponseDto.getDescricao().getEstabelecimento())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getNsu(), is(equalTo(transacaoResponseDto.getDescricao().getNsu())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getCodigoAutorizacao(), is(equalTo(transacaoResponseDto.getDescricao().getCodigoAutorizacao())));
		assertThat(transacaoResponseDtoRetornada.getDescricao().getStatus(), is(StatusTransacaoEnum.NEGADO));
		assertThat(transacaoResponseDtoRetornada.getFormaPagamento().getTipo(), is(equalTo(transacaoResponseDto.getFormaPagamento().getTipo())));
		assertThat(transacaoResponseDtoRetornada.getFormaPagamento().getParcelas(), is(equalTo(transacaoResponseDto.getFormaPagamento().getParcelas())));
	}

}


