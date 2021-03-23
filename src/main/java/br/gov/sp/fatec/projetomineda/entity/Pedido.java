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
@Table(name = "pedido")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pedido_id")
    private Long id;

    @Column(name = "pedido_desc")
    private String desc;

    @Column(name = "pedido_price")
    private double price; 
    
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