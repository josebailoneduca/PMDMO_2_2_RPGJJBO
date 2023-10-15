package com.example.rpgjjbo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {

   private Personaje personaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        Button btnInicio = findViewById(R.id.btnInicio);
        btnInicio.setOnClickListener(view -> elijeClase());
    }

    // ELEGIR CLASE
    private void elijeClase() {
        this.personaje = new Personaje();
        setContentView(R.layout.elegir_clase);
        Button btnClaseContinuar = findViewById(R.id.btnRasgosContinuar);
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
    @SuppressLint("SetTextI18n")
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

    @SuppressLint("SetTextI18n")
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
        btnStatSalud.setOnClickListener(view -> aumentaStatManual(personaje.aumentaSalud(),view,botonesManuales));
        btnStatAtaqueFisico.setOnClickListener(view -> aumentaStatManual(personaje.aumentaAtaqueFisico(),view,botonesManuales));
        btnStatAtaqueMagico.setOnClickListener(view -> aumentaStatManual(personaje.aumentaAtaqueMagico(),view,botonesManuales));
        btnStatDefensaFisica.setOnClickListener(view -> aumentaStatManual(personaje.aumentaDefensaFisica(),view,botonesManuales));
        btnStatDefensaMagica.setOnClickListener(view -> aumentaStatManual(personaje.aumentaDefensaMagica(),view,botonesManuales));
        btnStatPunteria.setOnClickListener(view -> aumentaStatManual(personaje.aumentaPunteria(),view,botonesManuales));

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
    @SuppressLint("SetTextI18n")
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
    @SuppressLint("ClickableViewAccessibility")
    private void avatarNombreDescripcion() {
        setContentView(R.layout.nombre_descripcion);
        ImageButton btnAnterior= findViewById(R.id.btnAvatarAnterior);
        ImageButton btnPosterior= findViewById(R.id.btnAvatarPosterior);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        Button btnNombreContinuar = findViewById(R.id.btnNombreContinuar);
        EditText inputNombre = findViewById(R.id.inputNombre);
        EditText inputDescripcion = findViewById(R.id.inputDescripcion);
        ImageView fondoNombre= findViewById(R.id.fondoNombre);

        SelectorAvatar selectorAvatar=new SelectorAvatar(btnAnterior,btnPosterior,imgAvatar,personaje,this);

        btnNombreContinuar.setEnabled(false);
        inputDescripcion.setRawInputType(InputType.TYPE_CLASS_TEXT);
        inputDescripcion.addTextChangedListener(this);

        fondoNombre.setOnTouchListener((view, motionEvent) -> {
             InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
             inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            btnNombreContinuar.setEnabled(inputNombre.getText().length() > 0 && inputDescripcion.getText().length() > 0);
            return false;
        });

        inputNombre.setOnFocusChangeListener((view, b) -> {});

        inputNombre.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                btnNombreContinuar.setEnabled(inputNombre.getText().length() > 0 && inputDescripcion.getText().length() > 0);
            }
            return false;
        });

        inputDescripcion.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                btnNombreContinuar.setEnabled(inputNombre.getText().length() > 0 && inputDescripcion.getText().length() > 0);
            }
            return false;
        });
        btnNombreContinuar.setOnClickListener(view -> {
            personaje.setNombre(inputNombre.getText().toString());
            personaje.setBiografia(inputDescripcion.getText().toString());
            elijeRasgos();
        });
    }
    //RASGOS
    private void elijeRasgos() {
        setContentView(R.layout.rasgos);
        CheckBox [] btnRasgos = new CheckBox[8];
        btnRasgos[0]=findViewById(R.id.inputBerserker);
        btnRasgos[1]=findViewById(R.id.inputCauteloso);
        btnRasgos[2]=findViewById(R.id.inputConductorOtto);
        btnRasgos[3]=findViewById(R.id.inputConcentrado);
        btnRasgos[4]=findViewById(R.id.inputHonesto);
        btnRasgos[5]=findViewById(R.id.inputRapido);
        btnRasgos[6]=findViewById(R.id.inputMusculoso);
        btnRasgos[7]=findViewById(R.id.inputEmpollon);
        for (CheckBox c:btnRasgos) {
            c.setOnCheckedChangeListener((compoundButton, b) -> manejadorCheckRasgos(btnRasgos));
        }

        ImageButton[]btnInfoRasgos = new ImageButton[8];
        btnInfoRasgos[0]=findViewById(R.id.btnInfoBerserker);
        btnInfoRasgos[1]=findViewById(R.id.btnInfoCauteloso);
        btnInfoRasgos[2]=findViewById(R.id.btnInfoConductorOtto);
        btnInfoRasgos[3]=findViewById(R.id.btnInfoConcentrado);
        btnInfoRasgos[4]=findViewById(R.id.btnInfoHonesto);
        btnInfoRasgos[5]=findViewById(R.id.btnInfoRapido);
        btnInfoRasgos[6]=findViewById(R.id.btnInfoMusucloso);
        btnInfoRasgos[7]=findViewById(R.id.btnInfoEmpollon);
        for (ImageButton i:btnInfoRasgos) {
            i.setOnClickListener(view -> manejadorInfoRasgos(view,btnInfoRasgos));
        }


        Button btnRasgosContinuar = findViewById(R.id.btnRasgosContinuar);
        btnRasgosContinuar.setOnClickListener(view -> verFichaDePersonaje());

    }

    private void manejadorCheckRasgos(CheckBox[] btnRasgos) {
        personaje.resetTraits();
        for(int i=0;i<btnRasgos.length;i++){
            if(btnRasgos[i].isChecked()) {
                btnRasgos[i].setTextColor(getColor(R.color.verde_claro));
                switch (i) {
                    case 0:
                        personaje.addTrait(EnumTrait.berserker);
                        break;
                    case 1:
                        personaje.addTrait(EnumTrait.cauteloso);
                        break;
                    case 2:
                        personaje.addTrait(EnumTrait.conductor_otto);
                        break;
                    case 3:
                        personaje.addTrait(EnumTrait.concentrado);
                        break;
                    case 4:
                        personaje.addTrait(EnumTrait.honesto);
                        break;
                    case 5:
                        personaje.addTrait(EnumTrait.rapido);
                        break;
                    case 6:
                        personaje.addTrait(EnumTrait.musculoso);
                        break;
                    case 7:
                        personaje.addTrait(EnumTrait.empollon);
                        break;
                }
            }else{
                btnRasgos[i].setTextColor(getColor(R.color.white));
            }
        }
        //si hemos llegado a 3 marcados entonces desactivamos el resto
        if (personaje.getTraits().size()==3){
            for (CheckBox btnRasgo : btnRasgos) {
                if (!btnRasgo.isChecked()) {
                    btnRasgo.setEnabled(false);
                    btnRasgo.setTextColor(getColor(R.color.rojo_oscuro));
                }
            }

            findViewById(R.id.btnRasgosContinuar).setEnabled(true);
            //en caso contrario los habilitamos todos
        }else {
            for (CheckBox btnRasgo : btnRasgos) {
                btnRasgo.setEnabled(true);
            }
            findViewById(R.id.btnRasgosContinuar).setEnabled(false);
        }
    }


    private void manejadorInfoRasgos(View view,ImageButton[]botones) {
        for (int i=0;i<botones.length;i++){
            if (view.equals(botones[i]))
                switch (i){
                    case 0:
                        muestraToast(getString(R.string.berserker_desc));
                        break;
                    case 1:
                        muestraToast(getString(R.string.cauteloso_desc));
                        break;
                    case 2:
                        muestraToast(getString(R.string.conductor_otto_desc));
                        break;
                    case 3:
                        muestraToast(getString(R.string.concentrado_desc));
                        break;
                    case 4:
                        muestraToast(getString(R.string.honesto_desc));
                        break;
                    case 5:
                        muestraToast(getString(R.string.rapido_desc));
                        break;
                    case 6:
                        muestraToast(getString(R.string.musculoso_desc));
                        break;
                    case 7:
                        muestraToast(getString(R.string.empollon_desc));
                        break;
                }
        }
    }




    //VER FICHA DE PERSONAJE
    private void verFichaDePersonaje() {
        setContentView(R.layout.ficha);
        Button btnStatsManualContinuar = findViewById(R.id.btnStatsManualContinuar);
        btnStatsManualContinuar.setOnClickListener(view -> elijeClase());

        TextView lbNombre = findViewById(R.id.lbNombre);
        TextView lbClase = findViewById(R.id.lbClase);
        TextView lbBiografia = findViewById(R.id.lbBiografia);
        ImageView imgAvatar = findViewById(R.id.imgAvatar);
        ImageView imgGenero = findViewById(R.id.imgGenero);
        lbNombre.setText(personaje.getNombre());
        lbClase.setText(getString(personaje.getClase().nombre));
        lbBiografia.setText(personaje.getBiografia());
        imgAvatar.setImageResource(personaje.getAvatar());
        imgGenero.setImageResource(personaje.getGenero().getIcono());

        actualizarEtiquetasStats();
        actualizarBarrasStats();

        TextView lbRasgo1 = findViewById(R.id.lbRasgo1);
        TextView lbRasgo1Desc = findViewById(R.id.lbRasgo1Desc);
        TextView lbRasgo2 = findViewById(R.id.lbRasgo2);
        TextView lbRasgo2Desc = findViewById(R.id.lbRasgo2Desc);
        TextView lbRasgo3 = findViewById(R.id.lbRasgo3);
        TextView lbRasgo3Desc = findViewById(R.id.lbRasgo3Desc);
        ArrayList<EnumTrait> rasgos = personaje.getTraits();
        lbRasgo1.setText(getString(rasgos.get(0).nombre));
        lbRasgo1Desc.setText(getString(rasgos.get(0).descripcion));
        lbRasgo2.setText(getString(rasgos.get(1).nombre));
        lbRasgo2Desc.setText(getString(rasgos.get(1).descripcion));
        lbRasgo3.setText(getString(rasgos.get(2).nombre));
        lbRasgo3Desc.setText(getString(rasgos.get(2).descripcion));
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

    @SuppressLint("SetTextI18n")
    @Override
    public void afterTextChanged(Editable editable) {
        TextView lbCharRestantes = findViewById(R.id.lbCharRestantes);
        lbCharRestantes.setText("("+(140-editable.length())+")");
    }
}