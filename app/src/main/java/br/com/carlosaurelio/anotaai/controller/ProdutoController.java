package br.com.carlosaurelio.anotaai.controller;

import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import br.com.carlosaurelio.anotaai.model.Produto;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ProdutoController {

    private Realm realm;

    public ProdutoController(boolean newInstance) {
        RealmConfiguration config = new RealmConfiguration.Builder().build();

        realm = newInstance ? Realm.getDefaultInstance() : Realm.getInstance(config);
    }
    public RealmResults<GrupoProduto> listarGrupos() {
        return realm.where(GrupoProduto.class).findAll();
    }

    public void deletarGrupo(GrupoProduto grupoProduto) {
        startTransaction();
        grupoProduto.deleteFromRealm();
        commitTransaction();
        close();
    }

    public void inserirGrupo(GrupoProduto grupoProduto) {
        startTransaction();

        if (realm.where(GrupoProduto.class).max("id") == null)
            grupoProduto.setId(1);
        else
            grupoProduto.setId(realm.where(GrupoProduto.class).max("id").intValue() + 1);

        grupoProduto.setCreateDate(grupoProduto.getUpdateDate());

        GrupoProduto realmGP = realm.copyToRealm(grupoProduto);

        commitTransaction();
        close();
    }

    public void atualizarGrupo(GrupoProduto grupoProduto) {
        startTransaction();
        GrupoProduto grupoProdutoBanco = realm.where(GrupoProduto.class).
                equalTo("id", grupoProduto.getId()).findFirst();

        grupoProduto.setCreateDate(grupoProdutoBanco.getCreateDate());

        GrupoProduto realmUN = realm.copyToRealmOrUpdate(grupoProduto);
        commitTransaction();
        close();
    }

    public RealmResults<UnidadeMedida> listarUnidadeMedidas() {
        return realm.where(UnidadeMedida.class).findAll();
    }

    public void deletarUnidadeMedida(UnidadeMedida unidadeMedida) {
        startTransaction();
        unidadeMedida.deleteFromRealm();
        commitTransaction();
        close();
    }

    public void inserirUnidadeMedida(UnidadeMedida unidadeMedida) {
        startTransaction();

        if (realm.where(UnidadeMedida.class).max("id") == null)
            unidadeMedida.setId(1);
        else
            unidadeMedida.setId(realm.where(UnidadeMedida.class).max("id").intValue() + 1);

        unidadeMedida.setCreateDate(unidadeMedida.getUpdateDate());

        UnidadeMedida realmUN = realm.copyToRealm(unidadeMedida);

        commitTransaction();
        close();
    }

    public void atualizarUnidadeMedida(UnidadeMedida unidadeMedida) {
        startTransaction();
        UnidadeMedida unidadeMedidaBanco = realm.where(UnidadeMedida.class).
                equalTo("id", unidadeMedida.getId()).findFirst();

        unidadeMedida.setCreateDate(unidadeMedidaBanco.getCreateDate());

        UnidadeMedida realmUN = realm.copyToRealmOrUpdate(unidadeMedida);
        commitTransaction();
        close();
    }

    public RealmResults<Produto> listaProdutos() {
        return realm.where(Produto.class).findAll();
    }

    public void deletarProduto(Produto produto) {
        startTransaction();
        produto.deleteFromRealm();
        commitTransaction();
        close();
    }

//    public void inserirProduto(Produto produto) {
//        produto.save();
//    }
//
//    public Produto buscarProduto(Long idProduto) {
//        return Produto.findById(Produto.class, idProduto);
//    }
//
//    public void atualizarProduto(Produto produto) {
//        produto.save();
//    }

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
