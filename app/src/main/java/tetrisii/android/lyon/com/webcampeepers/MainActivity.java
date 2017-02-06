package tetrisii.android.lyon.com.webcampeepers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button open_cam,open_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        open_cam = (Button)findViewById(R.id.open_cam);
        open_screen = (Button)findViewById(R.id.open_screen);

        open_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,StreamCameraActivity.class);
                startActivity(i);
            }
        });


        open_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder editDialog = new AlertDialog.Builder(MainActivity.this);
                editDialog.setTitle("--- 請輸入網址 ---");

                final EditText editText = new EditText(MainActivity.this);
                editText.setText("http://192.168.100.50:8080/");
                editText.setHint("http://.........");
                editDialog.setView(editText);

                editDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        if(!editText.getText().toString().equals("") ) {
                            Intent i = new Intent(MainActivity.this, ScreenWeb.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("URL", editText.getText().toString());
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }
                });
                editDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
                editDialog.show();
            }
        });
    }
}
