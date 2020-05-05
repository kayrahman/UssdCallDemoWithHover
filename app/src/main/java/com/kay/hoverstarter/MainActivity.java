package com.kay.hoverstarter;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hover.sdk.actions.HoverAction;
import com.hover.sdk.api.Hover;
import com.hover.sdk.api.HoverParameters;
import com.hover.sdk.permissions.PermissionActivity;
import com.kay.hoverstarter.broadcastReceiver.MessageReceiver;
import com.kay.hoverstarter.listeners.MessageListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Hover.DownloadListener, MessageListener {
	private final String TAG = "MainActivity";

	TextView smsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hover.initialize(getApplicationContext(), this);

	//	Hover.initialize(this);

        Button permissionsButton = findViewById(R.id.permissions_button);
        permissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), PermissionActivity.class);
                startActivityForResult(i, 0);
            }
        });

        smsTv = findViewById(R.id.sms_txt);
	    Button button= (Button) findViewById(R.id.action_button);
	    button.setEnabled(true);
	    button.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    Intent i = new HoverParameters.Builder(MainActivity.this)
					//.request("2e46abd1") // balance check
					.request("0b2f4701") // remaining minutes check
                    .extra("Check Remaining Minute", "*121*1*2#") // Uncomment and add your variables if any
					.buildIntent();
			    startActivityForResult(i, 0);
		    }
	    });


		MessageReceiver.bindListener(this);

    }

	@Override public void onError(String message) {
//		Toast.makeText(this, "Error while attempting to download actions, see logcat for error", Toast.LENGTH_LONG).show();
		Log.e(TAG, "Error: " + message);
	}

	@Override public void onSuccess(ArrayList<HoverAction> actions) {
//		Toast.makeText(this, "Successfully downloaded " + actions.size() + " actions", Toast.LENGTH_LONG).show();
		Log.d(TAG, "Successfully downloaded " + actions.size() + " actions");
	}

	@Override
	public void messageReceived(String message) {
		Log.d("sms",message);
		smsTv.setText(message);
	}
}
