package br.com.carteira.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.carteira.dto.TransacaoDto;
import br.com.carteira.dto.TransacaoFormDto;
import br.com.carteira.modelo.TipoTransacao;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository transacaoRepository;
	
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@InjectMocks
	TransacaoService service;
	
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
		
		TransacaoDto dto = service.cadastrar(formDto);
		
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
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto));
		
		
	}

}
