package com.example.rpgjjbo;

public enum EnumGender {
    femenino(R.string.femenino,R.drawable.femaleicon,1),
    masculino(R.string.masculino,R.drawable.maleicon,0);

    private int nombre;
    private int icono;

    private int id;
    EnumGender(int nombre,int icono, int id) {
        this.nombre=nombre;
        this.icono=icono;
        this.id =  id;
    }
    public int getNombre(){
        return nombre;
    }

    public int getIcono() {
        return icono;
    }

    public int getId() {
        return id;
    }
}
