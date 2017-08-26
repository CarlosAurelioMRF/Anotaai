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
import br.com.carlosaurelio.anotaai.activity.AddEditUsuarioActivity;
import br.com.carlosaurelio.anotaai.controller.UsuarioController;
import br.com.carlosaurelio.anotaai.model.Usuario;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class UsuariosAdapter extends RealmRecyclerViewAdapter<Usuario, UsuariosAdapter.MyViewHolder>{

    public UsuariosAdapter(Context mContext, RealmResults<Usuario> usuarioResults,  boolean autoUpdate) {
        super(mContext, usuarioResults, autoUpdate);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.usuarios_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Usuario usuario = getItem(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddEditUsuarioActivity.class);
                intent.putExtra("codigoUsuario", usuario.getId());
                intent.putExtra("nomeCompleto", usuario.getNomeCompleto());
                intent.putExtra("nomeUsuario", usuario.getNomeUsuario());
                intent.putExtra("tipoUsuario", usuario.getTipoUsuario());
                intent.putExtra("TYPE_ACTIVITY", 1);
                context.startActivity(intent);
            }
        });

        holder.txtIdUsuario.setText("#" + usuario.getId());
        holder.txtNomeCompleto.setText(usuario.getNomeCompleto());
        holder.txtNomeUsuario.setText(usuario.getNomeUsuario());

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MsgFunctions().questionMessage(context, context.getString(R.string.title_activity_usuarios),
                        context.getString(R.string.question_delete),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                try {
                                    UsuarioController controller = new UsuarioController(false);
                                    String mNomeCompleto = usuario.getNomeCompleto();
                                    controller.deletarUsuario(usuario);
                                    new MsgFunctions().toastDelete(context, mNomeCompleto);
                                } catch (Exception e) {
                                    new MsgFunctions().errorMessage(context,
                                            context.getString(R.string.title_activity_usuarios),
                                            context.getString(R.string.error_delete));
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdUsuario, txtNomeCompleto, txtNomeUsuario;
        public Button btnDeletar;

        public MyViewHolder(View view) {
            super(view);
            txtIdUsuario = (TextView) view.findViewById(R.id.txtIdUsuario);
            txtNomeCompleto = (TextView) view.findViewById(R.id.txtNomeCompleto);
            txtNomeUsuario = (TextView) view.findViewById(R.id.txtNomeUsuario);
            btnDeletar = (Button) view.findViewById(R.id.btnDeletarUsuario);
        }
    }
}