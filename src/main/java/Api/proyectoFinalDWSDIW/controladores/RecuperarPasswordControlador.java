package Api.proyectoFinalDWSDIW.controladores;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.RecuperarPasswordDto;
import Api.proyectoFinalDWSDIW.servicios.TokenServicio;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/password")
public class RecuperarPasswordControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private TokenServicio tokenServicio;

    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperarClave(@RequestBody RecuperarPasswordDto solicitud) {
        try {
            tokenServicio.enviarCorreoRecuperacion(solicitud.getEmailUsuario());
            return ResponseEntity.ok(Map.of("mensaje", "Se ha enviado un correo con instrucciones para recuperar la clave."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al enviar el correo: " + e.getMessage()));
        }
    }

    @PostMapping("/restablecer")
    public ResponseEntity<?> restablecerPassword(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String nuevaPassword = requestBody.get("passwordUsuario");

        System.out.println("Token recibido en la API: " + token);
        System.out.println("Nueva contraseña en la API: " + nuevaPassword);

        if (nuevaPassword == null || nuevaPassword.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "La contraseña no puede estar vacía."));
        }

        boolean actualizado = usuarioServicio.actualizarPassword(token, nuevaPassword);
        
        if (actualizado) {
            return ResponseEntity.ok(Map.of("mensaje", "Contraseña restablecida con éxito."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token inválido o usuario no encontrado."));
        }
    }
}
