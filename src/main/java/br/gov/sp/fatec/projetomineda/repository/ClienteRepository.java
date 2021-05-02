package br.gov.sp.fatec.projetomineda.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.gov.sp.fatec.projetomineda.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    public List<Cliente> findByNomeContainsIgnoreCase(String nome );

    public Cliente findByNome(String nome);

    public Optional<Cliente> findById(Long id);

    public Cliente findByNomeAndSenha(String nome, String senha);

    public List<Cliente> findByAutorizacoesNome(String autorizacao);

    // Queries

    @Query("select c from  Cliente c where c.nome = ?1 ")
    public Cliente buscaClientePorNome (String nome);

    @Query("select c from Cliente c where c.id = ?1")
    public Cliente buscarClientePorId(Long id);

    @Query("select c from Cliente c where c.nome = ?1 and c.senha = ?2")
    public Cliente buscaClientePorNomeESenha (String nome, String senha);

    @Query("select c from Cliente c inner join c.autorizacoes a where a.nome = ?1")
    public List<Cliente> buscaPorNomeAutorizacao(String autorizacao);

    @Query("select c from  Cliente c where c.email = ?1 ")
    public Cliente buscarClientePorEmail (String email);

    // @Query("select c from Cliente c inner join c.pedidos p where p.desc = ?1")
    // public Cliente buscaPorNomePedido(String pedido);
}