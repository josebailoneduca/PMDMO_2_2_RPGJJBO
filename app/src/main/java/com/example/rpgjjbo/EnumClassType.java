package com.example.rpgjjbo;

/**
 * Enumerador de tipos de clase de presonaje
 */
public enum EnumClassType {
    humano(R.string.humano,0),
    elfo(R.string.elfo,1),
    enano(R.string.enano,2),
    orco(R.string.orco,3);

    /**
     * Nombre de la clase
     */
    final int nombre;
    /**
     * Id de la clase
     */
    final int id;
    EnumClassType(int nombre, int id) {
        this.nombre=nombre;
        this.id=id;
    }
    public int getNombre(){
        return nombre;
    }

    public int getId() {
        return id;
    }
}
