package Api.proyectoFinalDWSDIW.controladores;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.servicios.EmailServicio;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/registro")
public class RegistroControlador {
@Autowired
	private EmailServicio emailServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/usuario")
    public ResponseEntity<String> registroUsuario(@RequestBody RegistroDto usuarioDto) {
        try {
            if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email es obligatorio.");
            }

            if (usuarioServicio.emailExistsUsuario(usuarioDto.getEmailUsuario())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado.");
            }

            String token = UUID.randomUUID().toString();
            LocalDateTime fechaExpiracion = LocalDateTime.now().plusDays(7);

            usuarioServicio.guardarRegistroTemporal(usuarioDto, token, fechaExpiracion);
            
            String enlaceConfirmacion = "http://localhost:8081/api/registro/confirmar?token=" + token;
            emailServicio.enviarCorreo(usuarioDto.getEmailUsuario(), "Confirma tu cuenta", 
                "Haz clic en el siguiente enlace para activar tu cuenta: " + enlaceConfirmacion);

            return ResponseEntity.status(HttpStatus.CREATED).body("Correo de confirmación enviado.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
    @GetMapping("/confirmar")
    public ResponseEntity<Object> confirmarUsuario(@RequestParam("token") String token) {
        boolean confirmado = usuarioServicio.confirmarRegistro(token);

        if (confirmado) {
            return ResponseEntity.status(302)
                    .header("Location", "http://localhost:4200/login")  // Nueva URL del login en Angular
                    .build();
        } else {
            return ResponseEntity.status(302)
                    .header("Location", "http://localhost:4200/error")  // Redirigir a una página de error si falla
                    .build();
        }
    }

}
