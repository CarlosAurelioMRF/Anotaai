package br.com.carlosaurelio.anotaai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;

public class AddEditGrupoActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private EditText edtNomeGrupo;
    private RadioGroup rdgTipoGrupo;
    private int idGrupo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_grupo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNomeGrupo = (EditText) findViewById(R.id.edtNomeGrupo);
        rdgTipoGrupo = (RadioGroup) findViewById(R.id.rdgTipoGrupo);

        TYPE_ACTIVITY = getIntent().getIntExtra("TYPE_ACTIVITY", 0);

        switch (TYPE_ACTIVITY) {
            case 0:
                getSupportActionBar().setTitle(getString(R.string.insert));
            case 1:
                getSupportActionBar().setTitle(getString(R.string.edit));

                idGrupo = getIntent().getIntExtra("codigoGrupo", 0);
                edtNomeGrupo.setText(getIntent().getStringExtra("nomeGrupo"));
                rdgTipoGrupo.check(getIntent().getIntExtra("tipoGrupo", 0));
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
            new MsgFunctions().questionMessage(this, getString(R.string.string_grupos),
                    getString(R.string.dont_save_data),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            supportFinishAfterTransition();
                        }
                    });
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
            edtNomeGrupo.setError(getString(R.string.required));
            edtNomeGrupo.requestFocus();
        } else {
            ProdutoController controller = new ProdutoController(false);

            try {
                GrupoProduto grupoProduto = new GrupoProduto(idGrupo, nome, tipo, dateNow);

                if (TYPE_ACTIVITY == 0)
                    controller.inserirGrupo(grupoProduto);
                else
                    controller.atualizarGrupo(grupoProduto);

                new MsgFunctions().toastSave(this, grupoProduto.getNomeGrupo());
            } catch (Exception e) {
                new MsgFunctions().errorMessage(this,
                        getString(R.string.string_grupos),
                        getString(R.string.error_save));
                e.printStackTrace();
            }

            Intent intent = new Intent(AddEditGrupoActivity.this, GrupoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
    }

}
