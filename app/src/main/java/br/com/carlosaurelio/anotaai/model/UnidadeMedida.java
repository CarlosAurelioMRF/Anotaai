package br.com.carlosaurelio.anotaai.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class UnidadeMedida extends RealmObject{

    @PrimaryKey
    private int id;
    @Required
    private String unidadeMedida;
    private String descricao;
    private String createDate;
    private String updateDate;

    public UnidadeMedida() {

    }

    public UnidadeMedida(String unidadeMedida, String descricao, String createDate, String updateDate) {
        this.unidadeMedida = unidadeMedida;
        this.descricao = descricao;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public UnidadeMedida(int id, String unidadeMedida, String descricao, String updateDate) {
        this.id = id;
        this.unidadeMedida = unidadeMedida;
        this.descricao = descricao;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
