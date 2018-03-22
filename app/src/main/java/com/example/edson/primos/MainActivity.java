package com.example.edson.primos;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etxt;
    Button boton;
    EditText txtv;
    SeekBar seek;
    Spinner spin;
    String colors[];

    ProgressDialog progressDialog;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    public static final String NOTIFICACION = "NOTIFICACION";
    public static final String NOTIFICACION2 = "NOTIFICACION";
    public static final String NOTIFICACION3 = "NOTIFICACION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etxt = findViewById(R.id.editText);
        boton = findViewById(R.id.button);
        seek=findViewById(R.id.seekBar);
        spin=findViewById(R.id.spinner);
        txtv = findViewById(R.id.editText);

        colors = new String[]{"rojo", "azul","negro", "amarillo"};
        addColor(colors);



        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int nummos = Integer.parseInt(etxt.getText().toString());

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMax(nummos);
                progressDialog.setMessage("buscando...");
                progressDialog.setTitle("Buscando Primos");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(false);
                progressDialog.show();
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                while (progressStatus <= progressDialog.getMax()) {
                                    try {
                                        Thread.sleep(200);
                                        progressStatus += 1;
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.setProgress(progressStatus);
                                        }
                                    });
                                }
                                progressDialog.dismiss();
                                progressStatus = 0;
                                progressDialog.setProgress(progressStatus);
                            }

                        }
                ).start();
                if (nummos > 0) {
                    for (int num = 2; num <= nummos; num++) {
                        int veces = 0;
                        for (int a = 1; a <= num; a++) {
                            if (num % a == 0) {
                                veces = veces + 1;
                            }
                        }
                        if (veces == 2) {

                            int icon = R.drawable.in_notification;
                            CharSequence titulo = "Notificacion de Primos";
                            CharSequence titubar = "Primo encontrado";
                            CharSequence txto = "Primo: "+num;

                            String txtnotifica = "Saludos desde \n" +num;
                            Intent i = new Intent(getApplicationContext(), secondActivity.class);
                            i.putExtra(NOTIFICACION, txtnotifica);
                            i.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                            PendingIntent p1 = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                            Notification notification = new Notification.Builder(getApplicationContext()).setTicker(titulo).setContentTitle(titubar).setContentText(txto)
                                    .setSmallIcon(icon).setAutoCancel(true).setContentIntent(p1).build();
                            notification.defaults |= Notification.DEFAULT_SOUND;
                            notification.defaults |= Notification.DEFAULT_VIBRATE;
                            notification.defaults |= Notification.DEFAULT_LIGHTS;

                            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            nm.notify(0, notification);
                        }
                    }
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.mcfly);
                        String not = txtv.getText().toString();
                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.bigText(not);
                        bigText.setSummaryText("Por: Edson");
                        NotificationCompat.Builder notification = new NotificationCompat.Builder(MainActivity.this, "").setContentTitle("Big Text Notification Example").setSmallIcon(R.drawable.in_notification)
                                .setLargeIcon(icon)
                                .setStyle(bigText);
                        notification.setDefaults(Notification.DEFAULT_ALL);
                        Intent i = new Intent(getApplicationContext(), secondActivity.class);
                        i.putExtra(NOTIFICACION, not);
                        i.putExtra(NOTIFICACION2,seekBar.getProgress());
                        i.putExtra(NOTIFICACION3,spin.getSelectedItem().toString());
                        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, i, 0);
                        notification.setContentIntent(pi);
                        int nId = 001;
                        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        nm.notify(nId, notification.build());


                    }
                });
            }
        });
    }

    private void addColor(String[] colors) {
        ArrayAdapter<String> spinrrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
        spinrrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(spinrrayAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
