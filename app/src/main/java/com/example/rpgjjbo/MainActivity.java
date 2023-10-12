package com.example.rpgjjbo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int clase = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        Button btnInicio = findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseClass();
            }
        });
    }

    // ELEGIR CLASE
    private void chooseClass() {
        setContentView(R.layout.elegir_clase);
        Button btnClaseContinuar = findViewById(R.id.btnClaseContinuar);
        btnClaseContinuar.setOnClickListener((view) -> {
            RadioGroup radioGroupClase = findViewById(R.id.radioGroupClase);
            int claseSeleccionada = radioGroupClase.getCheckedRadioButtonId();
            if (claseSeleccionada == -1) {
                muestraToast(getString(R.string.debes_elegir_una_clase));
                return;
            } else if (claseSeleccionada == R.id.radioButtonHumano) {
                this.clase = 0;
            } else if (claseSeleccionada == R.id.radioButtonElfo) {
                this.clase = 1;
            }else if (claseSeleccionada == R.id.radioButtonEnano) {
                this.clase = 2;
            } else if (claseSeleccionada == R.id.radioButtonOrco) {
                this.clase = 3;
            }
            statsStart();
        });
    }

    private void statsStart() {
    }

    private void muestraToast(String msg) {
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        t.show();
    }
}