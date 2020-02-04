package vista;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.Credito;
import modelo.Credito_Detalle;
import modelo.Cuenta;
import negocio.GestionCredito;
import negocio.GestionCreditoDetalle;
import negocio.GestionCuentas;

@ManagedBean
@SessionScoped
public class GestionCreditoBean {

	@Inject
	private GestionCredito gc;
	
	@Inject
	private GestionCreditoDetalle gcd;
	
	@Inject
	private GestionCuentas gcu;
	
	private Credito credito =  new Credito();
	private List<Credito_Detalle> creditoDetalles = new ArrayList<Credito_Detalle>();
	private String numeroCuenta;

	@PostConstruct
	public void init() {
		credito = new Credito();
	}
	
	public String guardarSolicitudCredito() {
		gc.guardar(credito);
		writePDF();
		init();
		return "informe-credito";
	}
		
	public String getCreditoDisponible(String numeroCuenta) {
		credito = gc.getCreditoCuentaDisponible(numeroCuenta);
		return "informe-credito";
	}
	
	public String solicitar(String numeroCuenta) {
		Cuenta cuenta = gcu.obtenerCuentaNumero(numeroCuenta);
		credito.setCodigo_cuenta(cuenta);
		guardarSolicitudCredito();
		return null;
	}
	
	
	 private void writePDF() {
		 
	        Document document = new Document();
	 
	        try {
	        	String path = new File(".").getCanonicalPath();
	        	String FILE_NAME = path + "/"+credito.getDesripcion_credito()+".pdf";
	        	System.out.println(FILE_NAME);
	        	
	            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
	 
	            document.open();
	 
	            Paragraph paragraphHello = new Paragraph();
	            paragraphHello.add("Solicitudo De Credto");
	            paragraphHello.setAlignment(Element.ALIGN_JUSTIFIED);
	 
	            document.add(paragraphHello);
	 
	            Paragraph paragraphLorem = new Paragraph();
	            paragraphLorem.add("Esta Es Una Prueba De Sus Accion Dentro De La Coperativa CoopExpress: Usted Solicito Un Monto : "
	            		+ credito.getMonto_credito()
	            		+ " Con Una Cantidad De Cuotas : " + credito.getNumero_meses_credito()
	            		+ " En La Fecha " + credito.getFecha_solicitud_credito()
	            		+ " Esta Solicitud Se Encuentra En Estado : " + credito.getEstado_credito()
	            		);
	            
	            java.util.List<Element> paragraphList = new ArrayList<>();
	            
	            paragraphList = paragraphLorem.breakUp();
	 
	            Font f = new Font();
	            f.setFamily(FontFamily.COURIER.name());
	            f.setStyle(Font.BOLDITALIC);
	            f.setSize(8);
	            
	            Paragraph p3 = new Paragraph();
	            p3.setFont(f);
	            p3.addAll(paragraphList);
	            p3.add("Archivo Den Gran Importacia");
	 
	            //document.add(paragraphLorem);
	            document.add(p3);
	            document.close();
	 
	        } catch (FileNotFoundException | DocumentException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}
	 
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void getPagosCredito(String numeroCuenta) {
		creditoDetalles = gcd.getPagos(Integer.parseInt(numeroCuenta));
	}

	public Credito getCredito() {
		return credito;
	}

	public void setCredito(Credito credito) {
		this.credito = credito;
	}

	public List<Credito_Detalle> getCreditoDetalles() {
		return creditoDetalles;
	}

	public void setCreditoDetalles(List<Credito_Detalle> creditoDetalles) {
		this.creditoDetalles = creditoDetalles;
	}

	public String getNumeroCuenta() {
		return numeroCuenta;
	}

	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
} 
