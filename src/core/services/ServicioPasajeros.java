/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.services;

import core.models.Pasajero;
import core.models.storage.BaseDatosSimulada;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author NICOLAS ARIAS
 */
public class ServicioPasajeros {
    private final BaseDatosSimulada baseDatos;

    public ServicioPasajeros(BaseDatosSimulada baseDatos) {
        this.baseDatos = baseDatos;
    }

    public ServicioPasajeros() {
        this.baseDatos = BaseDatosSimulada.getInstance();
    }

    public void registrarPasajero(Pasajero pasajero) {
        if (pasajero == null || baseDatos.getMapaPasajeros().containsKey(pasajero.getId())) {
            throw new IllegalArgumentException("Pasajero inválido o ya registrado");
        }
        baseDatos.getMapaPasajeros().put(pasajero.getId(), pasajero);
    }

    public void actualizarPasajero(Pasajero pasajero) {
        if (pasajero == null || !baseDatos.getMapaPasajeros().containsKey(pasajero.getId())) {
            throw new IllegalArgumentException("Pasajero inválido o no registrado");
        }
        baseDatos.getMapaPasajeros().put(pasajero.getId(), pasajero);
    }

    public Pasajero buscarPasajero(long id) {
        return baseDatos.obtenerPasajero(id);
    }

    public ArrayList<Pasajero> obtenerPasajerosOrdenadosPorNombre() {
        ArrayList<Pasajero> lista = new ArrayList<>(obtenerTodosLosPasajeros());
        lista.sort(Comparator.comparing(Pasajero::getNombre).thenComparing(Pasajero::getApellido));
        return lista;
    }

    public ArrayList<Pasajero> obtenerPasajerosOrdenadosPorId() {
        ArrayList<Pasajero> lista = new ArrayList<>(obtenerTodosLosPasajeros());
        lista.sort(Comparator.comparingLong(Pasajero::getId));
        return lista;
    }

    public int cantidadPasajeros() {
        return baseDatos.getMapaPasajeros().size();
    }

    public boolean existePasajero(long id) {
        return baseDatos.getMapaPasajeros().containsKey(id);
    }

    public void eliminarPasajero(long id) {
        baseDatos.getMapaPasajeros().remove(id);
    }

    public Collection<Pasajero> obtenerTodosLosPasajeros() {
        return baseDatos.getMapaPasajeros().values();
    }
}

