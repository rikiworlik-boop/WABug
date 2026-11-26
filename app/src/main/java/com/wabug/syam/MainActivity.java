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
    EditText inputSender, inputTarget;
    Button btnLogin, btnAttack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);
        layout.setBackgroundColor(Color.parseColor("#0B0914"));

        TextView title1 = new TextView(this);
        title1.setText("1. TAUTKAN SENDER KOSONG");
        title1.setTextColor(Color.parseColor("#8B5CF6"));
        title1.setTextSize(20);
        layout.addView(title1);

        inputSender = new EditText(this);
        inputSender.setHint("Nomor Kosong (cth: 628xxx)");
        inputSender.setTextColor(Color.WHITE);
        inputSender.setBackgroundColor(Color.parseColor("#2A1B4D"));
        layout.addView(inputSender);

        btnLogin = new Button(this);
        btnLogin.setText("DAPATKAN KODE PAIRING");
        layout.addView(btnLogin);

        TextView title2 = new TextView(this);
        title2.setText("\n2. EKSEKUSI BUG");
        title2.setTextColor(Color.parseColor("#8B5CF6"));
        title2.setTextSize(20);
        layout.addView(title2);

        inputTarget = new EditText(this);
        inputTarget.setHint("Nomor Target");
        inputTarget.setTextColor(Color.WHITE);
        inputTarget.setBackgroundColor(Color.parseColor("#2A1B4D"));
        layout.addView(inputTarget);

        btnAttack = new Button(this);
        btnAttack.setText("TEMBAK BUG");
        layout.addView(btnAttack);

        setContentView(layout);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sender = inputSender.getText().toString().trim();
                if(sender.isEmpty()) return;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("http://127.0.0.1:3000/api/login");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/json; utf-8");
                            conn.setDoOutput(true);
                            JSONObject json = new JSONObject();
                            json.put("phone", sender);
                            OutputStream os = conn.getOutputStream();
                            os.write(json.toString().getBytes("UTF-8"));
                            os.close();
                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Cek layar Termux untuk kode pairing!", Toast.LENGTH_LONG).show();
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
