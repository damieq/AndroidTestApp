package com.development.damian.testapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.development.damian.testapp.ApplicationState;
import com.development.damian.testapp.R;
import com.development.damian.testapp.utils.ViewDialog;

import org.json.JSONException;

public class DialogActivity extends Activity {

    Dialog mDialog;
    ApplicationState appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appState = (ApplicationState)getApplicationContext();

        ViewDialog alert = new ViewDialog();
        alert.showDialog(DialogActivity.this, "Error de conexión al servidor", appState);

       /* getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        View mView;
        mView = DialogActivity.this.getLayoutInflater().inflate(R.layout.activity_dialog, null);
        mDialog = new Dialog(DialogActivity.this);

        if (mDialog != null){
            mDialog.cancel();
        }

        mDialog = new Dialog(DialogActivity.this);
        mDialog.setContentView(mView);

        Button btnCloseDialog = (Button) mView.findViewById(R.id.btnCloseDialog);
        btnCloseDialog.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            mDialog.cancel();
                Intent intent = new Intent(DialogActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);

            }
        });

        mDialog.show();*/

    }
}
