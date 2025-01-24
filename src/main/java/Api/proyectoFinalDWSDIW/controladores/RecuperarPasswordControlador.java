package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.RecuperarPasswordDto;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/password")
public class RecuperarPasswordControlador {

	@Autowired
    private UsuarioServicio usuarioServicio;

    // Endpoint para solicitar la recuperación
    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarPassword(@RequestBody String emailUsuario) {
        try {
            usuarioServicio.enviarEmailRecuperacion(emailUsuario);
            return ResponseEntity.status(HttpStatus.OK).body("Correo de recuperación enviado.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    // Endpoint para restablecer la contraseña
    @PostMapping("/restablecer")
    public ResponseEntity<String> restablecerPassword(@RequestBody RecuperarPasswordDto restablecerDto) {
        try {
            usuarioServicio.restablecerPassword(restablecerDto.getToken(), restablecerDto.getNuevaPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Contraseña restablecida con éxito.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}
