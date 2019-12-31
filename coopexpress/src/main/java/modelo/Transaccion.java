package modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Transaccion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_transaccion")
	private int codigo_transaccion;
	
	@Column(name = "monto_transaccion")
	private double monto_transaccion;
	
	@Column(name = "fecha_transaccion")
	@Temporal(TemporalType.DATE)
	private Date fecha_transaccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cuenta_origen_transaccion", nullable=false)
	private Cuenta cuenta_origen_transaccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cuenta_destino_transaccion", nullable=false)
	private Cuenta cuenta_destino_transaccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tipo_transaccion", nullable=false)
	private Tipo_Transaccion tipo_transaccion;

	public int getCodigo_transaccion() {
		return codigo_transaccion;
	}

	public void setCodigo_transaccion(int codigo_transaccion) {
		this.codigo_transaccion = codigo_transaccion;
	}

	public double getMonto_transaccion() {
		return monto_transaccion;
	}

	public void setMonto_transaccion(double monto_transaccion) {
		this.monto_transaccion = monto_transaccion;
	}

	public Date getFecha_transaccion() {
		return fecha_transaccion;
	}

	public void setFecha_transaccion(Date fecha_transaccion) {
		this.fecha_transaccion = fecha_transaccion;
	}

	public Cuenta getCuenta_origen_transaccion() {
		return cuenta_origen_transaccion;
	}

	public void setCuenta_origen_transaccion(Cuenta cuenta_origen_transaccion) {
		this.cuenta_origen_transaccion = cuenta_origen_transaccion;
	}

	public Cuenta getCuenta_destino_transaccion() {
		return cuenta_destino_transaccion;
	}

	public void setCuenta_destino_transaccion(Cuenta cuenta_destino_transaccion) {
		this.cuenta_destino_transaccion = cuenta_destino_transaccion;
	}

	public Tipo_Transaccion getTipo_transaccion() {
		return tipo_transaccion;
	}

	public void setTipo_transaccion(Tipo_Transaccion tipo_transaccion) {
		this.tipo_transaccion = tipo_transaccion;
	}
	
	@Override
	public String toString() {
		return "Transaccion [codigo_transaccion=" + codigo_transaccion + ", monto_transaccion=" + monto_transaccion
				+ ", fecha_transaccion=" + fecha_transaccion + ", cuenta_origen_transaccion="
				+ cuenta_origen_transaccion + ", cuenta_destino_transaccion=" + cuenta_destino_transaccion
				+ ", tipo_transaccion=" + tipo_transaccion + "]";
	}	
}
