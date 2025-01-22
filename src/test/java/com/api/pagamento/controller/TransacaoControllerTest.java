package com.api.pagamento.controller;

import com.api.pagamento.domain.convert.gson.serializer.LocalDateTimePtbrJsonSerializer;
import com.api.pagamento.controller.transacao.TransacaoController;
import com.api.pagamento.domain.dto.builder.request.transacao.TransacaoRequestDtoBuilder;
import com.api.pagamento.domain.dto.builder.response.transacao.TransacaoResponseDtoBuilder;
import com.api.pagamento.domain.dto.request.transacao.TransacaoRequestDto;
import com.api.pagamento.domain.dto.response.transacao.TransacaoResponseDto;
import com.api.pagamento.domain.enumeration.transacao.descricao.StatusTransacaoEnum;
import com.api.pagamento.domain.exception.handler.http.HttpExceptionHandler;
import com.api.pagamento.domain.exception.http.NotFoundException;
import com.api.pagamento.domain.exception.handler.util.ExceptionUtil;
import com.api.pagamento.service.dto.transacao.TransacaoDtoService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.api.pagamento.domain.constant.pattern.PatternConstants.FORMATTER_DATA_HORA_PT_BR;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de teste responsável por realizar os testes unitários no controller da transação
 *
 * @author Euller Henrique
 */
