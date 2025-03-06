package com.reviewtodolist.controllers;

import com.reviewtodolist.model.Cliente;
import com.reviewtodolist.services.ClienteService;
import com.reviewtodolist.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping("/client")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> byId(@PathVariable Long id){
        Cliente cliente = this.clienteService.buscarClienteById(id);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cliente>> buscarTodosClientesPorUsuarioId(@PathVariable Long userId){
        this.usuarioService.buscarUsuarioById(userId);
        List<Cliente> clientes = this.clienteService.buscarTodosClientesByUsuarioId(userId);
        return ResponseEntity.ok().body(clientes);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> criar(@Valid @RequestBody Cliente cliente){
        this.clienteService.criarCliente(cliente);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(cliente.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> atualizar(@Valid @RequestBody Cliente cliente,
                                          @PathVariable Long id){
        cliente.setId(id);
        this.clienteService.atualizarCliente(cliente);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        this.clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }


}
