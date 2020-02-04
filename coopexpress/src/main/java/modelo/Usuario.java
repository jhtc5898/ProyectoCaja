package modelo;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="usuario")
public class Usuario {

	@Id
	@Column(name = "cedula_usuario")
	private String cedula;

	@Column(name = "nombre_usuario")
	private String nombre;

	@Column(name = "apellido_usuario")
	private String apellido;

	@Column(name = "fecha_nacimiento_usuario")
	private String fecha_nacimiento;

	@Column(name = "nivel_educacion_usuario")
	private String educacion;

	@Column(name = "estado_civil_usuario")
	private String estado;

	@Column(name = "genero_usuario")
	private String genero;

	@Column(name = "telefono_usuario")
	private String telefono;

	@Column(name = "ciudad_usuario")
	private String ciudad;

	@Column(name = "calle_principal_usuario")
	private String principal;

	@Column(name = "calle_secundaria_usuario")
	private String secundaria;
	
	@Column(name = "estado_usuario")
	private String estado_usuario;

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getEducacion() {
		return educacion;
	}

	public void setEducacion(String educacion) {
		this.educacion = educacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getSecundaria() {
		return secundaria;
	}

	public void setSecundaria(String secundaria) {
		this.secundaria = secundaria;
	}

	public String getEstado_usuario() {
		return estado_usuario;
	}

	public void setEstado_usuario(String estado_usuario) {
		this.estado_usuario = estado_usuario;
	}

	@Override
	public String toString() {
		return "Usuario [cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido + ", fecha_nacimiento="
				+ fecha_nacimiento + ", educacion=" + educacion + ", estado=" + estado + ", genero=" + genero
				+ ", telefono=" + telefono + ", ciudad=" + ciudad + ", principal=" + principal + ", secundaria="
				+ secundaria + ", estado_usuario=" + estado_usuario + "]";
	}

}