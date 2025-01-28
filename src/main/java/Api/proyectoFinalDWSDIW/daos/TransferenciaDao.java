package Api.proyectoFinalDWSDIW.daos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "transferencia", schema = "proyecto_final")
public class TransferenciaDao {
	//Atributos
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false)
	private long idTransferencia;
    @Column(name = "cantidad_transferencia")
	private int cantidadTransferencia;
    @Column(name = "id_cuenta_mandatario")
	private long idCuentaMandatario;
    @Column(name = "id_cuenta_receptor")
	private long idCuentaReceptor;
    //Getters & Setters
	public long getIdTransferencia() {
		return idTransferencia;
	}
	public void setIdTransferencia(long idTransferencia) {
		this.idTransferencia = idTransferencia;
	}
	public int getCantidadTransferencia() {
		return cantidadTransferencia;
	}
	public void setCantidadTransferencia(int cantidadTransferencia) {
		this.cantidadTransferencia = cantidadTransferencia;
	}
	public long getIdCuentaMandatario() {
		return idCuentaMandatario;
	}
	public void setIdCuentaMandatario(long idCuentaMandatario) {
		this.idCuentaMandatario = idCuentaMandatario;
	}
	public long getIdCuentaReceptor() {
		return idCuentaReceptor;
	}
	public void setIdCuentaReceptor(long idCuentaReceptor) {
		this.idCuentaReceptor = idCuentaReceptor;
	}
    //Metodo ToString
	@Override
	public String toString() {
		return "TransferenciaDao [idTransferencia=" + idTransferencia + ", cantidadTransferencia="
				+ cantidadTransferencia + ", idCuentaMandatario=" + idCuentaMandatario + ", idCuentaReceptor="
				+ idCuentaReceptor + "]";
	}
	
}
