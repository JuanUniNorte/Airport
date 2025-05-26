/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.models;

import core.services.ServicioVuelos;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author NICOLAS ARIAS
 */
public class Pasajero implements Cloneable{
    private final long id;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private int codigoPaisTelefono;
    private long telefono;
    private String pais;
    private final ArrayList<Vuelo> vuelos;

    public Pasajero(long id, String nombre, String apellido, LocalDate fechaNacimiento, int codigoPaisTelefono, long telefono, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.codigoPaisTelefono = codigoPaisTelefono;
        this.telefono = telefono;
        this.pais = pais;
        this.vuelos = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public int getCodigoPaisTelefono() {
        return codigoPaisTelefono;
    }

    public long getTelefono() {
        return telefono;
    }

    public String getPais() {
        return pais;
    }

    public ArrayList<Vuelo> getVuelos() {
        return new ArrayList<>(vuelos);
    }

    public void agregarVuelo(Vuelo vuelo) {
        if (!vuelos.contains(vuelo)) {
            vuelos.add(vuelo);
        }
    }

    public Pasajero clone() {
        Pasajero clon = new Pasajero(id, nombre, apellido, fechaNacimiento, codigoPaisTelefono, telefono, pais);
        return clon;
    }

    public int getEdad() {
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }

    public String getTelefonoFormateado() {
        return String.format("+%d %d", this.codigoPaisTelefono, this.telefono);
    }

    public String getNombreCompleto() {
        return String.format("%s %s", this.nombre, this.apellido);
    }

    public int getCantidadVuelos() {
        return this.vuelos.size();
    }
    
    public static Pasajero desdeJSON(JSONObject json, ServicioVuelos servicioVuelos) {
        long id = json.getLong("id");
        String nombre = json.getString("nombre");
        String apellido = json.getString("apellido");
        LocalDate fechaNacimiento = LocalDate.parse(json.getString("fechaNacimiento"), DateTimeFormatter.ISO_LOCAL_DATE);
        int codigoPaisTelefono = json.getInt("codigoPaisTelefono");
        long telefono = json.getLong("telefono");
        String pais = json.getString("pais");

        Pasajero pasajero = new Pasajero(id, nombre, apellido, fechaNacimiento, codigoPaisTelefono, telefono, pais);

        if (json.has("vuelos")) {JSONArray vuelosJSON = json.getJSONArray("vuelos");
            for (int i = 0; i < vuelosJSON.length(); i++) {
                String idVuelo = vuelosJSON.getString(i);
                Vuelo vuelo = servicioVuelos.buscarVuelo(idVuelo);
                if (vuelo != null) {
                    pasajero.agregarVuelo(vuelo);
                }
            }
        }

        return pasajero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pasajero)) return false;
        Pasajero pasajero = (Pasajero) o;
        return id == pasajero.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
