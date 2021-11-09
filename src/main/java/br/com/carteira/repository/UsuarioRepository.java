package br.com.carteira.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carteira.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByLogin(String username);
	
	@Query("select u from Usuario u join fetch u.perfis where u.id = :id")
	Optional<Usuario> carregarPorIdComPerfis(Long id);
	
}
