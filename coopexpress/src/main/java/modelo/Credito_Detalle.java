package modelo;

import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="credito_detalle")
public class Credito_Detalle {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "codigo_detalle_credito")
	private int codigo_detalle_credito;
	
	@Column(name = "numero_cuota_detalle_credito")
	private int numero_cuota_detalle_credito;
	
	@Column(name = "fecha_detalle_credito")
	private String fecha_detalle_credito;
	
	@Column(name = "valor_detalle_credito")
	private double valor_detalle_credito;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="codigo_credito", nullable=false)
	private Credito codigo_credito;

	public int getCodigo_detalle_credito() {
		return codigo_detalle_credito;
	}

	public void setCodigo_detalle_credito(int codigo_detalle_credito) {
		this.codigo_detalle_credito = codigo_detalle_credito;
	}

	public int getNumero_cuota_detalle_credito() {
		return numero_cuota_detalle_credito;
	}

	public void setNumero_cuota_detalle_credito(int numero_cuota_detalle_credito) {
		this.numero_cuota_detalle_credito = numero_cuota_detalle_credito;
	}

	public String getFecha_detalle_credito() {
		return fecha_detalle_credito;
	}

	public void setFecha_detalle_credito(String fecha_detalle_credito) {
		this.fecha_detalle_credito = fecha_detalle_credito;
	}

	public double getValor_detalle_credito() {
		return valor_detalle_credito;
	}

	public void setValor_detalle_credito(double valor_detalle_credito) {
		this.valor_detalle_credito = valor_detalle_credito;
	}

	public Credito getCodigo_credito() {
		return codigo_credito;
	}

	public void setCodigo_credito(Credito codigo_credito) {
		this.codigo_credito = codigo_credito;
	}

	@Override
	public String toString() {
		return "Credito_Detalle [codigo_detalle_credito=" + codigo_detalle_credito + ", numero_cuota_detalle_credito="
				+ numero_cuota_detalle_credito + ", fecha_detalle_credito=" + fecha_detalle_credito
				+ ", valor_detalle_credito=" + valor_detalle_credito + ", codigo_credito=" + codigo_credito + "]";
	}

}
