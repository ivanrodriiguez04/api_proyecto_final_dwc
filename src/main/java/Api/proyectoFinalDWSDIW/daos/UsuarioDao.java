package Api.proyectoFinalDWSDIW.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario", schema = "proyecto_final_usuarios")
public class UsuarioDao {
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false)
    private long idUsuario;

    @Column(name = "nombre_completo_usuario", length = 50)
    private String nombreCompletoUsuario;

    @Column(name = "telefono_usuario")
    private String telefonoUsuario;

    @Column(name = "rol_usuario")
    private String rolUsuario;

    @Column(name = "email_usuario", length = 50, unique = true)
    private String emailUsuario;

    @Column(name = "password_usuario", length = 60)
    private String passwordUsuario;

    @Column(name = "dni_usuario", unique = true, length = 9)
    private String dniUsuario;
    @Column(name = "confirmado", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean confirmado = false;

    public UsuarioDao() {
    	
    }
    
	public UsuarioDao(long idUsuario, String nombreCompletoUsuario, String telefonoUsuario, String rolUsuario,
			String emailUsuario, String passwordUsuario, String dniUsuario, boolean confirmado) {
		super();
		this.idUsuario = idUsuario;
		this.nombreCompletoUsuario = nombreCompletoUsuario;
		this.telefonoUsuario = telefonoUsuario;
		this.rolUsuario = rolUsuario;
		this.emailUsuario = emailUsuario;
		this.passwordUsuario = passwordUsuario;
		this.dniUsuario = dniUsuario;
		this.confirmado = confirmado;
	}

	//Getters & Setters
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getNombreCompletoUsuario() {
		return nombreCompletoUsuario;
	}
	public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
		this.nombreCompletoUsuario = nombreCompletoUsuario;
	}
	public String getTelefonoUsuario() {
		return telefonoUsuario;
	}
	public void setTelefonoUsuario(String telefonoUsuario) {
		this.telefonoUsuario = telefonoUsuario;
	}
	public String getRolUsuario() {
		return rolUsuario;
	}
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	public String getEmailUsuario() {
		return emailUsuario;
	}
	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
	public String getPasswordUsuario() {
		return passwordUsuario;
	}
	public void setPasswordUsuario(String passwordUsuario) {
		this.passwordUsuario = passwordUsuario;
	}
	public String getDniUsuario() {
		return dniUsuario;
	}
	public void setDniUsuario(String dniUsuario) {
		this.dniUsuario = dniUsuario;
	}
	public boolean isConfirmado() {
	    return confirmado;
	}
	public void setConfirmado(boolean confirmado) {
	    this.confirmado = confirmado;
	}
}
