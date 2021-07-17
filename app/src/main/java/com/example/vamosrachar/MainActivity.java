package com.example.vamosrachar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener, TextToSpeech.OnInitListener {

    EditText etValor;
    EditText etQPessoas;
    TextView tvResultado;
    FloatingActionButton share, tts;
    TextToSpeech ttsPlayer;

    double valor1 = 0;
    int valor2 = 2;
    double resultado = 0;
    String resFormatado = "0,00";
    DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etValor = (EditText) findViewById(R.id.editTextValor);
        etQPessoas = (EditText) findViewById(R.id.editTextQdePessoas);
        tvResultado = (TextView) findViewById(R.id.textTotal);

        tvResultado.setText(getString(R.string.r) +" 0,00");

        etValor.addTextChangedListener(this);
        etQPessoas.addTextChangedListener(this);

        share = (FloatingActionButton) findViewById(R.id.floatingActionButtonsCompartilhar);
        tts = (FloatingActionButton) findViewById(R.id.floatingActionButtonTTS);

        share.setOnClickListener(this);
        tts.setOnClickListener(this);

        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, 1122);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1122){
            ttsPlayer = new TextToSpeech(this, this);
        }
        else{
            Intent installTTSIntent = new Intent();
            installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
            startActivity(installTTSIntent);
        }
    }

    @Override
    public void onInit(int initStatus){
        if(initStatus == TextToSpeech.SUCCESS){
            Toast.makeText(this, "tts ativado...", Toast.LENGTH_LONG).show();
        }
        else if(initStatus == TextToSpeech.ERROR){
            Toast.makeText(this, "tts não habilitado...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        try{
            valor1 = Double.parseDouble(etValor.getText().toString());
            valor2 = Integer.parseInt(etQPessoas.getText().toString());

            tvResultado.setText( getString(R.string.r) + " " + df.format(valor1/valor2));
        }
        catch(Exception e){
            tvResultado.setText(getString(R.string.r) +" 0,00");
        }
    }


    @Override
    public void onClick(View v) {
        if(v == share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.O_valor_por_pessoa_é_de) + tvResultado.getText().toString());
            startActivity(intent);
        }
        if(v == tts){
            if(tts!=null){
                ttsPlayer.speak(getString(R.string.O_valor_por_pessoa_é_de) + " " + tvResultado.getText().toString(), TextToSpeech.QUEUE_FLUSH,null,"ID1");
            }
        }
    }

}