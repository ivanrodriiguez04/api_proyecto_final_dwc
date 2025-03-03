package Api.proyectoFinalDWSDIW.servicios;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Api.proyectoFinalDWSDIW.daos.RegistroTemporalDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;
import Api.proyectoFinalDWSDIW.dtos.RegistroDto;
import Api.proyectoFinalDWSDIW.repositorios.RegistroTemporalRepositorio;
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
    @Autowired
    private RegistroTemporalRepositorio registroTemporalRepositorio;

    public ResponseEntity<String> validarCredenciales(String emailUsuario, String passwordUsuario) {
        UsuarioDao usuario = usuarioRepositorio.findByEmailUsuario(emailUsuario);

        if (usuario == null || !passwordEncoder.matches(passwordUsuario, usuario.getPasswordUsuario())) {
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
    
    public void guardarRegistroTemporal(RegistroDto usuarioDto, String token, LocalDateTime fechaExpiracion) {
        System.out.println("🔍 Recibiendo datos del usuario...");

        // Crear el usuario temporal
        UsuarioDao usuario = new UsuarioDao();
        usuario.setNombreCompletoUsuario(usuarioDto.getNombreCompletoUsuario());
        usuario.setDniUsuario(usuarioDto.getDniUsuario());
        usuario.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
        usuario.setEmailUsuario(usuarioDto.getEmailUsuario());
        usuario.setPasswordUsuario(passwordEncoder.encode(usuarioDto.getPasswordUsuario()));
        usuario.setRolUsuario("usuario");
        usuario.setConfirmado(false); // Usuario aún no confirmado


        usuarioRepositorio.save(usuario);
        System.out.println("✅ Usuario guardado con éxito.");

        // Crear y guardar el registro temporal
        RegistroTemporalDao registroTemporal = new RegistroTemporalDao();
        registroTemporal.setUsuario(usuario);
        registroTemporal.setToken(token);
        registroTemporal.setFechaExpiracion(fechaExpiracion);

        registroTemporalRepositorio.save(registroTemporal);
        System.out.println("✅ Registro temporal guardado con token: " + token);
    }



    @Transactional
    public boolean confirmarRegistro(String token) {
        // 1️⃣ Buscar el registro temporal asociado al token
        Optional<RegistroTemporalDao> optionalRegistroTemporal = registroTemporalRepositorio.findByToken(token);

        if (optionalRegistroTemporal.isEmpty()) {
            System.out.println("⚠️ Token no encontrado en la base de datos.");
            return false;
        }

        RegistroTemporalDao registroTemporal = optionalRegistroTemporal.get();

        if (registroTemporal.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            System.out.println("⚠️ Token expirado.");
            return false;
        }

        // 2️⃣ Obtener el usuario asociado al registro temporal
        UsuarioDao usuario = registroTemporal.getUsuario();

        if (usuario == null) {
            System.out.println("⚠️ Usuario no encontrado en la base de datos.");
            return false;
        }

        // 3️⃣ Confirmar el usuario
        usuario.setConfirmado(true);
        usuarioRepositorio.save(usuario); // Guardamos el cambio en la base de datos
        System.out.println("✅ Usuario confirmado exitosamente: " + usuario.getEmailUsuario());

        // 4️⃣ Eliminar el registro temporal después de confirmar
        registroTemporalRepositorio.delete(registroTemporal);
        System.out.println("✅ Registro temporal eliminado.");

        return true;
    }



}
