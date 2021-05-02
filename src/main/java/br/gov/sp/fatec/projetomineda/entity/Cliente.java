package br.gov.sp.fatec.projetomineda.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import br.gov.sp.fatec.projetomineda.controller.View;

@Entity
@Table(name = "cliente")
public class Cliente {

    @JsonView(View.ClienteCompleto.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Long id;

    @JsonView({View.ClienteResumo.class, View.AutorizacaoResumo.class, View.PedidoLista.class})
    @Column(name = "cliente_nome")
    private String nome;

    @JsonView({View.ClienteCompleto.class, View.PedidoLista.class})
    @Column(name = "cliente_email")
    private String email;

    @Column(name = "cliente_senha")
    private String senha;

    @JsonView(View.ClienteResumo.class)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cau_cliente_autorizacao",
        joinColumns = { @JoinColumn(name = "cliente_id")},
        inverseJoinColumns = { @JoinColumn(name = "autorizacao_id") }
        )
    private Set<Autorizacao> autorizacoes;

    @JsonView(View.ClienteResumo.class)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "cliente_pedido",
        joinColumns = {@JoinColumn(name = "cliente_id")},
        inverseJoinColumns = {@JoinColumn(name = "pedido_id")}
        )
    private Set<Pedido> pedidos;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public Set<Autorizacao> getAutorizacoes() {
        return this.autorizacoes;
    }

    public void setAutorizacoes(Set<Autorizacao> autorizacoes) {
        this.autorizacoes = autorizacoes;
    }

    public Set<Pedido> getPedidos(){
        return this.pedidos;
    }

    public void setPedidos(Set<Pedido> pedidos){
        this.pedidos = pedidos;
    }
}
