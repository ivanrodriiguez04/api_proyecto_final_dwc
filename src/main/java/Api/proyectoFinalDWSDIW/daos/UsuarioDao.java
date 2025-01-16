package Api.proyectoFinalDWSDIW.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario", schema = "proyecto_final")
public class UsuarioDao {
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false)
    private long idUsuario;

    @Column(name = "nombre_completo_usuario", length = 50)
    private String nombreCompletoUsuario;

    @Column(name = "telefono_usuario")
    private int telefonoUsuario;

    @Column(name = "rol_usuario")
    private String rolUsuario;

    @Column(name = "email_usuario", length = 50, unique = true)
    private String emailUsuario;

    @Column(name = "password_usuario", length = 60)
    private String passwordUsuario;

    @Column(name = "dni_usuario", unique = true, length = 9)
    private String dniUsuario;

    @Column(name = "foto_dni_frontal_usuario", columnDefinition = "bytea")
    private byte[] fotoDniFrontalUsuario;

    @Column(name = "foto_dni_trasero_usuario", columnDefinition = "bytea")
    private byte[] fotoDniTraseroUsuario;

    @Column(name = "foto_usuario", columnDefinition = "bytea")
    private byte[] fotoUsuario;
    //Constructores
	public UsuarioDao(long idUsuario, String nombreCompletoUsuario, int telefonoUsuario, String rolUsuario,
			String emailUsuario, String passwordUsuario, String dniUsuario, byte[] fotoDniFrontalUsuario,
			byte[] fotoDniTraseroUsuario, byte[] fotoUsuario) {
		super();
		this.idUsuario = idUsuario;
		this.nombreCompletoUsuario = nombreCompletoUsuario;
		this.telefonoUsuario = telefonoUsuario;
		this.rolUsuario = rolUsuario;
		this.emailUsuario = emailUsuario;
		this.passwordUsuario = passwordUsuario;
		this.dniUsuario = dniUsuario;
		this.fotoDniFrontalUsuario = fotoDniFrontalUsuario;
		this.fotoDniTraseroUsuario = fotoDniTraseroUsuario;
		this.fotoUsuario = fotoUsuario;
	}
    public UsuarioDao() {
    	
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
	public int getTelefonoUsuario() {
		return telefonoUsuario;
	}
	public void setTelefonoUsuario(int telefonoUsuario) {
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
	public byte[] getFotoDniFrontalUsuario() {
		return fotoDniFrontalUsuario;
	}
	public void setFotoDniFrontalUsuario(byte[] fotoDniFrontalUsuario) {
		this.fotoDniFrontalUsuario = fotoDniFrontalUsuario;
	}
	public byte[] getFotoDniTraseroUsuario() {
		return fotoDniTraseroUsuario;
	}
	public void setFotoDniTraseroUsuario(byte[] fotoDniTraseroUsuario) {
		this.fotoDniTraseroUsuario = fotoDniTraseroUsuario;
	}
	public byte[] getFotoUsuario() {
		return fotoUsuario;
	}
	public void setFotoUsuario(byte[] fotoUsuario) {
		this.fotoUsuario = fotoUsuario;
	}
    //Metodo ToString
	@Override
	public String toString() {
		return "UsuarioDao [idUsuario=" + idUsuario + ", nombreCompletoUsuario=" + nombreCompletoUsuario
				+ ", telefonoUsuario=" + telefonoUsuario + ", rolUsuario=" + rolUsuario + ", emailUsuario="
				+ emailUsuario + ", passwordUsuario=" + passwordUsuario + ", dniUsuario=" + dniUsuario
				+"]";
	}
	
}
