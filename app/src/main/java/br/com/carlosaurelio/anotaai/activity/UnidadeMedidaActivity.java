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
import br.com.carlosaurelio.anotaai.adapter.UnidadeMedidaAdapter;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import br.com.carlosaurelio.anotaai.other.DividerItemDecoration;

public class UnidadeMedidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidade_medida);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SugarContext.init(getApplicationContext());

        List<UnidadeMedida> mUnidadeMedidas = new ProdutoController().listarUnidadeMedidas();

        UnidadeMedidaAdapter adapter = new UnidadeMedidaAdapter(this, mUnidadeMedidas);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvUnidadeMedida);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdicionarUnidadeMedida);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication().getApplicationContext(), AddEditUnidadeMedidaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE_ACTIVITY", 0);
                bundle.putString("TITLE_ACTIVITY", "Nova unidade medida");
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
