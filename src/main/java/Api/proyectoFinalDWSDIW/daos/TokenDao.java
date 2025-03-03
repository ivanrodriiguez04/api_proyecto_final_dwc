package Api.proyectoFinalDWSDIW.daos;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tokens",schema="logica_proyecto_final_dwc")
public class TokenDao {
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token", updatable = false)
    private Long idToken;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = true)
    private UsuarioDao usuario;

    @Column(nullable = false)
    private LocalDateTime fechaExpiracion;

    public boolean estaExpirado() {
        return fechaExpiracion.isBefore(LocalDateTime.now());
    }
    //Getters & Setters

	public Long getIdToken() {
		return idToken;
	}

	public void setIdToken(Long idToken) {
		this.idToken = idToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UsuarioDao getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDao usuario) {
		this.usuario = usuario;
	}

	public LocalDateTime getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(LocalDateTime fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}

}