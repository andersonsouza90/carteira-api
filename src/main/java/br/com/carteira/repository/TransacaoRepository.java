package br.com.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carteira.dto.ItemRelatorioCarteiraDto;
import br.com.carteira.modelo.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	@Query("select new br.com.carteira.dto.ItemRelatorioCarteiraDto(  \r\n"
			+ "	t.ticker,\r\n"
			+ "	sum(t.quantidade),\r\n"
			+ "	sum(t.quantidade) * 1.0 / (select sum(t2.quantidade) from Transacao t2) * 1.0) \r\n"
			+ "from Transacao t\r\n"
			+ "group by t.ticker")
	List<ItemRelatorioCarteiraDto> relatorioCarteira();

}
