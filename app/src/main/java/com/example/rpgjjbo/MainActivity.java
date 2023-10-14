package com.example.rpgjjbo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher {

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

    //STATS ALEATORIOS
    private void statsAleatoria() {
        setContentView(R.layout.asignar_stats_random);
        Button btnLanzarDados = findViewById(R.id.btnLanzarDados);
        Button btnContinuar =findViewById(R.id.btnNombreContinuar);
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

    //STATS MANUAL
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

        btnContinuar.setOnClickListener(view -> avatarNombreDescripcion());
    }



    private void aumentaStatManual(boolean exito, View view, ImageButton[] botonesManuales) {
        if(!exito){
            view.setEnabled(false);
            ((ImageButton)view).setColorFilter(R.color.black);
        }
        actualizaStatsManual();
        actualizaBotonesStatsManual(botonesManuales);
    }

    private void actualizaBotonesStatsManual(ImageButton[] botonesStatsManual) {
        if (this.personaje.getPuntosStatManuales()==0){
            for (ImageButton btn: botonesStatsManual) {
            btn.setEnabled(false);
            btn.setColorFilter(R.color.black);
            }
            findViewById(R.id.btnStatsManualContinuar).setEnabled(true);
        }
    }

    private void actualizaStatsManual(){
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
        if (lbValPuntosRestantes!=null)
            lbValPuntosRestantes.setText(""+personaje.getPuntosStatManuales());
    }



    //AVATAR NOMBRE DESCRIPCION
    private void avatarNombreDescripcion() {
        setContentView(R.layout.nombre_descripcion);
        ImageButton btnAnterior= findViewById(R.id.btnAvatarAnterior);
        ImageButton btnPosterior= findViewById(R.id.btnAvatarPosterior);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);

        SelectorAvatar selectorAvatar=new SelectorAvatar(btnAnterior,btnPosterior,imgAvatar,personaje,this);
        Button btnNombreContinuar = findViewById(R.id.btnNombreContinuar);
        btnNombreContinuar.setEnabled(false);
        EditText inputNombre = findViewById(R.id.inputNombre);
        EditText inputDescripcion = findViewById(R.id.inputDescripcion);
        inputDescripcion.setRawInputType(InputType.TYPE_CLASS_TEXT);
        inputDescripcion.addTextChangedListener(this);
         ImageView fondoNombre= findViewById(R.id.fondoNombre);
        fondoNombre.setOnTouchListener((view, motionEvent) -> {
             InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
             inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            return false;
        });
        inputNombre.setOnFocusChangeListener((view, b) -> {});
        inputNombre.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (inputNombre.getText().length()>0&&inputDescripcion.getText().length()>0)
                    btnNombreContinuar.setEnabled(true);
            }
            return false;
        });
        inputDescripcion.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                if (inputNombre.getText().length()>0&&inputDescripcion.getText().length()>0)
                    btnNombreContinuar.setEnabled(true);
            }
            return false;
        });

    }



    private void muestraToast(String msg) {
        Toast t = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        t.show();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        TextView lbCharRestantes = findViewById(R.id.lbCharRestantes);
        lbCharRestantes.setText("("+(140-editable.length())+")");
    }
}