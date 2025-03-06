package com.reviewtodolist.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = Usuario.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Usuario {

    public static final String TABLE_NAME = "usuarios";


    public interface CriarUsuario{}
    public interface AttUsuario {}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", unique = true)
    private Long id;


    @Column(name = "nome_usuario", length = 100, nullable = false, unique = true)
    @NotNull(groups = CriarUsuario.class)
    @NotEmpty(groups = CriarUsuario.class)
    @Size(groups = CriarUsuario.class, min = 2, max =  100)
    private String nome;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "senha_usuario", length = 50, nullable = false)
    @NotNull(groups = {CriarUsuario.class, AttUsuario.class})
    @NotEmpty(groups = {CriarUsuario.class, AttUsuario.class})
    @Size(groups = {CriarUsuario.class, AttUsuario.class}, min = 8, max = 50)
    private String senha;

    @OneToMany(mappedBy = "usuario")
    //@JsonManagedReference
    @JsonIgnore
    private List<Cliente> clientes = new ArrayList<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }
}
