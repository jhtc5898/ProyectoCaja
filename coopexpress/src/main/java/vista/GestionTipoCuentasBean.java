package vista;




import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;


import modelo.Tipo_Cuenta;


import negocio.GestionTipoCuentas;

@ManagedBean
@SessionScoped
public class GestionTipoCuentasBean {

	@Inject
	private GestionTipoCuentas gtc;
	private List<Tipo_Cuenta> tipocuentalist;
	
	private Tipo_Cuenta tipo_cuenta = new Tipo_Cuenta();
	
	public String guardar() {
		gtc.guardar(tipo_cuenta);
		tipocuentalist = gtc.getTiposCuentas();
		return "listar-tipocuenta";
	}
	
	public String eliminarTipoCuenta()
	{
		gtc.eliminar(tipo_cuenta.getCodigo_tipo_cuenta());
		return null;
	}

	public List<Tipo_Cuenta> getTipocuentalist() {
		return tipocuentalist;
	}

	public void setTipocuentalist(List<Tipo_Cuenta> tipocuentalist) {
		this.tipocuentalist = tipocuentalist;
	}

	public Tipo_Cuenta getTipo_cuenta() {
		return tipo_cuenta;
	}

	public void setTipo_cuenta(Tipo_Cuenta tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}

	
	

	
	
}
