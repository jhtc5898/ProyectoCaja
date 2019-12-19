package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Tipo_Cuenta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_tipo_cuenta")
	private int codigo_tipo_cuenta;
	
	@Column(name = "nombre_tipo_cuenta")
	//@NotEmpty
	private String nombre_tipo_cuenta;
	
	@Column(name = "descripcion_tipo_cuenta")
	private String descripcion_tipo_cuenta;

	public int getCodigo_tipo_cuenta() {
		return codigo_tipo_cuenta;
	}

	public void setCodigo_tipo_cuenta(int codigo_tipo_cuenta) {
		this.codigo_tipo_cuenta = codigo_tipo_cuenta;
	}

	public String getNombre_tipo_cuenta() {
		return nombre_tipo_cuenta;
	}

	public void setNombre_tipo_cuenta(String nombre_tipo_cuenta) {
		this.nombre_tipo_cuenta = nombre_tipo_cuenta;
	}

	public String getDescripcion_tipo_cuenta() {
		return descripcion_tipo_cuenta;
	}

	public void setDescripcion_tipo_cuenta(String descripcion_tipo_cuenta) {
		this.descripcion_tipo_cuenta = descripcion_tipo_cuenta;
	}

	@Override
	public String toString() {
		return "TipoCuenta [codigo_tipo_cuenta=" + codigo_tipo_cuenta + ", nombre_tipo_cuenta=" + nombre_tipo_cuenta
				+ ", descripcion_tipo_cuenta=" + descripcion_tipo_cuenta + "]";
	}

	


}
