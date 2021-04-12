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
@Table(name = "pedido")
public class Pedido {
    
    @JsonView(View.PedidoLista.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long id;

    @JsonView({View.ClienteResumo.class, View.PedidoLista.class})
    @Column(name = "pedido_desc")
    private String desc;

    @JsonView({View.ClienteResumo.class, View.PedidoLista.class})
    @Column(name = "pedido_price")
    private double price; 
    
    @JsonView({View.PedidoLista.class})
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "pedidos")
    private Set<Cliente> clientes;
    

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Set<Cliente> getCliente(){
        return this.clientes;
    }

    public void setCliente(Set<Cliente> clientes){
        this.clientes = clientes;
    }
}