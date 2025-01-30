package Api.proyectoFinalDWSDIW.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.TokenDao;
import Api.proyectoFinalDWSDIW.daos.UsuarioDao;

@Repository
public interface TokenRepositorio extends JpaRepository<TokenDao, Long> {
    Optional<TokenDao> findByToken(String token);
    Optional<TokenDao> findByUsuario(UsuarioDao usuario); // Nuevo método
}
