package br.gov.sp.fatec.projetomineda.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import br.gov.sp.fatec.projetomineda.controller.View;

@Entity
@Table(name = "autorizacao")
public class Autorizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autorizacao_id")
    private Long id;

    @JsonView({View.ClienteResumo.class, View.AutorizacaoResumo.class, })
    @Column(name = "autorizacao_nome")
    private String nome;

    @JsonView({View.AutorizacaoResumo.class})
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "autorizacoes")
    private Set<Cliente> clientes;

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

    public Set<Cliente> getCliente(){
        return this.clientes;
    }

    public void setCliente(Set<Cliente> clientes){
        this.clientes = clientes;
    }
}
