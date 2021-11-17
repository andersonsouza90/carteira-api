package br.com.carteira.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.carteira.dto.AtualizarTransacaoFormDto;
import br.com.carteira.dto.TransacaoConsultarDto;
import br.com.carteira.dto.TransacaoDto;
import br.com.carteira.dto.TransacaoFormDto;
import br.com.carteira.modelo.Usuario;
import br.com.carteira.service.TransacaoService;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/transacoes")
public class TransacaoController {
	
	@Autowired
	private TransacaoService service;
	
	//Para retornar XML importar as dependencias no pom.xml e usar o produces 
	//@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
	
	@GetMapping
	public Page<TransacaoDto> listar(@PageableDefault(size = 10) Pageable paginacao, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
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
		
		return service.listar(paginacao, logado);
		
		
	}
	
	@PostMapping
	public ResponseEntity<TransacaoDto> cadastrar(@RequestBody @Valid TransacaoFormDto dto,
													UriComponentsBuilder uriBuilder,
													@ApiIgnore @AuthenticationPrincipal Usuario logado) {
		
		TransacaoDto transacaoDto = service.cadastrar(dto, logado);
		
		URI uri = uriBuilder
				.path("/transacao/{id}")
				.buildAndExpand(transacaoDto.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(transacaoDto);
	}
	
	@PutMapping
	public ResponseEntity<TransacaoDto> atualizar(@RequestBody @Valid AtualizarTransacaoFormDto dto,
															@ApiIgnore @AuthenticationPrincipal Usuario logado) {
		
		TransacaoDto atualizada = service.atualizar(dto, logado);
		return ResponseEntity.ok(atualizada);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<TransacaoDto> deletar(@PathVariable @NotNull Long id, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		service.deletar(id, logado);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TransacaoConsultarDto> consultar(@PathVariable @NotNull Long id, @ApiIgnore @AuthenticationPrincipal Usuario logado) {
		TransacaoConsultarDto dto = service.consultar(id, logado);
		return ResponseEntity.ok(dto);
	}
	
}
