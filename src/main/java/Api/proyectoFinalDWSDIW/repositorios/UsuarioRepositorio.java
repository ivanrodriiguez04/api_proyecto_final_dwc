package Api.proyectoFinalDWSDIW.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.UsuarioDao;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDao, Long> {
	UsuarioDao findByEmailUsuario(String emailUsuario);

	boolean existsByEmailUsuario(String emailUsuario);

    UsuarioDao findByTokenRecuperacion(String tokenRecuperacion);

}
