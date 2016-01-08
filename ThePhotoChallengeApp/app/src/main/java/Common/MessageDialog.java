package Common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MessageDialog  {
    private AlertDialog dialog;

    public MessageDialog(Activity activity){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });

        dialog = dialogBuilder.create();
    }

    public void show(){
        dialog.show();
    }

    public void setTitle(String title){
        dialog.setTitle(title);
    }

    public void setMessage(String message){
        dialog.setMessage(message);
    }


}
