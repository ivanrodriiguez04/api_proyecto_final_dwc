package Api.proyectoFinalDWSDIW.controladores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.servicios.AdministradorServicio;



/**
 * Controlador para gestionar usuarios administradores.
 * Proporciona endpoints para obtener y eliminar usuarios.
 * 
 * @author irodhan - 06/03/2025
 */
@RestController
@RequestMapping("/api/administrador")
public class AdministradorControlador {

    @Autowired
    private AdministradorServicio administradorServicio;

    private static final Logger logger = LoggerFactory.getLogger(AdministradorControlador.class);
    
    /**
     * Obtiene la lista de todos los usuarios.
     * 
     * @return ResponseEntity con la lista de usuarios o código 204 si está vacía
     */
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDao>> obtenerUsuarios() {
        logger.info("Solicitud recibida para obtener todos los usuarios");
        List<UsuarioDao> usuarios = administradorServicio.obtenerTodosLosUsuarios();
        if (usuarios.isEmpty()) {
            logger.warn("No se encontraron usuarios en la base de datos");
            return ResponseEntity.noContent().build();
        }
        logger.info("Usuarios obtenidos: {}", usuarios.size());
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Elimina un usuario por su ID.
     * 
     * @param id Identificador del usuario a eliminar
     * @return ResponseEntity con mensaje de éxito o error
     */
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, String>> eliminarUsuario(@PathVariable Long id) {
        logger.info("Solicitud recibida para eliminar usuario con ID: {}", id);
        boolean eliminado = administradorServicio.eliminarUsuario(id);

        Map<String, String> response = new HashMap<>();

        if (eliminado) {
            logger.info("Usuario con ID {} eliminado correctamente", id);
            response.put("message", "Usuario eliminado correctamente");
            return ResponseEntity.ok(response); // Devuelve JSON
        } else {
            logger.warn("Usuario con ID {} no encontrado", id);
            response.put("message", "Usuario no encontrado");
            return ResponseEntity.status(404).body(response);
        }
    }
}