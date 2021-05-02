package br.gov.sp.fatec.projetomineda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.projetomineda.entity.Autorizacao;

public interface AutorizacaoRepository extends JpaRepository<Autorizacao, Long>{
    
    public Autorizacao findByNome(String autorizacao);
}