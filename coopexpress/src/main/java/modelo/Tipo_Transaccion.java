package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tipo_Transaccion {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_tipo_transaccion")
	private int codigo_tipo_transaccion;
	
	@Column(name = "nombre_tipo_transaccion")
	private String nombre_tipo_transaccion;
	
	@Column(name = "descripcion_tipo_transaccion")
	private String descripcion_tipo_transaccion;

	public int getCodigo_tipo_transaccion() {
		return codigo_tipo_transaccion;
	}

	public void setCodigo_tipo_transaccion(int codigo_tipo_transaccion) {
		this.codigo_tipo_transaccion = codigo_tipo_transaccion;
	}

	public String getNombre_tipo_transaccion() {
		return nombre_tipo_transaccion;
	}

	public void setNombre_tipo_transaccion(String nombre_tipo_transaccion) {
		this.nombre_tipo_transaccion = nombre_tipo_transaccion;
	}

	public String getDescripcion_tipo_transaccion() {
		return descripcion_tipo_transaccion;
	}

	public void setDescripcion_tipo_transaccion(String descripcion_tipo_transaccion) {
		this.descripcion_tipo_transaccion = descripcion_tipo_transaccion;
	}
	@Override
	public String toString() {
		return "TipoTransaccion [codigo_tipo_transaccion=" + codigo_tipo_transaccion + ", nombre_tipo_transaccion="
				+ nombre_tipo_transaccion + ", descripcion_tipo_transaccion=" + descripcion_tipo_transaccion + "]";
	}
}
