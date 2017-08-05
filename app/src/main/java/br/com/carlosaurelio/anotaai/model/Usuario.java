package br.com.carlosaurelio.anotaai.model;

import com.orm.SugarRecord;

public class Usuario extends SugarRecord {

    private String nomeCompleto;
    private String nomeUsuario;
    private int tipoUsuario;
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

    public Usuario(String nomeCompleto, String nomeUsuario, int tipoUsuario, String senhaUsuario, String updateDate) {
        this.nomeCompleto = nomeCompleto;
        this.nomeUsuario = nomeUsuario;
        this.tipoUsuario = tipoUsuario;
        this.senhaUsuario = senhaUsuario;
        this.updateDate = updateDate;
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
