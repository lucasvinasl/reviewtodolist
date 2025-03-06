package com.reviewtodolist.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = Cliente.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Cliente {

    public static final String TABLE_NAME = "clientes";

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    //@JsonBackReference
    private Usuario usuario;


    @Column(name = "nome_cliente", length = 100, nullable = false, unique = true)
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String nomeCliente;

    @Column(name = "telefone_cliente", length = 20)
    private String telefoneCliente;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefoneCliente() {
        return telefoneCliente;
    }

    public void setTelefoneCliente(String telefoneCliente) {
        this.telefoneCliente = telefoneCliente;
    }
}
