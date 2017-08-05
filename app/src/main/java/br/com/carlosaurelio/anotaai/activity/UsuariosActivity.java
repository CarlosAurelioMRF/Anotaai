package br.com.carlosaurelio.anotaai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.orm.SugarContext;

import java.util.List;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.adapter.UsuariosAdapter;
import br.com.carlosaurelio.anotaai.controller.UsuarioController;
import br.com.carlosaurelio.anotaai.model.Usuario;

public class UsuariosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SugarContext.init(getApplicationContext());

        List<Usuario> mUsuarios = new UsuarioController().listarContatos();

        UsuariosAdapter adapter = new UsuariosAdapter(this, mUsuarios);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvUsuarios);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdicionarUsuario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication().getApplicationContext(), AddEditUsuarioActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE_ACTIVITY", 0);
                bundle.putString("TITLE_ACTIVITY", "Novo usuário");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