@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

	private final Gson GSON = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimePtbrJsonSerializer()).create();
	private TransacaoRequestDto transacaoRequestDto;
	private TransacaoResponseDto transacaoResponseDto;
	private MockMvc mockMvc;

	@Mock
	private TransacaoDtoService transacaoDtoService;

	@InjectMocks
	private TransacaoController transacaoController;

	/**
	 * Método que cria os objetos a cada teste
	 *
	 * @author Euller Henrique
	 */
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).setControllerAdvice(new HttpExceptionHandler(new ExceptionUtil())).build();
		transacaoRequestDto = TransacaoRequestDtoBuilder.builder().build().obterTransacaoRequestDto();
		transacaoResponseDto = TransacaoResponseDtoBuilder.builder().build().obterTransacaoResponseDto();
	}

	/**
	 * Teste que testa o retorno do endpoint pagar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoUmaTransacaoEhSolicitadaElaDeveSerRealizada() throws Exception {
		//Dado
		//transacaoRequestDto e transacaoResponseDto já foram instanciados no setUp

		//Quando
		when(transacaoDtoService.pagar(transacaoRequestDto)).thenReturn(transacaoResponseDto);

		// Então
		String jsonResponse = GSON.toJson(transacaoResponseDto);
		mockMvc.perform(post("/transacao/v1/pagar").contentType(MediaType.APPLICATION_JSON).content(jsonResponse))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(transacaoResponseDto.getId())))
				.andExpect(jsonPath("$.cartao", is(transacaoResponseDto.getCartao())))
				.andExpect(jsonPath("$.descricao.valor", is(transacaoResponseDto.getDescricao().getValor())))
				.andExpect(jsonPath("$.descricao.dataHora", is(transacaoResponseDto.getDescricao().getDataHora().format(FORMATTER_DATA_HORA_PT_BR))))
				.andExpect(jsonPath("$.descricao.estabelecimento", is(transacaoResponseDto.getDescricao().getEstabelecimento())))
				.andExpect(jsonPath("$.descricao.nsu", is(transacaoResponseDto.getDescricao().getNsu())))
				.andExpect(jsonPath("$.descricao.codigoAutorizacao", is(transacaoResponseDto.getDescricao().getCodigoAutorizacao())))
				.andExpect(jsonPath("$.descricao.status", is(transacaoResponseDto.getDescricao().getStatus().toString())))
				.andExpect(jsonPath("$.formaPagamento.tipo", is(transacaoResponseDto.getFormaPagamento().getTipo().toString())))
				.andExpect(jsonPath("$.formaPagamento.parcelas", is(transacaoResponseDto.getFormaPagamento().getParcelas())));
	}

	/**
	 * Teste que testa uma exceção do endpoint buscar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoUmaTransacaoEhBuscadaPeloIdENaoEhEncontradaUmaExcecaoDeveSerRetornada() throws Exception {
		//Dado
		Long id = 1L;

		//Quando
		when(transacaoDtoService.buscarTransacao(id)).thenThrow(NotFoundException.class);

		// Então
		mockMvc.perform(get("/transacao/v1/buscar/{id}", 1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
	}

	/**
	 * Teste que testa uma exceção do endpoint pagar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoUmPagamentoEhSolicitadoComCamposPreenchidosIncorretamenteUmaExcecaoDeveSerRetornada() throws Exception {
		//Dado
		//transacaoRequestDto e transacaoResponseDto já foram instanciados no setUp

		transacaoResponseDto.setCartao(null);
		transacaoResponseDto.getDescricao().setEstabelecimento(null);

		//When
		//A anotação @Valid do Spring não permite que o objeto seja instanciado com campos nulos

		// Então
		String jsonResponse = GSON.toJson(transacaoResponseDto);
		mockMvc.perform(post("/transacao/v1/pagar").contentType(MediaType.APPLICATION_JSON).content(jsonResponse))
				.andExpect(status().isBadRequest())
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));

	}

	/**
	 * Teste que testa o retorno do endpoint buscar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoUmaTransacaoEhBuscadaPeloIdElaDeveSerRetornada() throws Exception {
		//Dado
		Long id = 1L;
		//transacaoResponseDto já foi instanciado no setUp

		//Quando
		when(transacaoDtoService.buscarTransacao(id)).thenReturn(transacaoResponseDto);

		// Então
		String jsonResponse = GSON.toJson(transacaoResponseDto);
		mockMvc.perform(get("/transacao/v1/buscar/{id}", id).contentType(MediaType.APPLICATION_JSON).content(jsonResponse))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(transacaoResponseDto.getId())))
				.andExpect(jsonPath("$.cartao", is(transacaoResponseDto.getCartao())))
				.andExpect(jsonPath("$.descricao.valor", is(transacaoResponseDto.getDescricao().getValor()))).andExpect(jsonPath("$.descricao.dataHora", is(transacaoResponseDto.getDescricao().getDataHora().format(FORMATTER_DATA_HORA_PT_BR))))
				.andExpect(jsonPath("$.descricao.estabelecimento", is(transacaoResponseDto.getDescricao().getEstabelecimento())))
				.andExpect(jsonPath("$.descricao.nsu", is(transacaoResponseDto.getDescricao().getNsu())))
				.andExpect(jsonPath("$.descricao.codigoAutorizacao", is(transacaoResponseDto.getDescricao().getCodigoAutorizacao())))
				.andExpect(jsonPath("$.descricao.status", is(transacaoResponseDto.getDescricao().getStatus().toString())))
				.andExpect(jsonPath("$.formaPagamento.tipo", is(transacaoResponseDto.getFormaPagamento().getTipo().toString())))
				.andExpect(jsonPath("$.formaPagamento.parcelas", is(transacaoResponseDto.getFormaPagamento().getParcelas())));
	}

	/**
	 * Teste que testa o retorno do endpoint listar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoTransacoesSaoBuscadasElasDevemSerRetornadas() throws Exception {
		//Dado
		//transacaoResponseDto já foi instanciado no setUp

		List<TransacaoResponseDto> transacoesResponseDto = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			transacoesResponseDto.add(transacaoResponseDto);
		}

		//Quando
		when(transacaoDtoService.listarTransacoes()).thenReturn(transacoesResponseDto);

		// Então
		String jsonResponse = GSON.toJson(transacoesResponseDto);
		mockMvc.perform(get("/transacao/v1/listar").contentType(MediaType.APPLICATION_JSON).content(jsonResponse))
				.andExpect(jsonPath("$[*].id", containsInAnyOrder(transacoesResponseDto.stream().map(TransacaoResponseDto::getId).toArray())))
				.andExpect(jsonPath("$[*].cartao", containsInAnyOrder(transacoesResponseDto.stream().map(TransacaoResponseDto::getCartao).toArray())))
				.andExpect(jsonPath("$[*].descricao.valor", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getDescricao().getValor()).toArray())))
				.andExpect(jsonPath("$[*].descricao.dataHora", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getDescricao().getDataHora().format(FORMATTER_DATA_HORA_PT_BR)).toArray())))
				.andExpect(jsonPath("$[*].descricao.estabelecimento", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getDescricao().getEstabelecimento()).toArray())))
				.andExpect(jsonPath("$[*].descricao.nsu", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getDescricao().getNsu()).toArray())))
				.andExpect(jsonPath("$[*].descricao.codigoAutorizacao", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getDescricao().getCodigoAutorizacao()).toArray())))
				.andExpect(jsonPath("$[*].descricao.status", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getDescricao().getStatus().toString()).toArray())))
				.andExpect(jsonPath("$[*].formaPagamento.tipo", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getFormaPagamento().getTipo().toString()).toArray())))
				.andExpect(jsonPath("$[*].formaPagamento.parcelas", containsInAnyOrder(transacoesResponseDto.stream().map(transacaoResponseDto -> transacaoResponseDto.getFormaPagamento().getParcelas()).toArray())));
	}

	/**
	 * Teste que testa o retorno do endpoint estornar
	 *
	 * @author Euller Henrique
	 */
	@Test
	void QuandoUmEstornoEhSolicitadoEleEhRealizado() throws Exception {
		// Dado
		Long id = 1L;
		//transacaoResponseDto já foi instanciado no setUp
		transacaoResponseDto.getDescricao().setStatus(StatusTransacaoEnum.CANCELADO);

		//When
		when(transacaoDtoService.estornar(id)).thenReturn(transacaoResponseDto);

		// Então
		String jsonResponse = GSON.toJson(transacaoResponseDto);
		mockMvc.perform(put("/transacao/v1/estornar/{id}", id).contentType(MediaType.APPLICATION_JSON).content(jsonResponse))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id", is(transacaoResponseDto.getId())))
				.andExpect(jsonPath("$.cartao", is(transacaoResponseDto.getCartao())))
				.andExpect(jsonPath("$.descricao.valor", is(transacaoResponseDto.getDescricao().getValor()))).andExpect(jsonPath("$.descricao.dataHora", is(transacaoResponseDto.getDescricao().getDataHora().format(FORMATTER_DATA_HORA_PT_BR))))
				.andExpect(jsonPath("$.descricao.estabelecimento", is(transacaoResponseDto.getDescricao().getEstabelecimento())))
				.andExpect(jsonPath("$.descricao.nsu", is(transacaoResponseDto.getDescricao().getNsu())))
				.andExpect(jsonPath("$.descricao.codigoAutorizacao", is(transacaoResponseDto.getDescricao().getCodigoAutorizacao())))
				.andExpect(jsonPath("$.descricao.status", is(StatusTransacaoEnum.CANCELADO.toString())))
				.andExpect(jsonPath("$.formaPagamento.tipo", is(transacaoResponseDto.getFormaPagamento().getTipo().toString())))
				.andExpect(jsonPath("$.formaPagamento.parcelas", is(transacaoResponseDto.getFormaPagamento().getParcelas())));

	}

}

