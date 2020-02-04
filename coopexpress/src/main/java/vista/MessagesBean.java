package vista;

import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class MessagesBean {

	private UIComponent componente;

	public void setMensaje(String mensaje) {
		FacesContext.getCurrentInstance().addMessage("txtMonto", new FacesMessage(mensaje));
    }
		 
	public UIComponent getComponente() {
		return componente;
	}

	public void setComponente(UIComponent componente) {
		this.componente = componente;
	}
}
