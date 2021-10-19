package br.com.carteira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemRelatorioCarteiraDto {
	
	private String ticker;
	private long quantidade;
	private double percentual;
}
