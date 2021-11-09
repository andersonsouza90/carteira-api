package br.com.carteira.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.carteira.modelo.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long>{

}
