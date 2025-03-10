package Api.proyectoFinalDWSDIW.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Api.proyectoFinalDWSDIW.daos.CuentaDao;

public interface CuentaRepositorio extends JpaRepository<CuentaDao, Long> {
    /**
     * Encuentra todas las cuentas asociadas a un usuario específico.
     * 
     * @param idUsuario ID del usuario.
     * @return Lista de cuentas del usuario.
     */
    List<CuentaDao> findByUsuarioIdUsuario(Long idUsuario);

    /**
     * Busca una cuenta por su IBAN.
     * 
     * @param ibanCuenta IBAN de la cuenta.
     * @return Un Optional que contiene la cuenta si se encuentra.
     */
    Optional<CuentaDao> findByIbanCuenta(String ibanCuenta);
}
