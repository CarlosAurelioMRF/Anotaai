package br.com.carlosaurelio.anotaai.controller;

import java.util.List;

import br.com.carlosaurelio.anotaai.model.Usuario;

public class UsuarioController {

    public List<Usuario> listarContatos() {
        return Usuario.listAll(Usuario.class);
    }

    public void deletarUsuario(Usuario usuario) {
        usuario.delete();
    }

    public void inserirUsuario(Usuario usuario) {
        usuario.save();
    }

    public Usuario buscarUsuario(long id) {
        return Usuario.findById(Usuario.class, id);
    }

    public void atualizarUsuario(Usuario usuario) {
        usuario.save();
    }

    public Usuario buscarUsuario(String nomeUsuario) {
        List<Usuario> usuarios = Usuario.find(Usuario.class, "nomeUsuario = ?", nomeUsuario);
        return usuarios.get(0);
    }

}
