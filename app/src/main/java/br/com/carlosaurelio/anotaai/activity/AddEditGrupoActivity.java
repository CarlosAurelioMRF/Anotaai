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
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;

public class AddEditGrupoActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private static String TITLE_ACTIVITY;
    private EditText edtNomeGrupo;
    private RadioGroup rdgTipoGrupo;
    private Long idGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_grupo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TYPE_ACTIVITY = bundle.getInt("TYPE_ACTIVITY");
        TITLE_ACTIVITY = bundle.getString("TITLE_ACTIVITY");
        getSupportActionBar().setTitle(TITLE_ACTIVITY);

        edtNomeGrupo = (EditText) findViewById(R.id.edtNomeGrupo);
        rdgTipoGrupo = (RadioGroup) findViewById(R.id.rdgTipoGrupo);

        if (TYPE_ACTIVITY == 1) {
            idGrupo = bundle.getLong("codigoGrupo");
            edtNomeGrupo.setText(bundle.getString("nomeGrupo"));
            rdgTipoGrupo.check(bundle.getInt("tipoGrupo"));
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
            salvarGrupo();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean someDataEntered() {
        return edtNomeGrupo.getText().toString().length() > 0;
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

    private void salvarGrupo() {
        String nome = edtNomeGrupo.getText().toString().trim();
        int tipo = rdgTipoGrupo.getCheckedRadioButtonId();

        Calendar calendar = Calendar.getInstance();
        String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

        if (nome.equals("")) {
            TextInputLayout lNomeLayout = (TextInputLayout) findViewById(R.id.lNomeLayout);
            lNomeLayout.setErrorEnabled(true);
            lNomeLayout.setError("Preencha o campo nome.");
            edtNomeGrupo.setError("Obrigatório");
            edtNomeGrupo.requestFocus();
        } else {
            ProdutoController controller = new ProdutoController();

            if (TYPE_ACTIVITY == 0) {
                try {
                    GrupoProduto grupoProduto = new GrupoProduto(nome, tipo, dateNow, dateNow);

                    controller.inserirGrupo(grupoProduto);
                    Toast.makeText(getApplicationContext(), grupoProduto.getNomeGrupo() + " cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível inserir o grupo!").create().show();
                    e.printStackTrace();
                }
            } else {
                try {
                    GrupoProduto grupoProduto = controller.buscarGrupo(idGrupo);

                    grupoProduto.setNomeGrupo(nome);
                    grupoProduto.setTipoGrupo(tipo);
                    grupoProduto.setUpdateDate(dateNow);

                    controller.atualizarGrupo(grupoProduto);

                    Toast.makeText(getApplicationContext(), grupoProduto.getNomeGrupo() + " editado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível atualizar o grupo!").create().show();
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(AddEditGrupoActivity.this, GrupoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }

}
