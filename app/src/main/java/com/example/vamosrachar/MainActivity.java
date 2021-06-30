package com.example.vamosrachar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements TextWatcher {

    EditText etValor;
    EditText etQPessoas;
    TextView tvResultado;

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

        tvResultado.setText("R$ 0,00");

        etValor.addTextChangedListener(this);
        etQPessoas.addTextChangedListener(this);
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

            tvResultado.setText("R$ " + df.format(valor1/valor2));
        }
        catch(Exception e){
            tvResultado.setText("R$ 0,00");
        }
    }


}