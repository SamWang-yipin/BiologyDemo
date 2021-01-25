package com.sam.biologydemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DialogUtility {
    public static void showSingleButtonDialog(final Context context, String message, String positiveText, DialogInterface.OnClickListener listener)
    {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("提示");
        alertDialog.setMessage(message);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, positiveText, listener);
        alertDialog.show();
    }

    public static AlertDialog showSingleChoiceDialog(Context context, int defaultItem, int titleResourceId, String[] items, String positiveTextResourceId, String negativeTextResourceId, DialogInterface.OnClickListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleResourceId);
        builder.setSingleChoiceItems(items, defaultItem, listener);
        builder.setPositiveButton(positiveTextResourceId, listener);
        builder.setNegativeButton(negativeTextResourceId, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }
}
