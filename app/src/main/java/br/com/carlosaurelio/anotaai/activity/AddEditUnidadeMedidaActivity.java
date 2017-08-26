package br.com.carlosaurelio.anotaai.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;

public class AddEditUnidadeMedidaActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private EditText edtUN, edtDescricao;
    private int idUN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_unidade_medida);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtUN = (EditText) findViewById(R.id.edtUN);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);

        TYPE_ACTIVITY = getIntent().getIntExtra("TYPE_ACTIVITY", 0);

        switch (TYPE_ACTIVITY) {
            case 0:
                getSupportActionBar().setTitle(getString(R.string.insert));
            case 1:
                getSupportActionBar().setTitle(getString(R.string.edit));

                idUN = getIntent().getIntExtra("codigoUN", 0);
                edtUN.setText(getIntent().getStringExtra("unidadeMedida"));
                edtDescricao.setText(getIntent().getStringExtra("descricao"));
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
            new MsgFunctions().questionMessage(this, getString(R.string.string_unidade_de_medida),
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

    private void salvarUnidadeMedida() {
        String un = edtUN.getText().toString().trim();
        String descricao = edtDescricao.getText().toString().trim();

        Calendar calendar = Calendar.getInstance();
        String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

        if (un.equals("")) {
            TextInputLayout lUnidadeMedida = (TextInputLayout) findViewById(R.id.lUnidadeMedida);
            lUnidadeMedida.setErrorEnabled(true);
            lUnidadeMedida.setError("Preencha o campo unidade medida.");
            edtUN.setError(getString(R.string.required));
            edtUN.requestFocus();
        } else if (descricao.equals("")) {
            TextInputLayout lDescricao = (TextInputLayout) findViewById(R.id.lDescricao);
            lDescricao.setErrorEnabled(true);
            lDescricao.setError("Preencha o campo descrição.");
            edtDescricao.setError(getString(R.string.required));
            edtDescricao.requestFocus();
        } else {
            ProdutoController controller = new ProdutoController(false);

            try {
                UnidadeMedida unidadeMedida = new UnidadeMedida(idUN, un, descricao, dateNow);

                if (TYPE_ACTIVITY == 0)
                    controller.inserirUnidadeMedida(unidadeMedida);
                else
                    controller.atualizarUnidadeMedida(unidadeMedida);

                new MsgFunctions().toastSave(this, unidadeMedida.getUnidadeMedida());
            } catch (Exception e) {
                new MsgFunctions().errorMessage(this,
                        getString(R.string.string_unidade_de_medida),
                        getString(R.string.error_save));
                e.printStackTrace();
            }

            Intent intent = new Intent(AddEditUnidadeMedidaActivity.this, UnidadeMedidaActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }
}
