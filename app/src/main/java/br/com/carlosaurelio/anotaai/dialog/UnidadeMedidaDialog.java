package br.com.carlosaurelio.anotaai.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.carlosaurelio.anotaai.R;
import br.com.carlosaurelio.anotaai.controller.ProdutoController;
import br.com.carlosaurelio.anotaai.model.UnidadeMedida;
import br.com.carlosaurelio.anotaai.other.MsgFunctions;

public class UnidadeMedidaDialog extends DialogFragment {

    private UnidadeMedida mUnidadeMedida;
    private EditText edtUN, edtDescricao;
    private TextInputLayout lUnidadeMedida, lDescricao;

    public UnidadeMedidaDialog(UnidadeMedida unidadeMedida) {
        this.mUnidadeMedida = unidadeMedida;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View alertLayout = getActivity().getLayoutInflater().
                inflate(R.layout.dialog_unidade_medida, null);

        edtUN = (EditText) alertLayout.findViewById(R.id.edtUN);
        edtDescricao = (EditText) alertLayout.findViewById(R.id.edtDescricao);
        lUnidadeMedida = (TextInputLayout) alertLayout.findViewById(R.id.lUnidadeMedida);
        lDescricao = (TextInputLayout) alertLayout.findViewById(R.id.lDescricao);

        if (mUnidadeMedida.getUnidadeMedida() != null)
            edtUN.setText(mUnidadeMedida.getUnidadeMedida());

        if (mUnidadeMedida.getDescricao() != null)
            edtDescricao.setText(mUnidadeMedida.getDescricao());

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(mUnidadeMedida.getId() == 0 ?
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
                                UnidadeMedidaDialog.this.getDialog().cancel();
                            }
                        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean canCloseDialog = false;
                String un = edtUN.getText().toString().trim();
                String descricao = edtDescricao.getText().toString().trim();

                Calendar calendar = Calendar.getInstance();
                String dateNow = new SimpleDateFormat("dd/MM/yyyy").format(calendar.getTime());

                if (un.equals("")) {
                    lUnidadeMedida.setErrorEnabled(true);
                    lUnidadeMedida.setError("Preencha o campo unidade medida.");
                    edtUN.setError(getString(R.string.required));
                    edtUN.requestFocus();
                } else if (descricao.equals("")) {
                    lDescricao.setErrorEnabled(true);
                    lDescricao.setError("Preencha o campo descrição.");
                    edtDescricao.setError(getString(R.string.required));
                    edtDescricao.requestFocus();
                } else {
                    ProdutoController controller = new ProdutoController(false);

                    try {
                        UnidadeMedida unidadeMedida = new UnidadeMedida(mUnidadeMedida.getId(), un, descricao, dateNow);

                        if (unidadeMedida.getId() == 0)
                            controller.inserirUnidadeMedida(unidadeMedida);
                        else
                            controller.atualizarUnidadeMedida(unidadeMedida);

                        canCloseDialog = true;

                        new MsgFunctions().toastSave(getActivity(), unidadeMedida.getUnidadeMedida());
                    } catch (Exception e) {
                        new MsgFunctions().errorMessage(getActivity(),
                                getString(R.string.string_unidade_de_medida),
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
