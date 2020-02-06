package negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import datos.CreditoDAO;
import datos.CreditoDetalleDAO;
import datos.CuentaDAO;
import modelo.Credito;
import modelo.Credito_Detalle;
import modelo.Cuenta;

@Stateless
public class GestionCreditoDetalle {

	@Inject
	private CreditoDetalleDAO detalleDAO;
	
	@Inject
	private CreditoDAO creditoDAO;
	
	@Inject
	private CuentaDAO cuentaDAO;

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
	
	public void guardarCredito(Credito credito)
	{
			String fechasCuotas = credito.getFecha_pago_credito();
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
			String strFecha = fechasCuotas;
			Date fecha = null;
			try {
			fecha = formatoDelTexto.parse(strFecha);
			for (int i = 1; i <= credito.getNumero_meses_credito(); i++) 
			{
				Calendar cal = Calendar.getInstance();
	            cal.setTime(fecha);
	            cal.add(Calendar.MONTH, i);
	            Date nuevaFecha = cal.getTime();
	            String fechafinal =formatoDelTexto.format(nuevaFecha) ;
				
	            Credito_Detalle detalle_credito = new Credito_Detalle();

				detalle_credito.setCodigo_credito(credito);
				detalle_credito.setFecha_detalle_credito(fechafinal);
				detalle_credito.setNumero_cuota_detalle_credito(i);
				detalle_credito.setValor_detalle_credito(credito.getMonto_credito() / credito.getNumero_meses_credito());

				detalleDAO.insert(detalle_credito);	
			}
			
			} catch (ParseException ex) {

			ex.printStackTrace();

			}
	}

	public List<Credito_Detalle> getCreditosDetalle(int codigo_credito) {
		return detalleDAO.getCreditoDetalle(codigo_credito);
	}
	
	public List<Credito_Detalle> getPagos(int numeroCuenta){
		return detalleDAO.getPagosCuenta(numeroCuenta);
	}

}
