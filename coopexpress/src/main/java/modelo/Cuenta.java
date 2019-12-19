package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Cuenta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_cuenta")
	private int codigo_cuenta;
	
	@Column(name = "numero_cuenta")
	//@NotEmpty
	private String numero_cuenta;
	
	@Column(name = "saldo_cuenta")
	private double saldo_cuenta;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="usuario_codigo", nullable=false)
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tipo_cuenta_codigo", nullable=false)
	private Tipo_Cuenta tipo_cuenta;
	
	
	public Tipo_Cuenta getTipo_cuenta() {
		return tipo_cuenta;
	}

	public void setTipo_cuenta(Tipo_Cuenta tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}

	public int getCodigo_cuenta() {
		return codigo_cuenta;
	}

	public void setCodigo_cuenta(int codigo_cuenta) {
		this.codigo_cuenta = codigo_cuenta;
	}

	public String getNumero_cuenta() {
		return numero_cuenta;
	}

	public void setNumero_cuenta(String numero_cuenta) {
		this.numero_cuenta = numero_cuenta;
	}

	public double getSaldo_cuenta() {
		return saldo_cuenta;
	}

	public void setSaldo_cuenta(double saldo_cuenta) {
		this.saldo_cuenta = saldo_cuenta;
	}

	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Cuenta [codigo_cuenta=" + codigo_cuenta + ", numero_cuenta=" + numero_cuenta + ", saldo_cuenta="
				+ saldo_cuenta + ", usuario=" + usuario + ", tipo_cuenta=" + tipo_cuenta + "]";
	}

	


}
