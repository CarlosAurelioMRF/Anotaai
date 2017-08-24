package br.com.carlosaurelio.anotaai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;

public class AddEditUnidadeMedidaActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private static String TITLE_ACTIVITY;
    private EditText edtUN, edtDescricao;
    private int idUN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_unidade_medida);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TYPE_ACTIVITY = bundle.getInt("TYPE_ACTIVITY");
        TITLE_ACTIVITY = bundle.getString("TITLE_ACTIVITY");
        getSupportActionBar().setTitle(TITLE_ACTIVITY);

        edtUN = (EditText) findViewById(R.id.edtUN);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);

        if (TYPE_ACTIVITY == 1) {
            idUN = bundle.getInt("codigoUN");
            edtUN.setText(bundle.getString("unidadeMedida"));
            edtDescricao.setText(bundle.getString("descricao"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.btnSave) {
            salvarUnidadeMedida();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean someDataEntered() {
        return edtUN.getText().toString().length() > 0 ||
                edtDescricao.getText().toString().length() > 0;
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

    private void salvarUnidadeMedida() {
        String un = edtUN.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

        if (un.equals("")) {
            TextInputLayout lUnidadeMedida = (TextInputLayout) findViewById(R.id.lUnidadeMedida);
            lUnidadeMedida.setErrorEnabled(true);
            lUnidadeMedida.setError("Preencha o campo unidade medida.");
            edtUN.setError("Obrigatório");
            edtUN.requestFocus();
        } else if (descricao.equals("")) {
            TextInputLayout lDescricao = (TextInputLayout) findViewById(R.id.lDescricao);
            lDescricao.setErrorEnabled(true);
            lDescricao.setError("Preencha o campo descrição.");
            edtDescricao.setError("Obrigatório");
            edtDescricao.requestFocus();
        } else {
            ProdutoController controller = new ProdutoController(false);

            if (TYPE_ACTIVITY == 0) {
                try {
                    UnidadeMedida unidadeMedida = new UnidadeMedida(un, descricao, dateNow, dateNow);
                    controller.inserirUnidadeMedida(unidadeMedida);
                    Toast.makeText(getApplicationContext(), unidadeMedida.getUnidadeMedida() + " cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível inserir a unidade medida!").create().show();
                    e.printStackTrace();
                }
            } else {
                try {
                    UnidadeMedida unidadeMedida = new UnidadeMedida(idUN, un, descricao, dateNow);
                    controller.atualizarUnidadeMedida(unidadeMedida);
                    Toast.makeText(getApplicationContext(), unidadeMedida.getUnidadeMedida() + " editado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível atualizar a unidade medida!").create().show();
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(AddEditUnidadeMedidaActivity.this, UnidadeMedidaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
