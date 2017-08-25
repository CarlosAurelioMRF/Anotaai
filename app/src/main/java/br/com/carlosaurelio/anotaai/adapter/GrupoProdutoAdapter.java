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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.activity.AddEditGrupoActivity;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class GrupoProdutoAdapter extends RealmRecyclerViewAdapter<GrupoProduto, GrupoProdutoAdapter.MyViewHolder> {

    public GrupoProdutoAdapter(Context context, RealmResults<GrupoProduto> grupoProdtuoResults, boolean autoUpdate) {
        super(context, grupoProdtuoResults, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grupo_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final GrupoProduto grupoProduto = getItem(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditGrupoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("codigoGrupo", grupoProduto.getId());
                bundle.putString("nomeGrupo", grupoProduto.getNomeGrupo());
                bundle.putInt("tipoGrupo", grupoProduto.getTipoGrupo());
                bundle.putInt("TYPE_ACTIVITY", 1);
                bundle.putString("TITLE_ACTIVITY", "Editando grupo");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.txtIdGrupo.setText("#" + grupoProduto.getId());
        holder.txtNomeGrupo.setText(grupoProduto.getNomeGrupo());

        String nomeTipo;

        if (context.getResources().getResourceEntryName(grupoProduto.getTipoGrupo()).equals("rdgAlimentos")) {
            nomeTipo = "Alimentos";
        } else if (context.getResources().getResourceEntryName(grupoProduto.getTipoGrupo()).equals("rdgAlcoolicos")) {
            nomeTipo = "Alcoólicos";
        } else if (context.getResources().getResourceEntryName(grupoProduto.getTipoGrupo()).equals("rdgNaoAlcoolicos")) {
            nomeTipo = "Não Alcoólicos";
        } else {
            nomeTipo = "Outros";
        }

        holder.txtTipo.setText(nomeTipo);

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Alerta").setMessage("Deseja realmente deletar o grupo " + grupoProduto.getNomeGrupo() + "?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            ProdutoController controller = new ProdutoController(false);
                            String mNomeGrupo = grupoProduto.getNomeGrupo();
                            controller.deletarGrupo(grupoProduto);
                            Toast.makeText(context, mNomeGrupo + " deletado com sucesso.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
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

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdGrupo, txtNomeGrupo, txtTipo;
        public Button btnDeletar;

        public MyViewHolder(View view) {
            super(view);
            txtIdGrupo = (TextView) view.findViewById(R.id.txtIdGrupo);
            txtNomeGrupo = (TextView) view.findViewById(R.id.txtNomeGrupo);
            txtTipo = (TextView) view.findViewById(R.id.txtTipoGrupo);
            btnDeletar = (Button) view.findViewById(R.id.btnDeletarGrupo);
        }
    }
}
