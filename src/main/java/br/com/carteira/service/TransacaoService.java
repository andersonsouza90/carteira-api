package br.com.carteira.service;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.TransacaoDto;
import br.com.carteira.dto.TransacaoFormDto;
import br.com.carteira.modelo.Transacao;
import br.com.carteira.modelo.Usuario;
import br.com.carteira.repository.TransacaoRepository;
import br.com.carteira.repository.UsuarioRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	private ModelMapper modelMapper = new ModelMapper();
	
	public Page<TransacaoDto> listar(Pageable paginacao) {
		
		Page<Transacao> transacoes = transacaoRepository.findAll(paginacao);
		
		return transacoes
				.map(t -> modelMapper.map(t, TransacaoDto.class));
	}
	
	@Transactional
	public TransacaoDto cadastrar(TransacaoFormDto dto) {
		
		Long idUsuario = dto.getUsuarioId();
		Usuario usuario = usuarioRepository.getById(idUsuario);
		
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacao.setId(null);
		transacao.setUsuario(usuario);
		
		transacaoRepository.save(transacao);
		return modelMapper.map(transacao, TransacaoDto.class);
	}
	
	
}
