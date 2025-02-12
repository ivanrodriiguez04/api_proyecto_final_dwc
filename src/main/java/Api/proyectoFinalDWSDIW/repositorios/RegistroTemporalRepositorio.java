package Api.proyectoFinalDWSDIW.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Api.proyectoFinalDWSDIW.daos.RegistroTemporalDao;

@Repository
public interface RegistroTemporalRepositorio extends JpaRepository<RegistroTemporalDao, Long> {
    Optional<RegistroTemporalDao> findByToken(String token);
}
