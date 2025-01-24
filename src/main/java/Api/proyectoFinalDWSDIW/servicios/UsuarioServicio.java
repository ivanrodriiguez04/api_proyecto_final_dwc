package Api.proyectoFinalDWSDIW.servicios;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailServicio emailServicio;
    
    public ResponseEntity<String> validarCredenciales(String emailUsuario, String passwordUsuario) {
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);

        if (usuario == null) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        if (!passwordEncoder.matches(passwordUsuario, usuario.getPasswordUsuario())) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok(usuario.getRolUsuario());
    }

    public boolean emailExistsUsuario(String emailUsuario) {
        return usuarioRepositorio.existsByEmailUsuario(emailUsuario);
    }

    public void registroUsuario(RegistroDto usuarioDto) {
        if (usuarioDto.getEmailUsuario() == null || usuarioDto.getEmailUsuario().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }

        UsuarioDao usuario = new UsuarioDao();
        usuario.setNombreCompletoUsuario(usuarioDto.getNombreCompletoUsuario());
        usuario.setDniUsuario(usuarioDto.getDniUsuario());
        usuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDto.getPasswordUsuario()));
        usuario.setRolUsuario("usuario");
        usuario.setFotoDniFrontalUsuario(usuarioDto.getFotoDniFrontalUsuario());
        usuario.setFotoDniTraseroUsuario(usuarioDto.getFotoDniTraseroUsuario());
        usuario.setFotoUsuario(usuarioDto.getFotoUsuario());

        usuarioRepositorio.save(usuario);
    }
    public void enviarEmailRecuperacion(String emailUsuario) {
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);

        if (usuario == null) {
            throw new IllegalArgumentException("El email no está registrado.");
        }

        // Generar un token de recuperación
        String tokenRecuperacion = UUID.randomUUID().toString();

        // Guardar el token en la base de datos
        usuario.setTokenRecuperacion(tokenRecuperacion);
        usuarioRepositorio.save(usuario);

        // Enviar el correo
        String enlaceRecuperacion = "http://localhost:8081/restablecer-password?token=" + tokenRecuperacion;
        String mensaje = "Haz clic en este enlace para restablecer tu contraseña: " + enlaceRecuperacion;
        emailServicio.enviarCorreo(emailUsuario, "Recuperación de contraseña", mensaje);
    }

    
    public void restablecerPassword(String token, String nuevaPassword) {
        UsuarioDao usuario = usuarioRepositorio.findByTokenRecuperacion(token);

        if (usuario == null) {
            throw new IllegalArgumentException("Token inválido o expirado.");
        }

        // Actualizar contraseña
        usuario.setPasswordUsuario(passwordEncoder.encode(nuevaPassword));
        usuario.setTokenRecuperacion(null); // Limpiar el token después del uso
        usuarioRepositorio.save(usuario);
    }

}
