package br.com.carlosaurelio.anotaai.other;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import br.com.carlosaurelio.anotaai.R;

public class MsgFunctions {

    public static void errorMessage(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("OK", null)
                .setTitle(title)
                .setMessage(message + " " + title.toLowerCase())
                .create()
                .show();
    }

    public static void questionMessage(Context context, String title, String message, DialogInterface.OnClickListener posCallback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.positive_message), posCallback)
                .setNegativeButton(context.getString(R.string.negative_message), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }

    public static void toastSave(Context context, String message) {
        Toast.makeText(context, message + " " + context.getString(R.string.success_save), Toast.LENGTH_LONG).show();
    }

    public static void toastDelete(Context context, String message) {
        Toast.makeText(context, message + " " + context.getString(R.string.success_delete), Toast.LENGTH_LONG).show();
    }
}
