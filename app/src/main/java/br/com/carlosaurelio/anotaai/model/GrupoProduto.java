package br.com.carlosaurelio.anotaai.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class GrupoProduto extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nomeGrupo;
    private int tipoGrupo;
    private String createDate;
    private String updateDate;

    public GrupoProduto() {

    }

    public GrupoProduto(String nomeGrupo, int tipoGrupo, String createDate, String updateDate) {
        this.nomeGrupo = nomeGrupo;
        this.tipoGrupo = tipoGrupo;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public GrupoProduto(int id, String nomeGrupo, int tipoGrupo, String updateDate) {
        this.id = id;
        this.nomeGrupo = nomeGrupo;
        this.tipoGrupo = tipoGrupo;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public int getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(int tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
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
}
