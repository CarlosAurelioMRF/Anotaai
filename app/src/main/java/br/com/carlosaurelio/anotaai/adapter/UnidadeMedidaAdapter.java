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

import java.util.List;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.activity.AddEditUnidadeMedidaActivity;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;

public class UnidadeMedidaAdapter extends RecyclerView.Adapter<UnidadeMedidaAdapter.MyViewHolder> {

    private Context mContext;
    private List<UnidadeMedida> mUnidadeMedidaList;

    public UnidadeMedidaAdapter(Context context, List<UnidadeMedida> unidadeMedidaList) {
        this.mContext = context;
        this.mUnidadeMedidaList = unidadeMedidaList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unidade_medida_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UnidadeMedidaAdapter.MyViewHolder holder, final int position) {
        final UnidadeMedida unidadeMedida = mUnidadeMedidaList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddEditUnidadeMedidaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("codigoUN", unidadeMedida.getId());
                bundle.putString("unidadeMedida", unidadeMedida.getUnidadeMedida());
                bundle.putString("descricao", unidadeMedida.getDescricao());
                bundle.putInt("TYPE_ACTIVITY", 1);
                bundle.putString("TITLE_ACTIVITY", "Editando unidade medida");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        holder.txtIdUN.setText("#" + unidadeMedida.getId().toString());
        holder.txtUN.setText(unidadeMedida.getUnidadeMedida());
        holder.txtDescricao.setText(unidadeMedida.getDescricao());

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Alerta").setMessage("Deseja realmente deletar a unidade medida " + unidadeMedida.getUnidadeMedida() + "?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new ProdutoController().deletarUnidadeMedida(unidadeMedida);
                            mUnidadeMedidaList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(mContext, unidadeMedida.getUnidadeMedida() + " deletado com sucesso.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setPositiveButton("OK", null).setMessage("Não possível deletar a unidade medida!").create().show();
                            e.printStackTrace();
                        }

                        dialog.cancel();
                    }
                });

                alert.setNegativeButton("Não", null).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUnidadeMedidaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdUN, txtUN, txtDescricao;
        public ImageButton btnDeletar;

        public MyViewHolder(View view) {
            super(view);
            txtIdUN = (TextView) view.findViewById(R.id.txtIdUN);
            txtUN = (TextView) view.findViewById(R.id.txtUN);
            txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
            btnDeletar = (ImageButton) view.findViewById(R.id.btnDeletarUnidadeMedida);
        }
    }
}
