/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.models.Pasajero;
import core.services.ServicioPasajeros;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author NICOLAS ARIAS
 */
public class PasajeroController {
    private final ServicioPasajeros servicio;
    private static PasajeroController instancia;

    public PasajeroController(ServicioPasajeros servicio) {
        this.servicio = servicio;
    }
    
    // Método estático para obtener la instancia (Singleton)
    public static PasajeroController getInstance(ServicioPasajeros servicio) {
        if (instancia == null) {
            instancia = new PasajeroController(servicio);
        }
        return instancia;
    }
    
    public Response<Pasajero> registrarPasajero(Pasajero pasajero) {
        if (!validarPasajero(pasajero)) {
            return new Response<>(400, "Datos inválidos", null);
        }
        if (servicio.existe(pasajero.getId())) {
            return new Response<>(409, "ID de pasajero ya existe", null);
        }
        Pasajero copia = pasajero.clonar();
        servicio.registrar(copia);
        return new Response<>(201, "Pasajero registrado", copia);
    }

    public Response<Pasajero> actualizarPasajero(Pasajero pasajero) {
        if (!validarPasajero(pasajero)) {
            return new Response<>(400, "Datos inválidos", null);
        }
        if (!servicio.existe(pasajero.getId())) {
            return new Response<>(404, "Pasajero no encontrado", null);
        }
        Pasajero copia = pasajero.clonar();
        servicio.actualizar(copia);
        return new Response<>(200, "Pasajero actualizado", copia);
    }

    public Response<ArrayList<Pasajero>> obtenerPasajerosOrdenados() {
        ArrayList<Pasajero> pasajeros = servicio.obtenerTodosOrdenadosPorId();
        ArrayList<Pasajero> copia = pasajeros.stream().map(Pasajero::clonar).collect(Collectors.toCollection(ArrayList::new));
        return new Response<>(200, "Pasajeros obtenidos", copia);
    }

    private boolean validarPasajero(Pasajero p) {
        if (p == null) return false;
        if (p.getId() < 0 || String.valueOf(p.getId()).length() > 15) return false;
        if (p.getNombre() == null || p.getNombre().trim().isEmpty()) return false;
        if (p.getCodigoPaisTelefono() < 0 || String.valueOf(p.getCodigoPaisTelefono()).length() > 3) return false;
        if (p.getTelefono() < 0 || String.valueOf(p.getTelefono()).length() > 11) return false;
        if (p.getFechaNacimiento() == null) return false;
        return true;
    }
}

