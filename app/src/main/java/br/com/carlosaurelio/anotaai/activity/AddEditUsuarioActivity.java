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
import br.com.carlosaurelio.anotaai.controller.UsuarioController;
import br.com.carlosaurelio.anotaai.model.Usuario;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;

public class AddEditUsuarioActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private EditText edtNome, edtUsuario, edtSenha;
    private RadioGroup rdgGroupUser;
    private int idUsuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtUsuario = (EditText) findViewById(R.id.edtUserName);
        edtSenha = (EditText) findViewById(R.id.edtPassword);
        rdgGroupUser = (RadioGroup) findViewById(R.id.rdgGroupUser);

        TYPE_ACTIVITY = getIntent().getIntExtra("TYPE_ACTIVITY", 0);

        switch (TYPE_ACTIVITY) {
            case 0:
                getSupportActionBar().setTitle(getString(R.string.insert));
            case 1:
                getSupportActionBar().setTitle(getString(R.string.edit));

                idUsuario = getIntent().getIntExtra("codigoUsuario", 0);
                edtNome.setText(getIntent().getStringExtra("nomeCompleto"));
                edtUsuario.setText(getIntent().getStringExtra("nomeUsuario"));
                rdgGroupUser.check(getIntent().getIntExtra("tipoUsuario", 0));
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
            salvarUsuario();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (someDataEntered()) {
            new MsgFunctions().questionMessage(this, getString(R.string.title_activity_usuarios),
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

    private boolean someDataEntered() {
        if (edtNome.getText().toString().length() > 0 ||
                edtUsuario.getText().toString().length() > 0 ||
                edtSenha.getText().toString().length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void salvarUsuario() {
        String nome = edtNome.getText().toString().trim();
        String nomeUsuario = edtUsuario.getText().toString().trim();
        String senha = edtSenha.getText().toString().trim();
        int tipoUsuario = rdgGroupUser.getCheckedRadioButtonId();

        Calendar calendar = Calendar.getInstance();
        String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

        if (nome.equals("")) {
            TextInputLayout lNomeLayout = (TextInputLayout) findViewById(R.id.lNomeLayout);
            lNomeLayout.setErrorEnabled(true);
            lNomeLayout.setError("Preencha o campo nome completo.");
            edtNome.setError(getString(R.string.required));
            edtNome.requestFocus();
        } else if (nomeUsuario.equals("")) {
            TextInputLayout lNomeUsuarioLayout = (TextInputLayout) findViewById(R.id.lNomeUsuarioLayout);
            lNomeUsuarioLayout.setErrorEnabled(true);
            lNomeUsuarioLayout.setError("Preencha o campo nome de usuário.");
            edtUsuario.setError(getString(R.string.required));
            edtUsuario.requestFocus();
        } else if (senha.equals("")) {
            TextInputLayout lPasswordLayout = (TextInputLayout) findViewById(R.id.lPasswordLayout);
            lPasswordLayout.setErrorEnabled(true);
            lPasswordLayout.setError("Preencha o campo senha.");
            edtSenha.setError(getString(R.string.required));
            edtSenha.requestFocus();
        } else {
            UsuarioController controller = new UsuarioController(false);

            try {
                Usuario usuario = new Usuario(idUsuario, nome, nomeUsuario, tipoUsuario, senha, dateNow);

                if (TYPE_ACTIVITY == 0)
                    controller.inserirUsuario(usuario);
                else
                    controller.atualizarUsuario(usuario);

                new MsgFunctions().toastSave(this, usuario.getNomeCompleto());
            } catch (Exception e) {
                new MsgFunctions().errorMessage(this,
                        getString(R.string.title_activity_usuarios),
                        getString(R.string.error_save));
                e.printStackTrace();
            }

            Intent intent = new Intent(AddEditUsuarioActivity.this, UsuariosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}
