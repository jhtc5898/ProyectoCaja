package vista;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import modelo.Credito;
import modelo.Credito_Detalle;
import modelo.Cuenta;
import modelo.Usuario;
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
	private List<Credito> creditos;
	private String numeroCuenta;
	private String mensaje;
	private String estado;

	
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	@PostConstruct
	public void init() {
		credito = new Credito();
		creditos = gc.getCreditos();
	}
	
	public String guardarSolicitudCredito() {
		gc.guardar(credito);
		//writePDF();
		init();
		return "solicitudes-credito";
	}
	
	public String getCreditoDisponible(String numeroCuenta) {
		credito = gc.getCreditoCuentaDisponible(numeroCuenta);
		if(credito == null) {
			setMensaje("No existen creditos registrados en esta cuenta");
		}
		return "informe-credito";
	}
	
	public void solicitar() {
		if(gc.getCreditoUnico(numeroCuenta).equals("si")) {
			Cuenta cuenta = gcu.obtenerCuentaNumeroCredito(numeroCuenta);
			if(cuenta != null) {
				credito.setCodigo_cuenta(cuenta);
				guardarSolicitudCredito();
			}else {
				FacesContext.getCurrentInstance().addMessage("formulario-credito:txtCuentaOrigen", new FacesMessage("La cuenta no existe"));
			}
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-credito:txtCuentaOrigen", new FacesMessage("El usuario ya dispone de un credito"));
		}
	}
	
	public void solicitarUsuario(String numeroCuenta) {
		if(gc.getCreditoUnico(numeroCuenta).equals("si")) {
			Cuenta cuenta = gcu.obtenerCuentaNumero(numeroCuenta);
			credito.setCodigo_cuenta(cuenta);
			guardarSolicitudCreditoUsuario();
			FacesContext.getCurrentInstance().addMessage("formulario-credito:boton", new FacesMessage("Solicitud ingresada correctamente"));
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-credito:txtCuentaOrigen", new FacesMessage("No se puede tener mas de un crédito"));
		}
	}
	
	public void guardarSolicitudCreditoUsuario() {
		gc.guardar(credito);
		//writePDF();
		init();
	}
	
	public String solicitar(String numeroCuenta) {
		Cuenta cuenta = gcu.obtenerCuentaNumero(numeroCuenta);
		credito.setCodigo_cuenta(cuenta);
		guardarSolicitudCredito();
		return "solicitudes-credito";
	}
	
	public String aprobar(Credito credito)
	{
		credito.setEstado_credito("H");
		gc.actualizarCredito(credito);
		gcd.guardarCredito(credito);
		return "solicitudes-credito";	
	}
	
	public String denegar(Credito credito)
	{
		credito.setEstado_credito("R");
		gc.actualizarCredito(credito);
		return "solicitudes-credito";	
	}
	

	 private void writePDF() {
		 
	        Document document = new Document();
	 
	        try {
	        	String path = new File(".").getCanonicalPath();
	        
	        	String FILE_NAME = path + "/Reportes/"+credito.getDesripcion_credito()+".pdf";
	        	System.out.println(FILE_NAME);
	        	
	            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
	 
	            document.open();
	 
	            Image imagen = Image.getInstance("Caja.png");  
	            document.add(imagen);
	            

	            Font fuente = new Font(FontFamily.COURIER);
	            
	            Paragraph p;
	            p = new Paragraph("Estado:  ");
	            p.add(new Chunk("Revision", FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE,BaseColor.RED)));
	            p.setAlignment(Element.ALIGN_RIGHT);
	            document.add(p);
	            
	            Paragraph saltoLinea = new Paragraph("\n",fuente);
	            document.add(saltoLinea);
	            
	            Paragraph detallesUsuario = new Paragraph("Detalles del Usuario"+"\n");
	            detallesUsuario.setAlignment(Element.ALIGN_JUSTIFIED);
	            document.add(detallesUsuario);
	            
	            
	            Paragraph cedulaDocumento = new Paragraph("CI: "+credito.getCodigo_cuenta().getUsuario().getCedula(),fuente);
	            document.add(cedulaDocumento);
	            
	            Paragraph nombreDocumento = new Paragraph("Nombres: "+credito.getCodigo_cuenta().getUsuario().getNombre() +" "+credito.getCodigo_cuenta().getUsuario().getApellido(),fuente);
	            document.add(nombreDocumento);
	            
	            Paragraph telefonoDocumento = new Paragraph("Telefono: "+credito.getCodigo_cuenta().getUsuario().getTelefono(),fuente);
	            document.add(telefonoDocumento);
	            
	            Paragraph numeroCuenta = new Paragraph("Cuenta: "+credito.getCodigo_cuenta().getNumero_cuenta(),fuente);
	            document.add(numeroCuenta);
	            
	            Paragraph correoCuenta = new Paragraph("Correo: "+credito.getCodigo_cuenta().getCorreo_cuenta(),fuente);
	            document.add(correoCuenta);
	            
	          
	            document.add(saltoLinea);
	            
	            Paragraph solicitud = new Paragraph("Detalles de la solicitud del Credito"+"\n");
	            solicitud.setAlignment(Element.ALIGN_JUSTIFIED);
	            document.add(solicitud);
	            
	            Paragraph codigoCredito = new Paragraph("Numero Credito: "+credito.getCodigo_credito(),fuente);
	            document.add(codigoCredito);
	            
	            Paragraph cantidadCredito = new Paragraph("Cantidad Solicitada: "+credito.getMonto_credito(),fuente);
	            document.add(cantidadCredito);
	            
	            Paragraph mesesCredito = new Paragraph("Meses a pagar: "+credito.getNumero_meses_credito(),fuente);
	            document.add(mesesCredito);
	            
	            Paragraph fechaCredito = new Paragraph("Fecha Solicitud: "+credito.getFecha_solicitud_credito(),fuente);
	            document.add(fechaCredito);
	            
	            Paragraph descripcionCredito = new Paragraph("Descripcion: "+credito.getDesripcion_credito(),fuente);
	            document.add(descripcionCredito);
	            
	            
	            
	            Paragraph paragraphLorem = new Paragraph();
	            
	            paragraphLorem.add("");

	            document.add(saltoLinea);
	            
	            Paragraph informacionTabla = new Paragraph("En el caso de que su solicitud sea aprobada, esta seria su tabla de amortizacion",fuente);
	            document.add(informacionTabla);
	            
	            document.add(saltoLinea);
	            
	           //TABLA AMORIZACION
	            
	            PdfPTable table = new PdfPTable(3);
	            
	            Paragraph columnaNumeroCuota = new Paragraph("Numero Cuota");
	            columnaNumeroCuota.getFont().setStyle(Font.BOLD);
	            columnaNumeroCuota.getFont().setSize(10);
	            
	            Paragraph columnaFechaPago= new Paragraph("Fecha Pago");
	            columnaFechaPago.getFont().setStyle(Font.BOLD);
	            columnaFechaPago.getFont().setSize(10);
	            
	            Paragraph columnaMonto= new Paragraph("Monto");
	            columnaMonto.getFont().setStyle(Font.BOLD);
	            columnaMonto.getFont().setSize(10);
	            
	            table.addCell(columnaNumeroCuota);
	            table.addCell(columnaFechaPago);
	            table.addCell(columnaMonto);
	            
	            List<Credito_Detalle> detalle =gcd.detallePDF(credito);
	            
	            for (int i = 0; i < detalle.size(); i++) {
	            	String monto=String.valueOf(detalle.get(i).getValor_detalle_credito());
					monto=monto.substring(0,3);
					
					String fecha=detalle.get(i).getFecha_detalle_credito();
					String cuota=String.valueOf(detalle.get(i).getNumero_cuota_detalle_credito());
					
					table.addCell(cuota);
					table.addCell(fecha);
					table.addCell(monto);
					
				}
	            
	            
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
	 
	           
	            document.add(table);
	            document.add(p3);
	            document.close();

	            //descargar PDF
	            downloadFile(FILE_NAME);
	        } catch (FileNotFoundException | DocumentException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
				e.printStackTrace();
			}
	 
	    }
	 
	 public void downloadFile(String filePath) throws IOException{
		 
		    FacesContext context = FacesContext.getCurrentInstance();  
		    HttpServletResponse response = (HttpServletResponse) context  
		                         .getExternalContext().getResponse();  
		    File file = new File(filePath);  
		    if (!file.exists()) {  
		      response.sendError(HttpServletResponse.SC_NOT_FOUND);  
		      return;  
		     }  
		    response.reset();  
		    response.setBufferSize(DEFAULT_BUFFER_SIZE);  
		    response.setContentType("application/octet-stream");  
		    response.setHeader("Content-Length", String.valueOf(file.length()));  
		    response.setHeader("Content-Disposition", "attachment;filename=\""  
		           + file.getName() + "\"");  
		    BufferedInputStream input = null;  
		    BufferedOutputStream output = null;  

		    try 
		    {  
		        input = new BufferedInputStream(new FileInputStream(file),  
		                    DEFAULT_BUFFER_SIZE);  
		        output = new BufferedOutputStream(response.getOutputStream(),  
		                        DEFAULT_BUFFER_SIZE);  
		        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];  
		        int length;  
		        while ((length = input.read(buffer)) > 0) {  
		            output.write(buffer, 0, length);  
		        }  
		    } finally 
		    {  
		        input.close();  
		        output.close();  
		    }  
		    context.responseComplete();
		    
		}

	 public String cargarCreditoRevision(Credito credito) {
			this.credito = credito;
			return "revision-credito";
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

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	public void vaciarMensaje() {
		setMensaje(" ");
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Credito> getCreditos() {
		return creditos;
	}

	public void setCreditos(List<Credito> creditos) {
		this.creditos = creditos;
	}

	
} 
