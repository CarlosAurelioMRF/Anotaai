package br.com.carlosaurelio.anotaai.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.activity.AddEditProdutoActivity;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.Produto;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class ProdutoAdapter extends RealmRecyclerViewAdapter<Produto, ProdutoAdapter.MyViewHolder> {

    public ProdutoAdapter(Context context, RealmResults<Produto> produtoResults, boolean autoUpdate) {
        super(context, produtoResults, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.produto_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Produto produto = getItem(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditProdutoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("codigoProduto", produto.getId());
                bundle.putString("nomeProduto", produto.getNomeProduto());
                bundle.putString("codigoExterno", produto.getCodigoExterno());
                bundle.putInt("codigoUN", produto.getUnidadeMedida().getId());
                bundle.putInt("codigoGrupo", produto.getGrupoProduto().getId());
                bundle.putDouble("precoVenda", produto.getPrecoVenda());
                bundle.putInt("TYPE_ACTIVITY", 1);
                bundle.putString("TITLE_ACTIVITY", "Editando produto");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.txtIdProduto.setText("#" + produto.getId());
        holder.txtNomeProduto.setText(produto.getNomeProduto());

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Alerta").setMessage("Deseja realmente deletar o produto " + produto.getNomeProduto() + "?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ProdutoController controller = new ProdutoController(false);
                            controller.deletarProduto(produto);
                            Toast.makeText(context, produto.getNomeProduto() + " deletado com sucesso.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setPositiveButton("OK", null).setMessage("Não possível deletar o produto!").create().show();
                            e.printStackTrace();
                        }

                        dialog.cancel();
                    }
                });

                alert.setNegativeButton("Não", null).create().show();
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdProduto, txtNomeProduto;
        public ImageButton btnDeletar;

        public MyViewHolder(View view) {
            super(view);
            txtIdProduto = (TextView) view.findViewById(R.id.txtIdProduto);
            txtNomeProduto = (TextView) view.findViewById(R.id.txtNomeProduto);
            btnDeletar = (ImageButton) view.findViewById(R.id.btnDeletarProduto);
        }
    }
}
