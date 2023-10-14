package com.example.rpgjjbo;

public enum EnumGender {
    femenino(R.string.femenino,R.drawable.femaleicon),
    masculino(R.string.masculino,R.drawable.maleicon);

    private int nombre;
    private int icono;
    EnumGender(int nombre,int icono) {
        this.nombre=nombre;
        this.icono=icono;
    }
    public int getNombre(){
        return nombre;
    }

    public int getIcono() {
        return icono;
    }
}
