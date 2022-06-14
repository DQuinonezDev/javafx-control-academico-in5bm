package org.in5bm.davidquiñonez.eldrickhernandez.models;

/**
 *
 * @author David Josué André Quiñonez Zeta
 * @date 29 abr. 2022
 * @time 11:20:39
 *
 * CodigoTecnico: IN5BM
 */
public class CarrerasTecnicas {

    private String codigoTecnico;
    private String carrera;
    private String grado;   
    private char seccion;
    private String jornada;

    public CarrerasTecnicas() {
    }

    public CarrerasTecnicas(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public CarrerasTecnicas(String carrera, String grado, char seccion,
            String jornada) {
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }

    public CarrerasTecnicas(String codigoTecnico, String carrera, String grado,
            char seccion, String jornada) {
        this.codigoTecnico = codigoTecnico;
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }

    //GETTERS
    public String getCodigoTecnico() {
        return codigoTecnico;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getGrado() {
        return grado;
    }

    public char getSeccion() {
        return seccion;
    }

    public String getJornada() {
        return jornada;
    }

    //SETTERS
    public void setCodigoTecnico(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public void setSeccion(char seccion) {
        this.seccion = seccion;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    @Override
    public String toString() {
        return  codigoTecnico  + " | "  + carrera;
    }

}
