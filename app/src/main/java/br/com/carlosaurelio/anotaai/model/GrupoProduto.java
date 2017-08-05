package br.com.carlosaurelio.anotaai.model;

import com.orm.SugarRecord;

public class GrupoProduto extends SugarRecord {

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

    public GrupoProduto(String nomeGrupo, int tipoGrupo, String updateDate) {
        this.nomeGrupo = nomeGrupo;
        this.tipoGrupo = tipoGrupo;
        this.updateDate = updateDate;
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
