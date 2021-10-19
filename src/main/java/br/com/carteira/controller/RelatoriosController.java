package br.com.carteira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.carteira.dto.ItemRelatorioCarteiraDto;
import br.com.carteira.service.RelatorioService;

@RestController
@RequestMapping("/relatorios")
public class RelatoriosController {
	
	@Autowired
	private RelatorioService service;
	
	@GetMapping("/carteira")
	public List<ItemRelatorioCarteiraDto> relatorioCarteira(){
		return service.relatorioCarteira();
	}
}
