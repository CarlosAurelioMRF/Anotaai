package br.com.carlosaurelio.anotaai.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.adapter.UsuariosAdapter;
import br.com.carlosaurelio.anotaai.controller.UsuarioController;
import br.com.carlosaurelio.anotaai.model.Usuario;
import io.realm.RealmResults;

public class UsuariosActivity extends AppCompatActivity {

    private UsuarioController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = new UsuarioController(true);

        RealmResults<Usuario> mUsuarios = controller.listarUsuarios();

        UsuariosAdapter adapter = new UsuariosAdapter(this, mUsuarios, true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvUsuarios);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdicionarUsuario);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication().getApplicationContext(), AddEditUsuarioActivity.class);
                intent.putExtra("TYPE_ACTIVITY", 0);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        controller.close();
        super.onDestroy();
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
