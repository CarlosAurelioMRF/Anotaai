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
import br.com.carlosaurelio.anotaai.controller.UsuarioController;
import br.com.carlosaurelio.anotaai.model.Usuario;

public class AddEditUsuarioActivity extends AppCompatActivity {

    private static int TYPE_ACTIVITY;
    private static String TITLE_ACTIVITY;
    private EditText edtNome, edtUsuario, edtSenha;
    private RadioGroup rdgGroupUser;
    private Long idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_usuario);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TYPE_ACTIVITY = bundle.getInt("TYPE_ACTIVITY");
        TITLE_ACTIVITY = bundle.getString("TITLE_ACTIVITY");
        getSupportActionBar().setTitle(TITLE_ACTIVITY);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtUsuario = (EditText) findViewById(R.id.edtUserName);
        edtSenha = (EditText) findViewById(R.id.edtPassword);
        rdgGroupUser = (RadioGroup) findViewById(R.id.rdgGroupUser);

        if (TYPE_ACTIVITY == 1) {
            idUsuario = bundle.getLong("codigoUsuario");
            edtNome.setText(bundle.getString("nomeCompleto"));
            edtUsuario.setText(bundle.getString("nomeUsuario"));
            rdgGroupUser.check(bundle.getInt("tipoUsuario"));
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
            edtNome.setError("Obrigatório");
            edtNome.requestFocus();
        } else if (nomeUsuario.equals("")) {
            TextInputLayout lNomeUsuarioLayout = (TextInputLayout) findViewById(R.id.lNomeUsuarioLayout);
            lNomeUsuarioLayout.setErrorEnabled(true);
            lNomeUsuarioLayout.setError("Preencha o campo nome de usuário.");
            edtUsuario.setError("Obrigatório");
            edtUsuario.requestFocus();
        } else if (senha.equals("")) {
            TextInputLayout lPasswordLayout = (TextInputLayout) findViewById(R.id.lPasswordLayout);
            lPasswordLayout.setErrorEnabled(true);
            lPasswordLayout.setError("Preencha o campo senha.");
            edtSenha.setError("Obrigatório");
            edtSenha.requestFocus();
        } else {
            UsuarioController controller = new UsuarioController();

            // Inserindo
            if (TYPE_ACTIVITY == 0) {
                try {
                    Usuario usuario = new Usuario(nome, nomeUsuario, tipoUsuario, senha, dateNow, dateNow);

                    controller.inserirUsuario(usuario);
                    Toast.makeText(getApplicationContext(), usuario.getNomeCompleto() + " cadastrado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível inserir o usuário!").create().show();
                    e.printStackTrace();
                }
            } else {
                try {
                    Usuario usuario = controller.buscarUsuario(idUsuario);

                    usuario.setNomeCompleto(nome);
                    usuario.setNomeUsuario(nomeUsuario);
                    usuario.setTipoUsuario(tipoUsuario);
                    usuario.setSenhaUsuario(senha);
                    usuario.setUpdateDate(dateNow);

                    controller.atualizarUsuario(usuario);

                    Toast.makeText(getApplicationContext(), usuario.getNomeCompleto() + " editado com sucesso.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setPositiveButton("OK", null).setMessage("Não possível atualizar o usuário!").create().show();
                    e.printStackTrace();
                }
            }

            Intent intent = new Intent(AddEditUsuarioActivity.this, UsuariosActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

}
