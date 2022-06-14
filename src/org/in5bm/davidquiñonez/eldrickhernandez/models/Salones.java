package org.in5bm.davidquiñonez.eldrickhernandez.models;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 29 abr. 2022
 * @time 11:20:30
 *
 * CódigoTécnico: IN5BM
 */
public class Salones {

    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;

    public Salones() {

    }

    public Salones(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public Salones(String descripcion, int capacidadMax, String edificio,
            int nivel) {
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public Salones(String codigoSalon, int capacidadMaxima) {
        this.codigoSalon = codigoSalon;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Salones(String codigoSalon, String descripcion, int capacidadMaxima,
            String edificio, int nivel) {
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    //GETTERS 
    public String getCodigoSalon() {
        return codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }

    public int getNivel() {
        return nivel;
    }

    //SETTERS
    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return  codigoSalon;
    }

}
