/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;


import core.controllers.utils.Response;
import core.models.Avion;
import core.services.ServicioAviones;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author NICOLAS ARIAS
 */
public class AvionController {
    private final ServicioAviones servicio;
    private static AvionController instancia;

    private AvionController(ServicioAviones servicio) {
        this.servicio = servicio;
    }

    public static AvionController getInstance(ServicioAviones servicio) {
        if (instancia == null) {
            instancia = new AvionController(servicio);
        }
        return instancia;
    }

    public Response<Avion> registrarAvion(Avion avion) {
        if (!validarAvion(avion)) {
            return new Response<>(400, "Datos inválidos", null);
        }
        if (servicio.existeAvion(avion.getId())) {
            return new Response<>(409, "ID de avión ya existe", null);
        }
        
        Avion copia = avion.clone();
        servicio.registrarAvion(copia);
        return new Response<>(201, "Avión registrado", copia);
    }

    public Response<ArrayList<Avion>> obtenerAvionesOrdenados() {
        ArrayList<Avion> aviones = servicio.obtenerAvionesOrdenadosPorId();
        ArrayList<Avion> copia = aviones.stream().map(Avion::clone).collect(Collectors.toCollection(ArrayList::new));
        return new Response<>(200, "Aviones obtenidos", copia);
    }

    private boolean validarAvion(Avion a) {
        if (a == null) return false;
        if (!a.getId().matches("[A-Z]{2}\\d{5}")) return false;
        if (a.getModelo() == null || a.getModelo().trim().isEmpty()) return false;
        if (a.getMarca() == null || a.getMarca().trim().isEmpty()) return false;
        if (a.getCapacidadMaxima() <= 0) return false; // Suponiendo que el avión debe tener al menos una plaza
        if (a == null) return false;
        if (!a.getId().matches("[A-Z]{2}\\d{5}")) return false;
        if (a.getModelo() == null || a.getModelo().trim().isEmpty()) return false;
        if (a.getMarca() == null || a.getMarca().trim().isEmpty()) return false;
        if (a.getCapacidadMaxima() <= 0 || a.getCapacidadMaxima() > 1000) return false;
        return true;
    }
}

