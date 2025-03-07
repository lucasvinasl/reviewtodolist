package com.reviewtodolist.repositories;

import com.reviewtodolist.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByUsuario_Id(Long id);


}



/*

    Repository:

        Repository é uma interface onde permite que seja realizadas as Queries SQL comunicando a API com o Banco de Dados.

        Operações de CRUD.

        Extende à interfaces do JPA que possuem diversos métodos padrões que Queries sem precisar escrever em SQL.

        JpaRepository é definida pela Entidade e pelo tipo de ID da Entidade <Nome da Entidade, Tipo do ID>

        @Repository
        public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        }

        Além disso, eu posso declarar Queries tanto no formato Derivada, JPQL e SQL.

        Eu não preciso definir nenhuma query que já tenha por padrão, mas eu devo declarar queries que não são automáticas.

        Por exemplo, Localizar os Clientes de usuário a partir do Id do Usuário:

        Query Derivada - ClienteRepository:
            List<Cliente> findByUsuario_Id(Long id);

            JPA gera automaticamente a consulta SQL pelo nome do metodo repassado:
                List<Cliente> findByUsuario_Id(Long id);

            Em JPQL seria:
                SELECT t FROM Task t WHERE t.user.id = :id

            Em SQL seria:
                SELECT * FROM task WHERE user_id = ?;

        Query JPQL:

            JPQL é uma linguagem baseada em Objetos(Entidades/Classes) e não em Tabelas.

            @Query("SELECT c FROM Cliente c WHERE c.usuario.id = :id")
            List<Cliente> findClientesPorUsuario(@Param("id") Long id);

            Cliente c represente a entidade/classe Cliente e não mais a Tabela Cliente, então é criada uma instancia dessa classe.

            Cliente c instancia um objeto da entidade Cleinte e busca c associadas ao id: c.usuario.id

            Em SQL(Spring converte automaticamente) seria:
                SELECT * FROM cliente WHERE usuario_id = ?;



        Query Nativa SQL:
            @Query(value = "SELECT * FROM cliente WHERE usuario_id = :id", nativeQuery = true)
            List<Cliente> findClientesPorUsuario(@Param("id") Long id);


                Query pura em SQL
                O Spring executa exatamente essa query, sem conversão
                Maior controle sobre otimizações no banco de dados
                Diferente do JPQL eu não referencio o Objeto/Entidade Cliente mas sim a tabela Cliente.

                Em SQL:
                SELECT * FROM task WHERE user_id = ?;


        Qual usar?
            Se a consulta for simples → Use a Query Derivada (findByUser_Id).
            Se precisar de mais controle, mas ainda quiser JPA → Use JPQL (@Query sem nativeQuery).
            Se precisar de máxima performance e controle total → Use SQL Nativo (@Query com nativeQuery = true).
*/
