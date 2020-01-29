package negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.CreditoDAO;
import datos.CreditoDetalleDAO;
import modelo.Credito;
import modelo.Credito_Detalle;

@Stateless
public class GestionCreditoDetalle {

	@Inject
	private CreditoDetalleDAO detalleDAO;
	
	@Inject
	private CreditoDAO creditoDAO;

	public void guardar() {
		List<Credito> creditos = creditoDAO.getCreditoCodigo();
		Credito c = creditos.get(0);
		String fecha = c.getFecha_pago_credito();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date;
		Calendar cal = Calendar.getInstance();
		
		try {
			date = sdf.parse(fecha);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		double cuota = Math.ceil(c.getMonto_credito() / c.getNumero_meses_credito());

		for (int i = 1; i <= c.getNumero_meses_credito(); i++) {
			cal.add(Calendar.MONTH, 1);
			Credito_Detalle detalle_credito = new Credito_Detalle();

			Date d = cal.getTime();

			detalle_credito.setCodigo_credito(c);
			detalle_credito.setFecha_detalle_credito(sdf.format(d));
			detalle_credito.setNumero_cuota_detalle_credito(i);
			detalle_credito.setValor_detalle_credito(cuota);

			detalleDAO.insert(detalle_credito);
		}
		System.out.println(cuota);
	}

	public List<Credito_Detalle> getCreditosDetalle(int codigo_credito) {
		return detalleDAO.getCreditoDetalle(codigo_credito);
	}
	
	public List<Credito_Detalle> getPagos(String numeroCuenta){
		return detalleDAO.getPagosCuenta(numeroCuenta);
	}

}
