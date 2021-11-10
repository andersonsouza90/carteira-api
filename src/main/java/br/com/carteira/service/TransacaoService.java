package br.com.carteira.service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import br.com.carteira.dto.AtualizarTransacaoFormDto;
import br.com.carteira.dto.TransacaoConsultarDto;
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
	
	@Autowired // o model mapper foi adicionado no beans configurations para não precisar dar new modelMapper() 
	private ModelMapper modelMapper;
	
	public Page<TransacaoDto> listar(Pageable paginacao, Usuario usuario) {
		
		Page<Transacao> transacoes = transacaoRepository.findAllByUsuario(paginacao, usuario);
		
		return transacoes
				.map(t -> modelMapper.map(t, TransacaoDto.class));
	}
	
	@Transactional
	public TransacaoDto cadastrar(TransacaoFormDto dto, Usuario logado) {
		
		Long idUsuario = dto.getUsuarioId();
		
		try {
			
			Usuario usuario = usuarioRepository.getById(idUsuario);
			
			if (!usuario.equals(logado)) {
				lancarErroAcessoNegado();
			}
			
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(usuario);
			
			transacaoRepository.save(transacao);
			
			return modelMapper.map(transacao, TransacaoDto.class);
			
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Usuário inexistente!");
		}
		
	}
	
	@Transactional
	public TransacaoDto atualizar(AtualizarTransacaoFormDto dto, Usuario logado) {
		
		Transacao t = transacaoRepository.getById(dto.getId());
		
		if (!t.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		t.atualizarInformacoes(dto.getTicker(), dto.getData(), dto.getPreco(), dto.getQuantidade(), dto.getTipo());
		
		return modelMapper.map(t, TransacaoDto.class);
	}
	

	@Transactional
	public void deletar(Long id, Usuario logado) {
		
		Transacao t = transacaoRepository.getById(id);
		
		if (!t.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		transacaoRepository.deleteById(id);
	}

	public TransacaoConsultarDto consultar(Long id, Usuario logado) {
		
		Transacao t = transacaoRepository
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
		
		if (!t.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		return modelMapper.map(t, TransacaoConsultarDto.class);
	}
	
	private void lancarErroAcessoNegado() {
		throw new AccessDeniedException("Acesso negado!");
	}
	
	
}
