/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core.controllers;

import core.controllers.utils.Response;
import core.models.Aeropuerto;
import core.models.Ubicacion;
import core.services.ServicioAeropuertos;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author NICOLAS ARIAS
 */
public class AeropuertoController {
    private final ServicioAeropuertos servicio;
    private static AeropuertoController instancia;

    private AeropuertoController(ServicioAeropuertos servicio) {
        this.servicio = servicio;
    }

    public static AeropuertoController getInstance(ServicioAeropuertos servicio) {
        if (instancia == null) {
            instancia = new AeropuertoController(servicio);
        }
        return instancia;
    }

    public Response<Ubicacion> crearAeropuerto(Ubicacion ubicacion) {
        if (!validarAeropuerto(ubicacion)) {
            return new Response<>(400, "Datos inválidos", null);
        }
        if (servicio.existeAeropuerto(ubicacion.getIdAeropuerto())) {
            return new Response<>(409, "ID de aeropuerto ya existe", null);
        }
        Ubicacion copia = ubicacion.clonar(); 
        servicio.registrarAeropuerto(copia);
        return new Response<>(201, "Aeropuerto creado", copia);
    }

    public Response<ArrayList<Ubicacion>> obtenerAeropuertosOrdenados() {
        ArrayList<Ubicacion> aeropuertos = new ArrayList<>(servicio.obtenerTodosLosAeropuertos());
        aeropuertos.sort((a1, a2) -> a1.getIdAeropuerto().compareTo(a2.getIdAeropuerto()));
        ArrayList<Ubicacion> copia = aeropuertos.stream().map(Ubicacion::clonar).collect(Collectors.toCollection(ArrayList::new));
        return new Response<>(200, "Aeropuertos obtenidos", copia);
    }

    private boolean validarAeropuerto(Ubicacion u) {
        if (u == null) return false;
        if (!u.getIdAeropuerto().matches("[A-Z]{3}")) return false;
        if (u.getCiudad() == null || u.getCiudad().trim().isEmpty()) return false;
        if (u.getPais() == null || u.getPais().trim().isEmpty()) return false;
        if (!validarCoordenada(u.getLatitud(), -90, 90)) return false;
        if (!validarCoordenada(u.getLongitud(), -180, 180)) return false;
        return true;
    }

    private boolean validarCoordenada(double valor, double min, double max) {
        if (valor < min || valor > max) return false;
        String[] partes = String.valueOf(valor).split("\\.");
        return partes.length < 2 || partes[1].length() <= 4;
    }
}

