package com.example.rpgjjbo;

import android.content.Context;
import android.content.res.TypedArray;
import android.widget.ImageButton;
import android.widget.ImageView;
//import com.example.rpgjjbo.R;

public class SelectorAvatar {
    Context contexto;
    ImageButton btnAnterior;
    ImageButton btnPosterior;
    ImageView imgAvatar;
    Personaje personaje;
    int actual=0;
    int desfase=0;
    TypedArray resourceAvatares;

    public SelectorAvatar(ImageButton btnAnterior, ImageButton btnPosterior, ImageView imgAvatar, Personaje personaje,Context contexto) {
        this.btnAnterior = btnAnterior;
        this.btnPosterior = btnPosterior;
        this.imgAvatar = imgAvatar;
        this.personaje = personaje;
        this.contexto=contexto;
        desfase=this.personaje.getClase().id*8+this.personaje.getGenero().getId()*4;
        this.resourceAvatares = contexto.getResources().obtainTypedArray(R.array.avatares);
        setEventos();
        actualizarPersonaje();
        actualizaVistaAvatar();
    }

    private void setEventos() {
        if (this.btnAnterior!=null)
            this.btnAnterior.setOnClickListener(view -> anterior());
        if (this.btnPosterior!=null)
            this.btnPosterior.setOnClickListener(view ->posterior());
    }

    private void posterior() {
        this.actual++;
        if(this.actual>3)
            this.actual=0;
        actualizarPersonaje();
        actualizaVistaAvatar();
    }

    private void actualizarPersonaje() {
        this.personaje.setAvatar(this.resourceAvatares.getResourceId(this.desfase+actual,0));
    }

    private void anterior() {
        this.actual--;
        if(this.actual<0)
            this.actual=3;
        actualizarPersonaje();
        actualizaVistaAvatar();
    }

    private void actualizaVistaAvatar(){
        this.imgAvatar.setImageResource(personaje.getAvatar());
    }
}
