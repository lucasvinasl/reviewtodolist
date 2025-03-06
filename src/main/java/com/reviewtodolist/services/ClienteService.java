package com.reviewtodolist.services;

import com.reviewtodolist.model.Cliente;
import com.reviewtodolist.model.Usuario;
import com.reviewtodolist.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Cliente buscarClienteById(Long id){
        Optional<Cliente> cliente = this.clienteRepository.findById(id);

        return cliente.orElseThrow(() -> new RuntimeException(
                "Cliente não encontrado. ID: " +id+ " Tipo: "+Cliente.class.getName()
        ));
    }

    public List<Cliente> buscarTodosClientesByUsuarioId(Long id){
        List<Cliente> listaClientes = this.clienteRepository.findByUsuario_Id(id);
        return listaClientes;
    }

    @Transactional
    public Cliente criarCliente(Cliente cliente){
        Usuario usuario = this.usuarioService.buscarUsuarioById(cliente.getUsuario().getId());
        cliente.setId(null);
        cliente.setUsuario(usuario);
        cliente = this.clienteRepository.save(cliente);
        return cliente;
    }

    @Transactional
    public Cliente atualizarCliente(Cliente cliente){

        Cliente clienteExiste = buscarClienteById(cliente.getId());
        clienteExiste.setNomeCliente(cliente.getNomeCliente());
        clienteExiste.setTelefoneCliente(cliente.getTelefoneCliente());
        clienteExiste = this.clienteRepository.save(clienteExiste);
        return clienteExiste;

    }

    public void deletarCliente(Long id){
        Cliente cliente = buscarClienteById(id);

        try{
            this.clienteRepository.delete(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Cliete não localizado. "+ e);
        }
    }


}
