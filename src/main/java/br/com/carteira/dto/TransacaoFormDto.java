package br.com.carteira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonAlias;

import br.com.carteira.modelo.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoFormDto {
	
	@NotNull
	@NotEmpty
	@Size(min = 5, max = 6)
	@Pattern(regexp = "[a-zA-Z]{4}[0-9][0-9]?", message = "{transacao.ticker.invalido}")
	private String ticker;
	
	@PastOrPresent
	private LocalDate data;
	
	@NotNull
	@DecimalMin("0.01")
	private BigDecimal preco;
	
	@NotNull
	private int quantidade;
	
	@NotNull
	private TipoTransacao tipo;
	
	@JsonAlias("usuario_id")
	private Long usuarioId;
	
	public void setPreco(String preco) {
		this.preco =  new BigDecimal(preco.replace(",", ".")) ;
	}
}
