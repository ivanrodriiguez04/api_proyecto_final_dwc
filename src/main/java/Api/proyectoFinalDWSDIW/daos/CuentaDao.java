package Api.proyectoFinalDWSDIW.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuenta", schema = "proyecto_final")
public class CuentaDao {
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuenta", updatable = false)
	private long idCuenta;
	@Column(name="tipo_cuenta")
	private String tipoCuenta;
	@Column(name="iban_cuenta")
	private String ibanCuenta;
	@Column(name="dinero_cuenta")
	private int dineroCuenta;
	@Column(name="id_usuario")
	private long idUsuario;
	//Getters & Setters
	public long getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(long idCuenta) {
		this.idCuenta = idCuenta;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	public String getIbanCuenta() {
		return ibanCuenta;
	}
	public void setIbanCuenta(String ibanCuenta) {
		this.ibanCuenta = ibanCuenta;
	}
	public int getDineroCuenta() {
		return dineroCuenta;
	}
	public void setDineroCuenta(int dineroCuenta) {
		this.dineroCuenta = dineroCuenta;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	//Metodo ToString
	@Override
	public String toString() {
		return "CuentasDao [idCuenta=" + idCuenta + ", tipoCuenta=" + tipoCuenta + ", ibanCuenta=" + ibanCuenta
				+ ", dineroCuenta=" + dineroCuenta + ", idUsuario=" + idUsuario + "]";
	}
	
}
