package widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import com.shahcement.nirmaneyaami.R;

public class AppAlertDialog{

    private Context context;
    private AppAlertDialogListener appAlertDialogListerner;

    public void setAppAlertDialogListerner(AppAlertDialogListener listener){
        this.appAlertDialogListerner = listener;
    }

    public void didPressPositiveButton() {
        if (appAlertDialogListerner != null){
            appAlertDialogListerner.didPressPositiveButton();
        }
    }
    public AppAlertDialog(Context context){
        this.context = context;
    }

    public void showDialog(String title, String msg, String positiveButton, String negativeButton){

        final AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        didPressPositiveButton();
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.if_youtube)
                .show();
    }

}
