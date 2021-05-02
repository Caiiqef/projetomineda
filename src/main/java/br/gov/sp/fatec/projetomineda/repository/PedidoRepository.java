package br.gov.sp.fatec.projetomineda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import br.gov.sp.fatec.projetomineda.entity.Pedido;

public interface PedidoRepository extends JpaRepository <Pedido, Long> {    

    @Query("select p from Pedido p where p.id = ?1")
    public Pedido buscarPedidoPorId (Long id);

    @Query("select p from Pedido p where p.desc = ?1 and p.id = ?2")
    public Pedido buscaPedidoPorNomeEId (String desc, Long id);
    
    // public Pedido findByNome(String pedido);
    // public Optional<Pedido> findById(Long id);

    @Query("select p from Pedido p where p.desc =?1")
    public Pedido buscarPedidoPorDescricao(String desc);

    public Optional<Pedido> findById(Long id);
}