package br.com.carteira.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.carteira.modelo.TipoTransacao;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TransacaoDto {
	
	private Long id;
	private String ticker;
	private BigDecimal preco;
	private int quantidade;
	private TipoTransacao tipo;
	@JsonAlias("usuario_id")
	private Long usuarioId;
	
	
}
