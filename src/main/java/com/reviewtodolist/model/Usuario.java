package com.reviewtodolist.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonManagedReference
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //@JsonIgnore
    private List<Cliente> clientes = new ArrayList<>();


}


/*
	Entidade - Usuario:

		Anotações da Classe:

        	Pensando na concepção de um pequeno CRM o usuário terá o contexto de gerenciar os seus Clientes.

			@Entity -> Declara ao Spring que a classe é uma entidade portanto será tratada como uma Tabela diretamente no Banco de Dados.

			@Table -> Sugere algumas características dessa tabela, nesse caso: nome.

			@AllArgsConstructor
			@NoArgsConstructor
				-> Anotações do Lombok que injetam os construtores padrões de acordo com os atributos da classe, bem como o construtor vazio.

			@Getter
			@Setter
			@EqualsAndHashCode
				-> Também anotações do Lombok que intejam os métodos óbvios.


			Essas são anotações padrões para a construção de qualquer entidade básica na arquitetura REST.

		Atributos da Classe:

            public static final String TABLE_NAME = "usuarios"->

				static permite que esse atributo da classe seja acessado livremente sem necessidade de instanciar uma classe Usuários.
				final define que o nome da classe não será alterado.


			public interface CriarUsuario{}
    		public interface AttUsuario {}
				-> Essas duas interfaces são definidas afim de criar um contrato nos dois principais métodos que serão gerenciados pelo 			controller. Fazendo que eu tenha garantia(contrato) de determinados comportamentos serão implementados no momento da criação e da 			atualização de algum Usuário.


            Id do Usuario:
    			@Id
    			@GeneratedValue(strategy = GenerationType.IDENTITY)
    			@Column(name = "id_usuario", unique = true)
    			private Long id;

                O primeiro atributo padrão da entidade é o seu ID, que irá identificar de forma única aquele atributo no banco de dados.

                @Id -> do java persistence (JPA) indica essa padrão ao banco de dados

                @GeneratedValue  ->
                    Especifica a regra de como esse atributo será gerado no banco de dados:
                    Usando uma strategy IDENTITY -> seria algo semelhando ao AUTO_INCREMENTE
                    Além dessa opção, existe também:
                        AUTO -> O próprio Hibernate decide a estratégia,
                        SEQUENCE -> Utiliza uma sequência numérica para gerar ID (mais comum em PostgreSQL).,
                        TABLE -> Tabela só para armazenar o ID.

                @Column -> Especifica o nome da coluna desse atributo na minha tabela de Usuario e dá suporte a algumas características,
                nesse caso, o id será sempre um valor único.

            Nome/username do Usuario:
                    @Column(name = "nome_usuario", length = 100, nullable = false, unique = true)
                    @NotNull(groups = CriarUsuario.class)
                    @NotEmpty(groups = CriarUsuario.class)
                    @Size(groups = CriarUsuario.class, min = 2, max =  100)
                    private String nome;

                    @Column -> Define o nome da coluna do atributo, considera que ele não seja nulo, único e o seu tamanho.
                    @NotNull(groups = CriarUsuario.class)
                    @NotEmpty(groups = CriarUsuario.class)
                    @Size(groups = CriarUsuario.class, min = 2, max =  100)

                    -> Não pode ser nulo nem receber uma string vazia, além de especificar o tamanho, no momento da criação de um Usuario.
                    -> Perceba que a Interface definida anteriormente de CriarUsuario é implementada nessas 3 anotações:
                        Isso fará com que, sempre que houver a necessidade de declarar o nome/username do usuário essas 3 condições
                        deverão ser seguidos, afinal, elas passam a compor o contrato da interface.
                            ## Isso se torna mais visível nos Controllers através da anotação @Validated


            Senha/password do Usuario:
                    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                    @Column(name = "senha_usuario", length = 50, nullable = false)
                    @NotNull(groups = {CriarUsuario.class, AttUsuario.class})
                    @NotEmpty(groups = {CriarUsuario.class, AttUsuario.class})
                    @Size(groups = {CriarUsuario.class, AttUsuario.class}, min = 8, max = 50)
                    private String senha;

                    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)->
                        Em uma requisição Get por exemplo, eu teria o retorno de um Json na minha API, sendo assim o atributo de senha
                    poderia de alguma forma ser repassado. Utilizando essa anotação e indicado que o nível de acesso é apenas escrita,
                    eu garanto que o usuário pode apenas criar uma senha e nunca ver essa senha através do Json response da API,
                    somente no banco de dados, caso tenha acesso.

                    @Column(name = "senha_usuario", length = 50, nullable = false)
                    @NotNull(groups = {CriarUsuario.class, AttUsuario.class})
                    @NotEmpty(groups = {CriarUsuario.class, AttUsuario.class})
                    @Size(groups = {CriarUsuario.class, AttUsuario.class}, min = 8, max = 50)
                        -> Todos esse atributos tem o mesmo sentido para Nome, com algumas diferenças.
                        Agora eu implemento a interface AttUsuario, ou seja, eu preciso definiar uma senha não nula e não vazia com tamanho
                        específico tanto ao criar um usuário como ao atualizar um usuário:
                            Geralmente é regra de negócio de qualquer sistema você conseguir atualizar sua senha, mas nem sempre de redefinir
                            o nome do seu usuário, por exemplo, com e-mails.


            Lista de Clientes do Usuario:
                    @OneToMany(mappedBy = "usuario")
                    @JsonManagedReference
                    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
                    //@JsonIgnore
                    private List<Cliente> clientes = new ArrayList<>();

                     @OneToMany(mappedBy = "usuario") ->
                        Define o Relacionamento desse atributo, ou seja,1 Usuário pode ter N clientes. 1:X.

                        Se é uma relação 1:X, um preciso ter uma chave estrangeira que irá relacionar a tabela Usuario com a tabela Cliente.

                        mappedBy = "usuario" -> Indica o atributo que será relacionado na classe que receber a anotação complementar @ManyToOne,
                        nesse caso, na classe Cliente eu tenho o atributo private Usuario usuario que recebe essa anotação.

                        Se não houver essa informação do mappedBy, o Hibernate criaria uma nova tabela usuarios_clientes:
                            Uma coluna usuario_id e outra coluna cliente_id para relacioná-las.


                    Como eu posso adicionar vários clientes no meus, no momento em que eu fiz uma request para retornar determinado usuário,
                    caso esse usuário tenha clientes atribuidos a ele, esses clientes também retornarão o usuário nele vinculado e isso criará
                    um loop infinito no Json.

                    Para resolver esse problema e é um padrão para controlar o nível de acesso das informações que serão repassadas no Json:

                        @JsonIgnore -> Simplesmente vai ignorar esse atributo na resposta do meu Json.

                        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) -> vai dar acesso apenas a escrita desses atributos, ou seja,
                        consigo criar clientes para o meu usuário mas não posso, a priore, acessalos.

                        @JsonManagedReference -> Defino que esse atributo "Lista de Clientes" seria gerenciado/Meneged pelo atributo pai
                        @JsonBackReference que será anotado no atributo private Usuario usuario da classe Cliente.

                        Ou seja,
                            Em Usuario eu tenho uma Lista de Clientes que serão gerenciadas pelo Usuario vinculado ao Cliente.




 */

