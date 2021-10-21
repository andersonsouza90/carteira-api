package br.com.carteira.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.carteira.dto.ItemRelatorioCarteiraDto;
import br.com.carteira.modelo.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
	
	@Query("select new br.com.carteira.dto.ItemRelatorioCarteiraDto(  \r\n"
			+ "	t.ticker,\r\n"
			+ "	sum(case when(t.tipo = 'COMPRA') then t.quantidade else (t.quantidade * -1) end ),\r\n"
			+ "	(select sum(case when(t2.tipo = 'COMPRA') then t2.quantidade else (t2.quantidade * -1) end) from Transacao t2)) \r\n"
			+ "from Transacao t\r\n"
			+ "group by t.ticker")
	List<ItemRelatorioCarteiraDto> relatorioCarteira();

}
