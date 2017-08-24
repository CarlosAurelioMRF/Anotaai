package br.com.carlosaurelio.anotaai.controller;

import br.com.carlosaurelio.anotaai.model.Usuario;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class UsuarioController {

    private Realm realm;

    public UsuarioController(boolean newInstance) {
        RealmConfiguration config = new RealmConfiguration.Builder().build();

        realm = newInstance ? Realm.getDefaultInstance() : Realm.getInstance(config);
    }

    public RealmResults<Usuario> listarUsuarios() {
        return realm.where(Usuario.class).findAll();
    }

    public void deletarUsuario(Usuario usuario) {
        startTransaction();
        usuario.deleteFromRealm();
        commitTransaction();
        close();
    }

    public void inserirUsuario(Usuario usuario) {
        startTransaction();

        if (realm.where(Usuario.class).max("id") == null)
            usuario.setId(1);
        else
            usuario.setId(realm.where(Usuario.class).max("id").intValue() + 1);

        Usuario realmUsuario = realm.copyToRealm(usuario);

        commitTransaction();
        close();
    }

    public void atualizarUsuario(Usuario usuario) {
        startTransaction();
        Usuario usuarioBanco = realm.where(Usuario.class).equalTo("id", usuario.getId()).findFirst();
        usuario.setCreateDate(usuarioBanco.getCreateDate());

        Usuario realmUsuario = realm.copyToRealmOrUpdate(usuario);
        commitTransaction();
        close();
    }

    private void commitTransaction() {
        realm.commitTransaction();
    }

    private void startTransaction(){
        realm.beginTransaction();
    }

    public void close(){
        realm.close();
    }

}
