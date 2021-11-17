package br.com.carteira.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.carteira.dto.TransacaoDto;
import br.com.carteira.dto.TransacaoFormDto;
import br.com.carteira.modelo.TipoTransacao;
import br.com.carteira.modelo.Transacao;
import br.com.carteira.modelo.Usuario;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	CalculadoraDeImpostoService calculadoraDeImpostoService;
	
	@InjectMocks
	TransacaoService service;
	
	private Usuario logado;
	
	@BeforeEach
	public void before() {
		this.logado = new Usuario("Dandy", "dandy", "123");
	}
	
	private TransacaoFormDto criarTransacaoFormDto() {
		TransacaoFormDto formDto = new TransacaoFormDto(
											"XPT01",
											LocalDate.now(),
											new BigDecimal("30.00"),
											10,
											TipoTransacao.COMPRA,
											1l
										);
		return formDto;
	}

	@Test
	void deveriaCadastrarUmaTransacao() {
		
		TransacaoFormDto formDto = criarTransacaoFormDto();
		
		Mockito
			.when(usuarioRepository.getById(formDto.getUsuarioId()))
			.thenReturn(logado);
		
		Transacao transacao = new Transacao(
							formDto.getTicker(), 
							formDto.getData(), 
							formDto.getPreco(), 
							formDto.getQuantidade(), 
							formDto.getTipo(), 
							logado);
		
		Mockito
		.when(modelMapper.map(formDto, Transacao.class))
		.thenReturn(transacao);
		
		Mockito
		.when(modelMapper.map(transacao, TransacaoDto.class))
		.thenReturn(new TransacaoDto(
					null,
					transacao.getTicker(),
					transacao.getPreco(),
					transacao.getQuantidade(),
					transacao.getTipo(),
					BigDecimal.ZERO
				));
		
		TransacaoDto dto = service.cadastrar(formDto, logado);
		
		Mockito.verify(transacaoRepository).save(Mockito.any());
		
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
		
	}

	@Test
	void NaoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() {
		
		TransacaoFormDto formDto = criarTransacaoFormDto();
		
		Mockito
			.when(usuarioRepository.getById(formDto.getUsuarioId()))
			.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto, logado));
		
		
	}

}
