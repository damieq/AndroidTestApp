package com.development.damian.testapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.development.damian.testapp.Activities.MainActivity;
import com.development.damian.testapp.ApplicationState;
import com.development.damian.testapp.R;

/**
 * Created by damia on 17/04/2017.
 */

public class ViewDialog {

    public void showDialog(final Activity activity, String msg, final ApplicationState appState){

        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        View mView = activity.getLayoutInflater().inflate(R.layout.activity_dialog, null);

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(mView);

        /*TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);*/

        Button btnCloseDialog = (Button) dialog.findViewById(R.id.btnCloseDialog);
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                appState.setLockScreenDialogOpen(false);
                Intent intent = new Intent(activity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                activity.startActivity(intent);
            }
        });

        Button btnOpenApp = (Button) dialog.findViewById(R.id.btnOpenApp);
        btnOpenApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                appState.setLockScreenDialogOpen(false);
                Intent intent = new Intent(activity, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(intent);
            }
        });

        dialog.show();

        appState.setLockScreenDialogOpen(true);
    }
}
