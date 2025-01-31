package Api.proyectoFinalDWSDIW.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.repositorios.TokenRepositorio;
import Api.proyectoFinalDWSDIW.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenRepositorio tokenRepositorio;
    
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

    @Transactional
    public boolean actualizarPassword(String token, String nuevaPassword) {
        // Buscar usuario por el token
        UsuarioDao usuario = usuarioRepositorio.findByToken(token);

        if (usuario == null) {
            System.err.println("ERROR: Token inválido o usuario no encontrado.");
            return false;
        }

        // Encriptar la nueva contraseña
        String passwordEncriptada = passwordEncoder.encode(nuevaPassword);
        usuario.setPasswordUsuario(passwordEncriptada);

        // Guardar la nueva contraseña en la base de datos
        usuarioRepositorio.save(usuario);

        // Eliminar el token de la base de datos después de usarlo
        tokenRepositorio.deleteByToken(token);

        System.out.println("Contraseña actualizada con éxito para el usuario: " + usuario.getEmailUsuario());
        return true;
    }
}
