package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="transaccion")
public class Transaccion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_transaccion")
	private int codigo_transaccion;
	
	@Column(name = "monto_transaccion")
	private double monto_transaccion;
	
	@Column(name = "fecha_transaccion")
	private String fecha_transaccion;
	
	@Column(name = "descripcion_transaccion")
	private String descripcion_transaccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cuenta_transaccion", nullable=false)
	private Cuenta cuenta_transaccion;
	
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

	public String getFecha_transaccion() {
		return fecha_transaccion;
	}

	public void setFecha_transaccion(String fecha_transaccion) {
		this.fecha_transaccion = fecha_transaccion;
	}

	public String getDescripcion_transaccion() {
		return descripcion_transaccion;
	}

	public void setDescripcion_transaccion(String descripcion_transaccion) {
		this.descripcion_transaccion = descripcion_transaccion;
	}

	public Cuenta getCuenta_transaccion() {
		return cuenta_transaccion;
	}

	public void setCuenta_transaccion(Cuenta cuenta_transaccion) {
		this.cuenta_transaccion = cuenta_transaccion;
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
				+ ", fecha_transaccion=" + fecha_transaccion + ", descripcion_transaccion=" + descripcion_transaccion
				+ ", cuenta_transaccion=" + cuenta_transaccion + ", tipo_transaccion=" + tipo_transaccion + "]";
	}
	
}
