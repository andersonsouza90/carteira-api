package br.com.carteira.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.carteira.modelo.TipoTransacao;
import br.com.carteira.modelo.Transacao;
import br.com.carteira.modelo.Usuario;

class CalculadoraDeImpostoServiceTest {
	
	private CalculadoraDeImpostoService calculadora;
	
	private Transacao criarTransacao(BigDecimal preco, Integer quantidade, TipoTransacao tipo) {
		Transacao transacao = new Transacao(
					1l,
					"XPT01",
					LocalDate.now(),
					preco,
					quantidade,
					tipo,
					new Usuario(1l, "Usuario Teste", "teste@gmail.com", "123")
				);
		return transacao;
	}
	
	@BeforeEach
	public void inicializar() {
		calculadora = new CalculadoraDeImpostoService();
	}

	@Test
	void transacaoDoTipoCompraNaoDeveriaTerImposto() {
		Transacao transacao = criarTransacao(new BigDecimal("30.00"), 10, TipoTransacao.COMPRA);
		
//		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
		
	}

	@Test
	void transacaoDoTipoVendaComValorMenorQueVinteMilNaoDeveriaTerImposto() {
		
		Transacao transacao = criarTransacao(new BigDecimal("30.00"), 10, TipoTransacao.VENDA);
		
//		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO, imposto);
		
	}
	
	@Test
	void deveriaCalcularImpostoDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() {
		
		Transacao transacao = criarTransacao(new BigDecimal("1000.00"), 30, TipoTransacao.VENDA);
		
//		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(new BigDecimal("4500.00"), imposto);
		
	}

}
