package com.reviewtodolist.repositories;

import com.reviewtodolist.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    List<Cliente> findByUsuario_Id(Long id);


}
