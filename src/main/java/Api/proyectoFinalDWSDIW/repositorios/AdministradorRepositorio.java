package Api.proyectoFinalDWSDIW.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;

@Repository
public interface AdministradorRepositorio extends JpaRepository<UsuarioDao, Long> {
    /**
     * Busca un usuario por su dirección de correo electrónico.
     * 
     * @param email El email del usuario.
     * @return Un Optional que contiene el usuario si se encuentra.
     */
    Optional<UsuarioDao> findByEmailUsuario(String email);
}