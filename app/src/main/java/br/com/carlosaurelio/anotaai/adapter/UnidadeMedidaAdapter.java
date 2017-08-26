package br.com.carlosaurelio.anotaai.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.activity.AddEditUnidadeMedidaActivity;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class UnidadeMedidaAdapter extends RealmRecyclerViewAdapter<UnidadeMedida, UnidadeMedidaAdapter.MyViewHolder> {

    public UnidadeMedidaAdapter(Context context, RealmResults<UnidadeMedida> unidadeMedidaResults, boolean autoUpdate) {
        super(context, unidadeMedidaResults, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unidade_medida_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UnidadeMedidaAdapter.MyViewHolder holder, final int position) {
        final UnidadeMedida unidadeMedida = getItem(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditUnidadeMedidaActivity.class);
                intent.putExtra("codigoUN", unidadeMedida.getId());
                intent.putExtra("unidadeMedida", unidadeMedida.getUnidadeMedida());
                intent.putExtra("descricao", unidadeMedida.getDescricao());
                intent.putExtra("TYPE_ACTIVITY", 1);
                context.startActivity(intent);
            }
        });

        holder.txtIdUN.setText("#" + unidadeMedida.getId());
        holder.txtUN.setText(unidadeMedida.getUnidadeMedida());
        holder.txtDescricao.setText(unidadeMedida.getDescricao());

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MsgFunctions().questionMessage(context, context.getString(R.string.string_unidade_de_medida),
                        context.getString(R.string.question_delete),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    ProdutoController controller = new ProdutoController(false);
                                    String mUnidadeMedida = unidadeMedida.getUnidadeMedida();
                                    controller.deletarUnidadeMedida(unidadeMedida);
                                    new MsgFunctions().toastDelete(context, mUnidadeMedida);
                                } catch (Exception e) {
                                    new MsgFunctions().errorMessage(context,
                                            context.getString(R.string.string_unidade_de_medida),
                                            context.getString(R.string.error_delete));
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdUN, txtUN, txtDescricao;
        public Button btnDeletar;

        public MyViewHolder(View view) {
            super(view);
            txtIdUN = (TextView) view.findViewById(R.id.txtIdUN);
            txtUN = (TextView) view.findViewById(R.id.txtUN);
            txtDescricao = (TextView) view.findViewById(R.id.txtDescricao);
            btnDeletar = (Button) view.findViewById(R.id.btnDeletarUnidadeMedida);
        }
    }
}
