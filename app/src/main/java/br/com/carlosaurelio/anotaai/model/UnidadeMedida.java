package br.com.carlosaurelio.anotaai.model;

import com.orm.SugarRecord;

public class UnidadeMedida extends SugarRecord{

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

    public UnidadeMedida(String unidadeMedida, String descricao, String updateDate) {
        this.unidadeMedida = unidadeMedida;
        this.descricao = descricao;
        this.updateDate = updateDate;
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
