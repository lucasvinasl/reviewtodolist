package com.reviewtodolist.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
    @JsonBackReference
    private Usuario usuario;


    @Column(name = "nome_cliente", length = 100, nullable = false, unique = true)
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 100)
    private String nomeCliente;

    @Column(name = "telefone_cliente", length = 20)
    private String telefoneCliente;


}
 /*
    Entidade - Cliente:
        Anotações padrões do Lombok e Entidade como em Usuario.
        Definição do nome da tabela como em Usuario.

        Todos os atributos seguem a mesma lógica do Usuario.

        Como eu tenho a relação entre Usuario e Cliente, eu tenho que instanciar essa classe como um atributo.
        @ManyToOne
        @JoinColumn(name = "usuario_id", nullable = false, updatable = false)
        @JsonBackReference
        private Usuario usuario;

        @ManyToOne -> Define a relação Vários clientes para 1 Usuário - X:1.
        @JoinColumn(name = "usuario_id", nullable = false, updatable = false) ->
            Define a coluna que fará o join/junção entre as duas tabelas e me fornecerá a referência da chave estrangeira.
            Nesse caso, será criada uma coluna "usuario_id" que retorna o ID do usuario dono daquele cliente(chave estrangeira).

        @JsonBackReference -> Fecha a relação entre Referência pai e filha para não ter o problema de Serilização do Json.

*/



