package modelo;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Credito {
	@Id
	@Column(name = "codigo_credito")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int codigo_credito;
	
	@Column(name = "monto_credito")
	private double monto_credito;
	
	@Column(name = "numero_meses_credito")
	private int numero_meses_credito;
	
	@Column(name = "fecha_solicitud_credito")
	private String fecha_solicitud_credito;
	
	@Column(name = "fecha_pago_credito")
	private String fecha_pago_credito;
	
	@Column(name = "desripcion_credito")
	private String desripcion_credito;
	
	@Column(name = "estado_credito")
	private String estado_credito;
	
	@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="codigo_cuenta")
	private Cuenta codigo_cuenta;

	public int getCodigo_credito() {
		return codigo_credito;
	}

	public void setCodigo_credito(int codigo_credito) {
		this.codigo_credito = codigo_credito;
	}

	public double getMonto_credito() {
		return monto_credito;
	}

	public void setMonto_credito(double monto_credito) {
		this.monto_credito = monto_credito;
	}

	public int getNumero_meses_credito() {
		return numero_meses_credito;
	}
	
	public String getFecha_solicitud_credito() {
		return fecha_solicitud_credito;
	}

	public void setFecha_solicitud_credito(String fecha_solicitud_credito) {
		this.fecha_solicitud_credito = fecha_solicitud_credito;
	}

	public void setNumero_meses_credito(int numero_meses_credito) {
		this.numero_meses_credito = numero_meses_credito;
	}

	public String getFecha_pago_credito() {
		return fecha_pago_credito;
	}

	public void setFecha_pago_credito(String fecha_pago_credito) {
		this.fecha_pago_credito = fecha_pago_credito;
	}

	public String getDesripcion_credito() {
		return desripcion_credito;
	}

	public void setDesripcion_credito(String desripcion_credito) {
		this.desripcion_credito = desripcion_credito;
	}

	public String getEstado_credito() {
		return estado_credito;
	}

	public void setEstado_credito(String estado_credito) {
		this.estado_credito = estado_credito;
	}

	public Cuenta getCodigo_cuenta() {
		return codigo_cuenta;
	}

	public void setCodigo_cuenta(Cuenta codigo_cuenta) {
		this.codigo_cuenta = codigo_cuenta;
	}

	@Override
	public String toString() {
		return "Credito [codigo_credito=" + codigo_credito + ", monto_credito=" + monto_credito
				+ ", numero_meses_credito=" + numero_meses_credito + ", fecha_solicitud_credito="
				+ fecha_solicitud_credito + ", fecha_pago_credito=" + fecha_pago_credito + ", desripcion_credito="
				+ desripcion_credito + ", estado_credito=" + estado_credito + ", codigo_cuenta=" + codigo_cuenta + "]";
	}
	
}
