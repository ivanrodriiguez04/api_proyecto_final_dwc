package Api.proyectoFinalDWSDIW.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.LoginDto;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;
import Api.proyectoFinalDWSDIW.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/login")
public class LoginControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/validarUsuario")
    public ResponseEntity<String> autenticarUsuario(@RequestBody LoginDto usuario) {
        UsuarioDao usuarioDb = usuarioRepositorio.findByEmailUsuario(usuario.getEmailUsuario());

        if (usuarioDb == null || !passwordEncoder.matches(usuario.getPasswordUsuario(), usuarioDb.getPasswordUsuario())) {
            return ResponseEntity.status(401).body("Usuario o contraseña incorrectos.");
        }

        if (!usuarioDb.isConfirmado()) {
            return ResponseEntity.status(403).body("Debe confirmar su cuenta antes de iniciar sesión.");
        }

        return ResponseEntity.ok(usuarioDb.getRolUsuario());
    }

}
