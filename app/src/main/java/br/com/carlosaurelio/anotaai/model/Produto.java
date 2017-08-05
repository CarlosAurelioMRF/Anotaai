package br.com.carlosaurelio.anotaai.model;

import com.orm.SugarRecord;

public class Produto extends SugarRecord {

    private String nomeProduto;
    private String codigoExterno;
    private int idUnidadeMedida;
    private int idGrupo;
    private Double precoVenda;
    private String createDate;
    private String updateDate;

    public Produto() {

    }

    public Produto(String nomeProduto, String codigoExterno, int idUnidadeMedida, int idGrupo, Double precoVenda, String createDate, String updateDate) {
        this.nomeProduto = nomeProduto;
        this.codigoExterno = codigoExterno;
        this.idUnidadeMedida = idUnidadeMedida;
        this.idGrupo = idGrupo;
        this.precoVenda = precoVenda;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Produto(String nomeProduto, String codigoExterno, int idUnidadeMedida, int idGrupo, Double precoVenda, String updateDate) {
        this.nomeProduto = nomeProduto;
        this.codigoExterno = codigoExterno;
        this.idUnidadeMedida = idUnidadeMedida;
        this.idGrupo = idGrupo;
        this.precoVenda = precoVenda;
        this.updateDate = updateDate;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(String codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public int getIdUnidadeMedida() {
        return idUnidadeMedida;
    }

    public void setIdUnidadeMedida(int idUnidadeMedida) {
        this.idUnidadeMedida = idUnidadeMedida;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
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
