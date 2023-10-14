package com.example.rpgjjbo;

public enum EnumGender {
    femenino(R.string.femenino),
    masculino(R.string.masculino);

    private int nombre;
    EnumGender(int nombre) {
        this.nombre=nombre;
    }
    public int getNombre(){
        return nombre;
    }
}
