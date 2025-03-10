package Api.proyectoFinalDWSDIW.controladores;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;
import Api.proyectoFinalDWSDIW.servicios.CuentaServicio;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaControlador {

    @Autowired
    private CuentaServicio cuentaServicio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    private static final Logger logger = LoggerFactory.getLogger(CuentaControlador.class);

    /**
     * Obtiene las cuentas asociadas a un usuario por su ID.
     * @param idUsuario ID del usuario.
     * @return Lista de cuentas o mensaje de error si no existen.
     */
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<?> obtenerCuentasPorUsuario(@PathVariable("idUsuario") Long idUsuario) {
        logger.info("Solicitud para obtener cuentas del usuario con ID: {}", idUsuario);
        List<CuentaDao> cuentas = cuentaServicio.obtenerCuentasPorUsuario(idUsuario);
        if (cuentas.isEmpty()) {
            logger.warn("No hay cuentas para el usuario con ID: {}", idUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No hay cuentas para este usuario"));
        }
        logger.info("Se encontraron {} cuentas para el usuario con ID: {}", cuentas.size(), idUsuario);
        return ResponseEntity.ok(cuentas);
    }

    /**
     * Obtiene las cuentas de un usuario por su correo electrónico.
     * @param emailUsuario Email del usuario.
     * @return Lista de cuentas o mensaje de error si no existen.
     */
    @GetMapping("/usuario/email/{email}")
    public ResponseEntity<?> obtenerCuentasPorEmail(@PathVariable("email") String emailUsuario) {
        logger.info("Solicitud para obtener cuentas del usuario con email: {}", emailUsuario);
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);
        if (usuario == null) {
            logger.warn("Usuario no encontrado con email: {}", emailUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }
        List<CuentaDao> cuentas = cuentaServicio.obtenerCuentasPorUsuario(usuario.getIdUsuario());
        if (cuentas.isEmpty()) {
            logger.warn("No hay cuentas para el usuario con email: {}", emailUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "No hay cuentas para este usuario"));
        }
        logger.info("Se encontraron {} cuentas para el usuario con email: {}", cuentas.size(), emailUsuario);
        return ResponseEntity.ok(cuentas);
    }

    /**
     * Crea una nueva cuenta para un usuario.
     * @param requestBody Datos de la cuenta en formato JSON.
     * @return Mensaje de éxito o error.
     */
    @PostMapping("/crear")
    public ResponseEntity<?> crearCuenta(@RequestBody Map<String, String> requestBody) {
        logger.info("Solicitud para crear una cuenta con datos: {}", requestBody);
        String emailUsuario = requestBody.get("emailUsuario");
        String nombreCuenta = requestBody.get("nombreCuenta");
        String tipoCuenta = requestBody.get("tipoCuenta");
        String ibanCuenta = requestBody.get("ibanCuenta");
        Double dineroCuenta = Double.parseDouble(requestBody.get("dineroCuenta"));

        if (emailUsuario == null || nombreCuenta == null || tipoCuenta == null || ibanCuenta == null || dineroCuenta == null) {
            logger.error("Faltan campos obligatorios en la solicitud");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Todos los campos son obligatorios"));
        }

        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);
        if (usuario == null) {
            logger.warn("Usuario no encontrado con email: {}", emailUsuario);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuario no encontrado"));
        }

        boolean creada = cuentaServicio.crearCuenta(usuario.getIdUsuario(), nombreCuenta, tipoCuenta, ibanCuenta, dineroCuenta);

        if (creada) {
            logger.info("Cuenta creada con éxito para el usuario con email: {}", emailUsuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensaje", "Cuenta creada con éxito"));
        } else {
            logger.warn("Intento fallido de creación de cuenta, IBAN en uso: {}", ibanCuenta);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "El IBAN ya está en uso"));
        }
    }

    /**
     * Elimina una cuenta por su ID.
     * @param idCuenta ID de la cuenta a eliminar.
     * @return Mensaje de éxito o error.
     */
    @DeleteMapping("/eliminar/{idCuenta}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable("idCuenta") Long idCuenta) {
        logger.info("Solicitud para eliminar cuenta con ID: {}", idCuenta);
        boolean eliminado = cuentaServicio.eliminarCuenta(idCuenta);
        if (eliminado) {
            logger.info("Cuenta con ID {} eliminada con éxito", idCuenta);
            return ResponseEntity.ok(Map.of("mensaje", "Cuenta eliminada con éxito"));
        } else {
            logger.warn("Cuenta con ID {} no encontrada", idCuenta);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cuenta no encontrada"));
        }
    }
}
