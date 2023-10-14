package com.example.rpgjjbo;

public enum EnumClassType {
    humano(R.string.humano),
    elfo(R.string.elfo),
    enano(R.string.enano),
    orco(R.string.orco);

    int nombre;
    EnumClassType(int nombre) {
        this.nombre=nombre;
    }
    public int getNombre(){
        return nombre;
    }
}
