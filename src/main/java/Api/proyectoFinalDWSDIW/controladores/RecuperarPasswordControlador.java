package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.dtos.RecuperarPasswordDto;
import Api.proyectoFinalDWSDIW.dtos.RestablecerPasswordDto;
import Api.proyectoFinalDWSDIW.servicios.TokenServicio;

@RestController
@RequestMapping("/api/password")
public class RecuperarPasswordControlador {

	@Autowired
    private TokenServicio tokenServicio;

    @PostMapping("/recuperar")
    public ResponseEntity<String> recuperarClave(@RequestBody RecuperarPasswordDto solicitud) {
    	tokenServicio.enviarCorreoRecuperacion(solicitud.getEmailUsuario());
        return ResponseEntity.ok("Se ha enviado un correo con instrucciones para recuperar la clave.");
    }

    @PostMapping("/restablecer")
    public ResponseEntity<String> restablecerClave(@RequestBody RestablecerPasswordDto solicitud) {
    	tokenServicio.restablecerClave(solicitud.getToken(), solicitud.getNuevaPassword());
        return ResponseEntity.ok("La clave ha sido restablecida correctamente.");
    }
}
