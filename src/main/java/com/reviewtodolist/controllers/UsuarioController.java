package com.reviewtodolist.controllers;

import com.reviewtodolist.model.Usuario;
import com.reviewtodolist.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/user")
@Validated
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> byId(@PathVariable Long id){
        Usuario usuario = this.usuarioService.buscarUsuarioById(id);
        return ResponseEntity.ok().body(usuario);
    }

    @PostMapping
    public ResponseEntity<Void> criar(@Valid @RequestBody Usuario usuario){
        this.usuarioService.criarUsuario(usuario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(usuario.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PutMapping("/{id}")
    @Validated(Usuario.AttUsuario.class)
    public ResponseEntity<Void> atualizar(@Valid @RequestBody Usuario usuario,
                                          @PathVariable Long id){
        usuario.setId(id);
        usuario = this.usuarioService.atualizarUsuario(usuario);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        this.usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }


}
