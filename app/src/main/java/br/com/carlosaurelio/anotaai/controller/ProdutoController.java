package br.com.carlosaurelio.anotaai.controller;

import java.util.List;

import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import br.com.carlosaurelio.anotaai.model.Produto;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;

public class ProdutoController {

    public void deletarGrupo(GrupoProduto grupoProduto) {
        grupoProduto.delete();
    }

    public List<GrupoProduto> listarGrupos() {
        return GrupoProduto.listAll(GrupoProduto.class);
    }

    public void inserirGrupo(GrupoProduto grupoProduto) {
        grupoProduto.save();
    }

    public GrupoProduto buscarGrupo(Long idGrupo) {
        return GrupoProduto.findById(GrupoProduto.class, idGrupo);
    }

    public void atualizarGrupo(GrupoProduto grupoProduto) {
        grupoProduto.save();
    }

    public List<UnidadeMedida> listarUnidadeMedidas() {
        return UnidadeMedida.listAll(UnidadeMedida.class);
    }

    public void deletarUnidadeMedida(UnidadeMedida unidadeMedida) {
        unidadeMedida.delete();
    }

    public void inserirUnidadeMedida(UnidadeMedida unidadeMedida) {
        unidadeMedida.save();
    }

    public UnidadeMedida buscarUnidadeMedida(Long idUN) {
        return UnidadeMedida.findById(UnidadeMedida.class, idUN);
    }

    public void atualizarUnidadeMedida(UnidadeMedida unidadeMedida) {
        unidadeMedida.save();
    }

    public List<Produto> listaProdutos() {
        return Produto.listAll(Produto.class);
    }

    public void deletarProduto(Produto produto) {
        produto.delete();
    }

    public void inserirProduto(Produto produto) {
        produto.save();
    }

    public Produto buscarProduto(Long idProduto) {
        return Produto.findById(Produto.class, idProduto);
    }

    public void atualizarProduto(Produto produto) {
        produto.save();
    }
}
