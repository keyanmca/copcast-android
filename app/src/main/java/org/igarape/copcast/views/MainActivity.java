package org.igarape.copcast.views;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.igarape.copcast.R;
import org.igarape.copcast.utils.ApiClient;
import org.igarape.copcast.utils.Globals;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((TextView)findViewById(R.id.userName)).setText(Globals.getUserName());
        ((TextView)findViewById(R.id.userLogin)).setText(Globals.getUserLogin(this));

        final ImageView userImage = (ImageView) findViewById(R.id.userImage);
        ApiClient.get("/pictures/small/show", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Bitmap bm = BitmapFactory.decodeByteArray(responseBody, 0, responseBody.length);
                Globals.setUserImage(bm);
                userImage.setImageBitmap(bm);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                userImage.setVisibility(View.GONE);
            }
        });

        final Button starMissionButton = (Button) findViewById(R.id.startMissionButton);
        starMissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                starMissionButton.setEnabled(false);
                starMissionButton.setText(getString(R.string.recording_started));

                findViewById(R.id.settingsLayout).setVisibility(View.VISIBLE);
            }
        });

        ((Switch)findViewById(R.id.StartUploadingSwitch)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    findViewById(R.id.uploadLayout).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.uploadLayout).setVisibility(View.INVISIBLE);
                }
            }
        });
        findViewById(R.id.loggoutButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Globals.clear(MainActivity.this);
                ApiClient.setToken(null);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                MainActivity.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Globals.clear(MainActivity.this);
        ApiClient.setToken(null);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
