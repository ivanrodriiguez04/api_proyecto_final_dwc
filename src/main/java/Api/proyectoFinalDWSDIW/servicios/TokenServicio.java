package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class TokenServicio {
	@Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private TokenRepositorio tokenRepositorio;

    @Autowired
    private EmailServicio emailServicio;

    public void enviarCorreoRecuperacion(String emailUsuario) {
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);
        if (usuario == null) {
            System.out.println("⚠️ Usuario no encontrado: " + emailUsuario);
            return; // No lanzar excepción, solo termina la ejecución
        }

        String token = UUID.randomUUID().toString();
        LocalDateTime nuevaFechaExpiracion = LocalDateTime.now().plusMinutes(30);

        // Buscar si ya existe un token para el usuario
        Optional<TokenDao> tokenExistente = tokenRepositorio.findByUsuario(usuario);

        if (tokenExistente.isPresent()) {
            TokenDao tokenDao = tokenExistente.get();
            tokenDao.setToken(token);
            tokenDao.setFechaExpiracion(nuevaFechaExpiracion);
            tokenRepositorio.save(tokenDao);
        } else {
            TokenDao tokenDao = new TokenDao();
            tokenDao.setToken(token);
            tokenDao.setUsuario(usuario);
            tokenDao.setFechaExpiracion(nuevaFechaExpiracion);
            tokenRepositorio.save(tokenDao);
        }

        String enlaceRecuperacion = "http://localhost:4200/cambiar-password?token=" + token;
        
        emailServicio.enviarCorreo(emailUsuario, "Recuperación de Clave",
                "Para restablecer tu contraseña, haz clic en el siguiente enlace: " + enlaceRecuperacion);
        System.out.println("✅ Correo de recuperación enviado a: " + emailUsuario);
    }

    public void restablecerClave(String token, String nuevaPassword) {
        TokenDao tokenDao = tokenRepositorio.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (tokenDao.estaExpirado()) {
            throw new RuntimeException("El token ha expirado");
        }

        UsuarioDao usuario = tokenDao.getUsuario();
        usuario.setPasswordUsuario(new BCryptPasswordEncoder().encode(nuevaPassword));
        usuarioRepositorio.save(usuario);

        tokenRepositorio.delete(tokenDao);
    }
}
