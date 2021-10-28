package br.com.carteira.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LoginFormDto {
	
	@NotBlank
	private String login;
	@NotBlank
	private String senha;

}
