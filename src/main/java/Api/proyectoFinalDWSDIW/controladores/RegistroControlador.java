package Api.proyectoFinalDWSDIW.controladores;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity<Map<String, String>> registroUsuario(@RequestBody RegistroDto usuarioDto) {
        try {
            Map<String, String> response = new HashMap<>();

            if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
                response.put("message", "El email es obligatorio.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            if (usuarioServicio.emailExistsUsuario(usuarioDto.getEmailUsuario())) {
                response.put("message", "El email ya está registrado.");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            String token = UUID.randomUUID().toString();
            LocalDateTime fechaExpiracion = LocalDateTime.now().plusDays(7);

            usuarioServicio.guardarRegistroTemporal(usuarioDto, token, fechaExpiracion);
            
            String enlaceConfirmacion = "http://localhost:8081/api/registro/confirmar?token=" + token;
            emailServicio.enviarCorreo(usuarioDto.getEmailUsuario(), "Confirma tu cuenta", 
                "Haz clic en el siguiente enlace para activar tu cuenta: " + enlaceConfirmacion);

            response.put("message", "Correo de confirmación enviado.");
            return ResponseEntity.status(HttpStatus.CREATED).body(response); // Ahora devuelve JSON válido

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Error interno del servidor.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
    @GetMapping("/confirmar")
    public ResponseEntity<Void> confirmarUsuario(@RequestParam("token") String token) {
        boolean confirmado = usuarioServicio.confirmarRegistro(token);

        if (confirmado) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "http://localhost:4200/login"); // URL del proyecto Angular
            return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 Found → Redirección
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
