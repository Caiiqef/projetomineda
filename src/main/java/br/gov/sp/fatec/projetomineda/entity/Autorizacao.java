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

@Entity
@Table(name = "autorizacao")
public class Autorizacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autorizacao_id")
    private Long id;

    @Column(name = "autorizacao_nome")
    private String nome;

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
