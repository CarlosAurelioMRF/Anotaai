package br.com.carlosaurelio.anotaai.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.GrupoProduto;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;

public class GrupoDialog extends DialogFragment {

    private GrupoProduto mGrupoProduto;
    private Spinner spnTipoGrupo;
    private EditText edtNomeGrupo;
    private TextInputLayout lNomeLayout;

    public GrupoDialog(GrupoProduto grupoProduto) {
        this.mGrupoProduto = grupoProduto;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View alertLayout = getActivity().getLayoutInflater().
                inflate(R.layout.layout_custom_dialog, null);

        spnTipoGrupo = (Spinner) alertLayout.findViewById(R.id.spnTipoGrupo);
        edtNomeGrupo = (EditText) alertLayout.findViewById(R.id.edtNomeGrupo);
        lNomeLayout = (TextInputLayout) alertLayout.findViewById(R.id.lNomeLayout);

        if (mGrupoProduto.getNomeGrupo() != null)
            edtNomeGrupo.setText(mGrupoProduto.getNomeGrupo());

        if (mGrupoProduto.getTipoGrupo() > -1)
            spnTipoGrupo.setSelection(mGrupoProduto.getTipoGrupo());

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(mGrupoProduto.getId() == 0 ?
                getActivity().getString(R.string.insert) :
                getActivity().getString(R.string.edit))
                .setView(alertLayout)
                .setCancelable(false)
                .setPositiveButton(getActivity().getString(R.string.string_salvar),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                .setNegativeButton(getActivity().getString(R.string.cancel_message),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                GrupoDialog.this.getDialog().cancel();
                            }
                        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canCloseDialog = false;
                String nome = edtNomeGrupo.getText().toString().trim();
                int tipo = spnTipoGrupo.getSelectedItemPosition();

                Calendar calendar = Calendar.getInstance();
                String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                if (nome.equals("")) {
                    lNomeLayout.setErrorEnabled(true);
                    lNomeLayout.setError("Preencha o campo nome.");
                    edtNomeGrupo.setError(getString(R.string.required));
                    edtNomeGrupo.requestFocus();
                } else {
                    ProdutoController controller = new ProdutoController(false);

                    try {
                        if (mGrupoProduto.getId() == 0){
                            GrupoProduto grupoProduto = new GrupoProduto(nome, tipo, dateNow, dateNow);
                            controller.inserirGrupo(grupoProduto);
                        }
                        else {
                            GrupoProduto grupoProduto = new GrupoProduto(mGrupoProduto.getId(), nome, tipo, dateNow);
                            controller.atualizarGrupo(grupoProduto);
                        }

                        canCloseDialog = true;

                        new MsgFunctions().toastSave(getActivity(), nome);
                    } catch (Exception e) {
                        new MsgFunctions().errorMessage(getActivity(),
                                getString(R.string.string_grupos),
                                getString(R.string.error_save));
                        e.printStackTrace();
                    }
                }

                if (canCloseDialog)
                    dialog.dismiss();
            }
        });

        return dialog;
    }
}
