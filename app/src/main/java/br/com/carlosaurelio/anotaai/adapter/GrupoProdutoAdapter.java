package br.com.carlosaurelio.anotaai.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.dialog.GrupoDialog;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;
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
                GrupoDialog dialog = new GrupoDialog(grupoProduto);
                dialog.show(((FragmentActivity)context).getSupportFragmentManager(), "Grupo");
            }
        });

        holder.txtIdGrupo.setText("#" + grupoProduto.getId());
        holder.txtNomeGrupo.setText(grupoProduto.getNomeGrupo());

        String[] tipoGrupo = context.getResources().getStringArray(R.array.tipo_grupo);
        holder.txtTipo.setText(tipoGrupo[grupoProduto.getTipoGrupo()]);

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MsgFunctions().questionMessage(context, context.getString(R.string.string_grupos),
                        context.getString(R.string.question_delete),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    ProdutoController controller = new ProdutoController(false);
                                    String mNomeGrupo = grupoProduto.getNomeGrupo();
                                    controller.deletarGrupo(grupoProduto);
                                    new MsgFunctions().toastDelete(context, mNomeGrupo);
                                } catch (Exception e) {
                                    new MsgFunctions().errorMessage(context,
                                            context.getString(R.string.string_grupos),
                                            context.getString(R.string.error_delete));
                                    e.printStackTrace();
                                }
                            }
                        });
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
