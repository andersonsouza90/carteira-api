package br.com.carteira.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.ItemRelatorioCarteiraDto;
import br.com.carteira.repository.TransacaoRepository;

@Service
public class RelatorioService {
	
	@Autowired
	private TransacaoRepository repository;
	
	public List<ItemRelatorioCarteiraDto> relatorioCarteira(){
		return repository.relatorioCarteira();
	}
}
