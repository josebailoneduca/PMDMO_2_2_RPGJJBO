package com.example.rpgjjbo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

   private Personaje personaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        Button btnInicio = findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(view -> {
                this.personaje = new Personaje();
                elijeClase();
        });
    }

    // ELEGIR CLASE
    private void elijeClase() {
        setContentView(R.layout.elegir_clase);
        Button btnClaseContinuar = findViewById(R.id.btnClaseContinuar);
        RadioGroup radioGroupClase = findViewById(R.id.radioGroupClase);
        RadioGroup radioGroupGenero = findViewById(R.id.radioGroupGenero);

        radioGroupClase.clearCheck();
        radioGroupGenero.clearCheck();

        radioGroupClase.setOnCheckedChangeListener((radioGroup, i) -> {
            if (radioGroupClase.getCheckedRadioButtonId()!=-1&&radioGroupGenero.getCheckedRadioButtonId()!=-1)
                btnClaseContinuar.setEnabled(true);
        });
        radioGroupGenero.setOnCheckedChangeListener((radioGroup, i) -> {
            if (radioGroupClase.getCheckedRadioButtonId()!=-1&&radioGroupGenero.getCheckedRadioButtonId()!=-1)
                btnClaseContinuar.setEnabled(true);
        });

        btnClaseContinuar.setOnClickListener((view) -> {
            int claseSeleccionada = radioGroupClase.getCheckedRadioButtonId();
            if (claseSeleccionada == -1) {
                muestraToast(getString(R.string.debes_elegir_una_clase));
                return;
            } else if (claseSeleccionada == R.id.radioButtonHumano) {
                this.personaje.setClase(EnumClassType.humano);
            } else if (claseSeleccionada == R.id.radioButtonElfo) {
                this.personaje.setClase(EnumClassType.elfo);
            }else if (claseSeleccionada == R.id.radioButtonEnano) {
                this.personaje.setClase(EnumClassType.enano);
            } else if (claseSeleccionada == R.id.radioButtonOrco) {
                this.personaje.setClase(EnumClassType.orco);
            }
            int generoSeleccionado = radioGroupGenero.getCheckedRadioButtonId();
            if (generoSeleccionado == -1) {
                muestraToast(getString(R.string.debes_elegir_un_genero));
                return;
            } else if (generoSeleccionado == R.id.radioButtonMasculino) {
                this.personaje.setGenero(EnumGender.masculino);
            } else if (generoSeleccionado == R.id.radioButtonFemenino) {
                this.personaje.setGenero(EnumGender.femenino);
            }

            statsAleatoria();
        });
    }

    private void statsAleatoria() {
        setContentView(R.layout.asignar_stats_random);
        Button btnLanzarDados = findViewById(R.id.btnLanzarDados);
        Button btnContinuar =findViewById(R.id.btnStatsRandomContinuar);
        TextView lbValTiradas = findViewById(R.id.lbValTiradas);
        actualizarEtiquetasStats();
        lbValTiradas.setText(""+personaje.getIntentosStatsAzar());
        btnLanzarDados.setOnClickListener(view -> {
            int tiradasRestantes = this.personaje.randomStats();
            actualizarEtiquetasStats();
            lbValTiradas.setText(""+personaje.getIntentosStatsAzar());
            if (tiradasRestantes==0)
                btnLanzarDados.setEnabled(false);
            btnContinuar.setEnabled(true);
        });
        
        btnContinuar.setOnClickListener(view -> statsManual());
    }

    private void actualizarEtiquetasStats() {
        TextView lbValSalud = findViewById(R.id.lbValSalud);
        TextView lbValAtaqueFisico = findViewById(R.id.lbValAtaqueFisico);
        TextView lbValAtaqueMagico = findViewById(R.id.lbValAtaqueMagico);
        TextView lbValDefensaFisica = findViewById(R.id.lbValDefensaFisica);
        TextView lbValDefensaMagica = findViewById(R.id.lbValDefensaMagica);
        TextView lbValPunteria = findViewById(R.id.lbValPunteria);
        lbValSalud.setText(""+personaje.getStat_salud());
        lbValAtaqueFisico.setText(""+personaje.getStat_ataque_fisico());
        lbValAtaqueMagico.setText(""+personaje.getStat_ataque_magico());
        lbValDefensaFisica.setText(""+personaje.getStat_defensa_fisica());
        lbValDefensaMagica.setText(""+personaje.getStat_defensa_magica());
        lbValPunteria.setText(""+personaje.getStat_punteria());

    }

    private void statsManual() {
        setContentView(R.layout.asignar_stats_manual);
        Button btnContinuar =findViewById(R.id.btnStatsManualContinuar);
        btnContinuar.setEnabled(false);
        ImageButton btnStatSalud= findViewById(R.id.btnStatSalud);
        ImageButton btnStatAtaqueFisico= findViewById(R.id.btnStatAtaqueFisico);
        ImageButton btnStatAtaqueMagico= findViewById(R.id.btnStatAtaqueMagico);
        ImageButton btnStatDefensaFisica= findViewById(R.id.btnStatDefensaFisica);
        ImageButton btnStatDefensaMagica= findViewById(R.id.btnStatDefensaMagica);
        ImageButton btnStatPunteria= findViewById(R.id.btnStatPunteria);
        actualizarEtiquetasStats();
        actualizarBarrasStats();
        ImageButton[] botonesManuales=new ImageButton[]{btnStatSalud,btnStatAtaqueFisico,btnStatAtaqueMagico,btnStatDefensaFisica,btnStatDefensaMagica,btnStatPunteria};
        //eventos
        btnStatSalud.setOnClickListener(view -> {
            aumentaStatManual(personaje.aumentaSalud(),view,botonesManuales);
        });
        btnStatAtaqueFisico.setOnClickListener(view -> {
            aumentaStatManual(personaje.aumentaAtaqueFisico(),view,botonesManuales);
        });
        btnStatAtaqueMagico.setOnClickListener(view -> {
            aumentaStatManual(personaje.aumentaAtaqueMagico(),view,botonesManuales);
        });
        btnStatDefensaFisica.setOnClickListener(view -> {
            aumentaStatManual(personaje.aumentaDefensaFisica(),view,botonesManuales);
        });
        btnStatDefensaMagica.setOnClickListener(view -> {
            aumentaStatManual(personaje.aumentaDefensaMagica(),view,botonesManuales);
        });
        btnStatPunteria.setOnClickListener(view -> {
            aumentaStatManual(personaje.aumentaPunteria(),view,botonesManuales);
        });
    }

    private void aumentaStatManual(boolean exito, View view, ImageButton[] botonesManuales) {
        if(!exito){
            view.setEnabled(false);
            ((ImageButton)view).setColorFilter(R.color.black);
        }
        actualizaManual();
        actualizaBotonesManuales(botonesManuales);
    }

    private void actualizaBotonesManuales(ImageButton[] botonesManuales) {
        if (this.personaje.getPuntosStatManuales()==0){
            for (ImageButton btn:botonesManuales) {
            btn.setEnabled(false);
            btn.setColorFilter(R.color.black);
            }
            findViewById(R.id.btnStatsManualContinuar).setEnabled(true);
        }
    }

    private void actualizaManual(){
        actualizarEtiquetasStats();
        actualizarBarrasStats();
    }
    private void actualizarBarrasStats() {
        ProgressBar progressBarSalud = findViewById(R.id.progressBarSalud);
        ProgressBar progressBarAtaqueFisico = findViewById(R.id.progressBarAtaqueFisico);
        ProgressBar progressBarAtaqueMagico = findViewById(R.id.progressBarAtaqueMagico);
        ProgressBar progressBarDefensaFisica = findViewById(R.id.progressBarDefensaFisica);
        ProgressBar progressBarDefensaMagica = findViewById(R.id.progressBarDefensaMagica);
        ProgressBar progressBarPunteria = findViewById(R.id.progressBarPunteria);

        progressBarSalud.setProgress(this.personaje.getStat_salud());
        progressBarAtaqueFisico.setProgress(this.personaje.getStat_ataque_fisico());
        progressBarAtaqueMagico.setProgress(this.personaje.getStat_ataque_magico());
        progressBarDefensaFisica.setProgress(this.personaje.getStat_defensa_fisica());
        progressBarDefensaMagica.setProgress(this.personaje.getStat_defensa_magica());
        progressBarPunteria.setProgress(this.personaje.getStat_punteria());

        TextView lbValPuntosRestantes = findViewById(R.id.lbValPuntosRestantes);
        lbValPuntosRestantes.setText(""+personaje.getPuntosStatManuales());
    }

    private void muestraToast(String msg) {
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        t.show();
    }
}