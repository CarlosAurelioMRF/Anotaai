package br.com.carlosaurelio.anotaai.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Produto extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String nomeProduto;
    private String codigoExterno;
    private UnidadeMedida unidadeMedida;
    private GrupoProduto grupoProduto;
    @Required
    private Double precoVenda;
    private String createDate;
    private String updateDate;

    public Produto() {

    }

    public Produto(String nomeProduto, String codigoExterno, UnidadeMedida unidadeMedida, GrupoProduto grupoProduto, Double precoVenda, String createDate, String updateDate) {
        this.nomeProduto = nomeProduto;
        this.codigoExterno = codigoExterno;
        this.unidadeMedida = unidadeMedida;
        this.grupoProduto = grupoProduto;
        this.precoVenda = precoVenda;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public Produto(String nomeProduto, String codigoExterno, UnidadeMedida unidadeMedida, GrupoProduto grupoProduto, Double precoVenda, String updateDate) {
        this.nomeProduto = nomeProduto;
        this.codigoExterno = codigoExterno;
        this.unidadeMedida = unidadeMedida;
        this.grupoProduto = grupoProduto;
        this.precoVenda = precoVenda;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public GrupoProduto getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(GrupoProduto grupoProduto) {
        this.grupoProduto = grupoProduto;
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
