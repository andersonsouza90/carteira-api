package br.com.carteira.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.carteira.modelo.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoDto {
	
	private Long id;
	private String ticker;
	private BigDecimal preco;
	private int quantidade;
	private TipoTransacao tipo;
	private BigDecimal imposto;
//	@JsonAlias("usuario_id")
//	private Long usuarioId;
	
	
}
