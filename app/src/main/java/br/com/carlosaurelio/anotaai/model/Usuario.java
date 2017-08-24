package br.com.carlosaurelio.anotaai.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Usuario extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nomeCompleto;
    @Required
    private String nomeUsuario;
    private int tipoUsuario;
    @Required
    private String senhaUsuario;
    private String createDate;
    private String updateDate;

    public Usuario() {

    }

    public Usuario(String nomeCompleto, String nomeUsuario, int tipoUsuario, String senhaUsuario, String createDate, String updateDate) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.tipoUsuario = tipoUsuario;
        this.senhaUsuario = senhaUsuario;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Usuario(int id, String nomeCompleto, String nomeUsuario, int tipoUsuario, String senhaUsuario, String updateDate) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.tipoUsuario = tipoUsuario;
        this.senhaUsuario = senhaUsuario;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String toString() {
        return "Nome: " + this.nomeCompleto + "\n" +
                "Usuário: " + this.nomeUsuario + "\n" +
                "Criado em: " + this.createDate;
    }
}
