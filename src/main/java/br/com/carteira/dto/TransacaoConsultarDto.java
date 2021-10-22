package br.com.carteira.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class TransacaoConsultarDto extends TransacaoDto{
	
	private LocalDate data;
	private UsuarioDto usuario;
	
	
}
