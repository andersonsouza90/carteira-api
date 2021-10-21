package br.com.carteira.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ItemRelatorioCarteiraDto {
	
	private String ticker;
	private long quantidade;
	private BigDecimal percentual;
	
	public ItemRelatorioCarteiraDto(String ticker, long quantidade, Long quantidadeTotal) {
		this.ticker = ticker;
		this.quantidade = quantidade;
		this.percentual = new BigDecimal(quantidade)
								.divide(new BigDecimal(quantidadeTotal), 4, RoundingMode.HALF_UP)
								.multiply(new BigDecimal("100"))
								.setScale(2);
	}
	
	
}
