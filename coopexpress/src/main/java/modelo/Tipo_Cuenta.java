package modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Tipo_Cuenta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_tipo_cuenta")
	private int codigo_tipo_cuenta;
	
	@Column(name = "nombre_tipo_cuenta")
	private String nombre_tipo_cuenta;
	
	@Column(name = "descripcion_tipo_cuenta")
	private String descripcion_tipo_cuenta;
	
	@OneToMany(mappedBy="tipo_cuenta", cascade = CascadeType.ALL)
    private List<Cuenta> cuentas;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codigo_tipo_cuenta;
		result = prime * result + ((descripcion_tipo_cuenta == null) ? 0 : descripcion_tipo_cuenta.hashCode());
		result = prime * result + ((nombre_tipo_cuenta == null) ? 0 : nombre_tipo_cuenta.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tipo_Cuenta other = (Tipo_Cuenta) obj;
		if (codigo_tipo_cuenta != other.codigo_tipo_cuenta)
			return false;
		if (descripcion_tipo_cuenta == null) {
			if (other.descripcion_tipo_cuenta != null)
				return false;
		} else if (!descripcion_tipo_cuenta.equals(other.descripcion_tipo_cuenta))
			return false;
		if (nombre_tipo_cuenta == null) {
			if (other.nombre_tipo_cuenta != null)
				return false;
		} else if (!nombre_tipo_cuenta.equals(other.nombre_tipo_cuenta))
			return false;
		return true;
	}
	

	


}