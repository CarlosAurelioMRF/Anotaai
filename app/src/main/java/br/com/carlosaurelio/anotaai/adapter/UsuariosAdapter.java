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
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.activity.AddEditUsuarioActivity;
import br.com.carlosaurelio.anotaai.controller.UsuarioController;
import br.com.carlosaurelio.anotaai.model.Usuario;
import io.realm.RealmBaseAdapter;
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
                Bundle bundle = new Bundle();
                bundle.putInt("codigoUsuario", usuario.getId());
                bundle.putString("nomeCompleto", usuario.getNomeCompleto());
                bundle.putString("nomeUsuario", usuario.getNomeUsuario());
                bundle.putInt("tipoUsuario", usuario.getTipoUsuario());
                bundle.putInt("TYPE_ACTIVITY", 1);
                bundle.putString("TITLE_ACTIVITY", "Editando usuário");
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.txtIdUsuario.setText("#" + usuario.getId());
        holder.txtNomeCompleto.setText(usuario.getNomeCompleto());
        holder.txtNomeUsuario.setText(usuario.getNomeUsuario());

        holder.btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Alerta").setMessage("Deseja realmente deletar o usuário " + usuario.getNomeUsuario() + "?");
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            UsuarioController controller = new UsuarioController(false);
                            controller.deletarUsuario(usuario);
                            Toast.makeText(context, usuario.getNomeCompleto() + " deletado com sucesso.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setPositiveButton("OK", null).setMessage("Não possível deletar o usuário!").create().show();
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