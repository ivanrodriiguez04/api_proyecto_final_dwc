package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.LoginDto;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/login")
public class LoginControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @PostMapping("/validarUsuario")
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginDto usuario) {
        try {
            System.out.println("Email recibido: " + usuario.getEmailUsuario());
            System.out.println("Contraseña recibida: " + usuario.getPasswordUsuario());

            ResponseEntity<String> resultado = usuarioServicio.validarCredenciales(usuario.getEmailUsuario(), usuario.getPasswordUsuario());

            if (resultado.getStatusCodeValue() == 401) {
                return ResponseEntity.status(401).body("Usuario o contraseña incorrectos.");
            }

            String rol = resultado.getBody().trim();
            if ("admin".equals(rol)) {
                return ResponseEntity.status(200).body("admin");
            } else if ("usuario".equals(rol)) {
                return ResponseEntity.status(200).body("usuario");
            }

            return ResponseEntity.status(401).body("Rol desconocido.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }
}
