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
import br.com.carlosaurelio.anotaai.adapter.UnidadeMedidaAdapter;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import io.realm.RealmResults;

public class UnidadeMedidaActivity extends AppCompatActivity {

    private ProdutoController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade_medida);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = new ProdutoController(true);

        RealmResults<UnidadeMedida> mUnidadeMedidas = controller.listarUnidadeMedidas();

        UnidadeMedidaAdapter adapter = new UnidadeMedidaAdapter(this, mUnidadeMedidas, true);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvUnidadeMedida);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdicionarUnidadeMedida);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication().getApplicationContext(), AddEditUnidadeMedidaActivity.class);
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
