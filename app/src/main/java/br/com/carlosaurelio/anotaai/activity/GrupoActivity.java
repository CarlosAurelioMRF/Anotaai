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
import br.com.carlosaurelio.anotaai.adapter.GrupoProdutoAdapter;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import br.com.carlosaurelio.anotaai.other.DividerItemDecoration;

public class GrupoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SugarContext.init(getApplicationContext());

        List<GrupoProduto> mGrupoProduto = new ProdutoController().listarGrupos();

        GrupoProdutoAdapter adapter = new GrupoProdutoAdapter(this, mGrupoProduto);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rcvGrupo);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, R.drawable.divider));

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnAdicionarGrupo);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication().getApplicationContext(), AddEditGrupoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("TYPE_ACTIVITY", 0);
                bundle.putString("TITLE_ACTIVITY", "Novo grupo");
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
