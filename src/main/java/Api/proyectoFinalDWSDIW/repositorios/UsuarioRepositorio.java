package Api.proyectoFinalDWSDIW.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDao, Long> {
	UsuarioDao findByEmailUsuario(String emailUsuario);

	boolean existsByEmailUsuario(String emailUsuario);

	@Query("SELECT t.usuario FROM TokenDao t WHERE t.token = :token")
    UsuarioDao findByToken(@Param("token") String token);
}
