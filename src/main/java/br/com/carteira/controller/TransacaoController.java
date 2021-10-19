package br.com.carteira.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.carteira.dto.TransacaoDto;
import br.com.carteira.dto.TransacaoFormDto;
import br.com.carteira.service.TransacaoService;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
	
	@Autowired
	private TransacaoService service;
	
	//Para retornar XML importar as dependencias no pom.xml e usar o produces 
	//@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	
	@GetMapping
	public Page<TransacaoDto> listar(@PageableDefault(size = 10) Pageable paginacao) {
		//conversão modo 1
//		List<TransacaoDto> transacoesDto = new ArrayList<TransacaoDto>();
//		for (Transacao t : transacoes) {
//			TransacaoDto dto = new TransacaoDto();
//			dto.setTicker(t.getTicker());
//			dto.setPreco(t.getPreco());
//			dto.setQuantidade(t.getQuantidade());
//			dto.setTipo(t.getTipo());
//			
//			transacoesDto.add(dto);
//		}
//		return transacoesDto;
		
		//Modo 2 - necessário fazer um construtor recebendo o objeto Transacao
		//return transacoes.stream().map(TransacaoDto::new).collect(Collectors.toList());
		
		//Modo 3
//		return transacoes
//					.stream()
//					.map(t -> modelMapper.map(t, TransacaoDto.class)).collect(Collectors.toList());
		
		return service.listar(paginacao);
		
		
	}
	
	@PostMapping
	public ResponseEntity<TransacaoDto> cadastrar(@RequestBody @Valid TransacaoFormDto dto,
													UriComponentsBuilder uriBuilder) {
		TransacaoDto transacaoDto = service.cadastrar(dto);
		
		URI uri = uriBuilder
				.path("/transacao/{id}")
				.buildAndExpand(transacaoDto.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(transacaoDto);
	}
	
}
