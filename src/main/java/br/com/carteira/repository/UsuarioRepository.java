package br.com.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carteira.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
}
