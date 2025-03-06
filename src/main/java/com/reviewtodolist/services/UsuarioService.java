package com.reviewtodolist.services;


import com.reviewtodolist.model.Usuario;
import com.reviewtodolist.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario findUsuarioById(Long id){
        Optional<Usuario> usuario = this.usuarioRepository.findById(id) ;

        return usuario.orElseThrow(()-> new RuntimeException(
                "Usuário não encontrado - ID: "+id+" Tipo: "+Usuario.class.getName()
        ));
    }

    @Transactional
    public Usuario criarUsuario(Usuario usuario){
        usuario.setId(null);
        usuario.setSenha(usuario.getSenha());
        usuario = this.usuarioRepository.save(usuario);
        return usuario;
    }

    @Transactional
    public Usuario atualizarUsuario(Usuario usuario){

        Usuario usuarioExistente = findUsuarioById(usuario.getId());
        usuarioExistente.setSenha(usuario.getSenha());
        return this.usuarioRepository.save(usuarioExistente);

    }

    public void deleteUsuario(Long id){

        Usuario usuario = findUsuarioById(id);

        try{
            this.usuarioRepository.delete(usuario);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível excluir o Usuário: "+e);
        }
    }




}
