package br.com.carteira.dto;

import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioFormDto {
	
	@NotBlank
	private String nome;
	@NotBlank
	private String login;
	
	@NotNull
	private Long perfilId;

}
