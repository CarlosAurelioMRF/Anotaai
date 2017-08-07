package br.com.carlosaurelio.anotaai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.Produto;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import fr.ganfra.materialspinner.MaterialSpinner;

public class AddEditProdutoActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private static String TITLE_ACTIVITY;
    private Long idProduto;
    private String nomeProduto, codigoExterno;
    private int codigoUN, codigoGrupo;
    private Double precoVenda;

    private EditText edtNomeProduto, edtCodigoExterno, edtPrecoVenda;
    private MaterialSpinner spnUnidadeMedida, spnGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_produto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TYPE_ACTIVITY = bundle.getInt("TYPE_ACTIVITY");
        TITLE_ACTIVITY = bundle.getString("TITLE_ACTIVITY");
        getSupportActionBar().setTitle(TITLE_ACTIVITY);

        edtNomeProduto = (EditText) findViewById(R.id.edtNomeProduto);
        edtCodigoExterno = (EditText) findViewById(R.id.edtCodigoExterno);
        spnUnidadeMedida = (MaterialSpinner) findViewById(R.id.spnUnidadeMedida);
//        spnGrupo = (Spinner) findViewById(R.id.spnGrupo);
        edtPrecoVenda = (EditText) findViewById(R.id.edtPrecoVenda);

        ProdutoController controller = new ProdutoController();
        List<UnidadeMedida> unidadeMedidas = controller.listarUnidadeMedidas();

        List<String> stringList = new ArrayList<>(unidadeMedidas.size());
        for (UnidadeMedida unidadeMedida : unidadeMedidas) {
            stringList.add(unidadeMedida != null ? unidadeMedida.getUnidadeMedida() : null);
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, stringList);

        spnUnidadeMedida.setAdapter(dataAdapter);

        if (TYPE_ACTIVITY == 1) {
            idProduto = bundle.getLong("codigoProduto");
            nomeProduto = bundle.getString("nomeProduto");
            codigoExterno = bundle.getString("codigoExterno");
            codigoUN = bundle.getInt("codigoUN");
            codigoGrupo = bundle.getInt("codigoGrupo");
            precoVenda = bundle.getDouble("precoVenda");
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnSave) {
            salvarProduto();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean someDataEntered() {
        return edtNomeProduto.getText().toString().length() > 0 ||
//                spnUnidadeMedida.get;
//                spnGrupo
                edtPrecoVenda.getText().toString().length() > 0;

    }

    @Override
    public void onBackPressed() {
        if (someDataEntered()) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Alerta").setMessage("Alterações não foram salvas deseja sair assim mesmo?");
            alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    supportFinishAfterTransition();
                    dialog.cancel();
                }
            });

            alert.setNegativeButton("Não", null).create().show();

        } else {
            super.onBackPressed();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void salvarProduto() {
        String nome = edtNomeProduto.getText().toString().trim();
        String codigoExterno = edtCodigoExterno.getText().toString().trim();
        int codigoUnidadeMedida = 0;// PEGAR VALOR DO SPINNER UNIDADE MEDIDA
        int codigoGrupo = 0;// PEGAR VALOR DO SPINNER GRUPO
        Double precoVenda = Double.parseDouble(edtPrecoVenda.getText().toString().trim());

        Calendar calendar = Calendar.getInstance();
        String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

        if (nome.equals("")) {
            TextInputLayout lNomeProduto = (TextInputLayout) findViewById(R.id.lNomeProduto);
            lNomeProduto.setErrorEnabled(true);
            lNomeProduto.setError("Preencha o campo nome.");
            edtNomeProduto.setError("Obrigatório");
            edtNomeProduto.requestFocus();
        } else if (precoVenda == 0.0) {
            TextInputLayout lPrecoVenda = (TextInputLayout) findViewById(R.id.lPrecoVenda);
            lPrecoVenda.setErrorEnabled(true);
            lPrecoVenda.setError("Preencha o campo preço de venda.");
            edtPrecoVenda.setError("Obrigatório");
            edtPrecoVenda.requestFocus();
        } else {
            ProdutoController controller = new ProdutoController();

            if (TYPE_ACTIVITY == 0) {
                try {
                    Produto produto = new Produto(nome, codigoExterno, codigoUnidadeMedida, codigoGrupo, precoVenda, dateNow, dateNow);

                    controller.inserirProduto(produto);
                    Toast.makeText(getApplicationContext(), produto.getNomeProduto() + " cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível inserir o produto!").create().show();
                    e.printStackTrace();
                }
            } else {
                try {
                    Produto produto = controller.buscarProduto(idProduto);

                    produto.setNomeProduto(nome);
                    produto.setCodigoExterno(codigoExterno);
                    produto.setIdUnidadeMedida(codigoUnidadeMedida);
                    produto.setIdGrupo(codigoGrupo);
                    produto.setPrecoVenda(precoVenda);
                    produto.setUpdateDate(dateNow);

                    controller.atualizarProduto(produto);

                    Toast.makeText(getApplicationContext(), produto.getNomeProduto() + " editado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível atualizar o produto!").create().show();
                    e.printStackTrace();
                }
            }

            finish();
        }
    }

}
