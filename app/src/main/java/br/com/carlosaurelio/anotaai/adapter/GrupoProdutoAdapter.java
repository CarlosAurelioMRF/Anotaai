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
import br.com.carlosaurelio.anotaai.activity.AddEditGrupoActivity;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;

public class GrupoProdutoAdapter extends RecyclerView.Adapter<GrupoProdutoAdapter.MyViewHolder> {

    private Context mContext;
    private List<GrupoProduto> mGrupoProdutoList;

    public GrupoProdutoAdapter(Context context, List<GrupoProduto> grupoProdutoList) {
        this.mContext = context;
        this.mGrupoProdutoList = grupoProdutoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grupo_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GrupoProduto grupoProduto = mGrupoProdutoList.get(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddEditGrupoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("codigoGrupo", grupoProduto.getId());
                bundle.putString("nomeGrupo", grupoProduto.getNomeGrupo());
                bundle.putInt("tipoGrupo", grupoProduto.getTipoGrupo());
                bundle.putInt("TYPE_ACTIVITY", 1);
                bundle.putString("TITLE_ACTIVITY", "Editando grupo");
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        holder.txtIdGrupo.setText("#" + grupoProduto.getId().toString());
        holder.txtNomeGrupo.setText(grupoProduto.getNomeGrupo());

        String nomeTipo;

        if (mContext.getResources().getResourceEntryName(grupoProduto.getTipoGrupo()).equals("rdgAlimentos")) {
            nomeTipo = "Alimentos";
        } else if (mContext.getResources().getResourceEntryName(grupoProduto.getTipoGrupo()).equals("rdgAlcoolicos")) {
            nomeTipo = "Alcoólicos";
        } else if (mContext.getResources().getResourceEntryName(grupoProduto.getTipoGrupo()).equals("rdgNaoAlcoolicos")) {
            nomeTipo = "Não Alcoólicos";
        } else {
            nomeTipo = "Outros";
        }

        holder.txtTipo.setText(nomeTipo);

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Alerta").setMessage("Deseja realmente deletar o grupo " + grupoProduto.getNomeGrupo() + "?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            new ProdutoController().deletarGrupo(grupoProduto);
                            mGrupoProdutoList.remove(position);
                            notifyItemRemoved(position);
                            Toast.makeText(mContext, grupoProduto.getNomeGrupo() + " deletado com sucesso.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                            alert.setPositiveButton("OK", null).setMessage("Não possível deletar o grupo!").create().show();
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
        return mGrupoProdutoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdGrupo, txtNomeGrupo, txtTipo;
        public ImageButton btnDeletar;

        public MyViewHolder(View view) {
            super(view);
            txtIdGrupo = (TextView) view.findViewById(R.id.txtIdGrupo);
            txtNomeGrupo = (TextView) view.findViewById(R.id.txtNomeGrupo);
            txtTipo = (TextView) view.findViewById(R.id.txtTipoGrupo);
            btnDeletar = (ImageButton) view.findViewById(R.id.btnDeletarGrupo);
        }
    }
}
