package vista;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import modelo.Cuenta;
import modelo.Transaccion;
import negocio.GestionCredito;
import negocio.GestionCuentas;
import negocio.GestionTransaccion;



@ManagedBean
@SessionScoped
public class GestionTransaccionesBean {

	@Inject
	private GestionTransaccion gt;
		
	@Inject
	private GestionCuentas gc;
	private List<Transaccion> transaccionList = new ArrayList<Transaccion>();
	private Transaccion transaccion = new Transaccion();	
	private Cuenta cuenta;
	private String cuentaDestino;
	private String cuentaOrigen;
		
	
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	
	@PostConstruct
	public void init() {
		transaccion = new Transaccion();
		cuenta = new Cuenta();
		cuentaDestino = "";
		cuentaOrigen = "";
	}
	
	//Transferencia usuario
	public String guardarDeposito() {
		String var = gt.guardarTransaccionDeposito(transaccion, cuenta, cuentaDestino);
		if(var.equals("cuenta")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("La cuenta destino no existe"));
		}else if(var.equals("dinero")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtMonto", new FacesMessage("No dispone del dinero suficiente"));
		}else if(var.equals("exito")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:boton", new FacesMessage("Transferencia realizada correctamente"));
			init();
		}
		init();
		transaccion.setMonto_transaccion(0);
		return null;
	}
	
	//Deposito a una cuenta CAJERA
	public String guardarDepositoCajera() {
		String var = gt.guardarTransaccionDepositoCajera(transaccion, cuentaDestino);
		if(var.equals("cuenta")) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("La cuenta no existe"));
			init();
		}else if(var.equals("exito")){
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:boton", new FacesMessage("Depósito realizado correctamente"));
			init();
		}
		init();
		return null;
	}
	
	//Retiro de una cuenta CAJERA
	public String guardarRetiroCajera() {
		String var = gt.guardarTransaccionRetiroCajera(transaccion, cuentaOrigen);
		if(var.equals("dinero")) {
			FacesContext.getCurrentInstance().addMessage("retiro:txtMonto", new FacesMessage("La cuenta no dispone de suficiente dinero"));
			init();
		}else if(var.equals("cuenta")) {
			FacesContext.getCurrentInstance().addMessage("retiro:txtCuentaOrigen", new FacesMessage("La cuenta no existe"));
			init();
		}else if(var.equals("exito")) {
			FacesContext.getCurrentInstance().addMessage("retiro:boton", new FacesMessage("Transaccion realizada correctamente"));
			init();
		}
		init();
		return null;
	}
	
	//Datos desde la página
	public String deposito(String cuentaOrigen) {
		if(!cuentaOrigen.equals(cuentaDestino)) {
			this.cuenta.setNumero_cuenta(cuentaOrigen);
			guardarDeposito();
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:txtCuentaDestino", new FacesMessage("No se puede realizar una transferencia hacia su propia cuenta"));
		}
		
		return null;
	}
	
	public String retiro(String cuentaOrigen) {
		this.cuenta.setNumero_cuenta(cuentaOrigen);
		guardarRetiroCajera();
		return null;
	}
	
	public void cargarUsuario() {
		Cuenta cuenta = gc.obtenerCuentaNumero(cuentaOrigen);
		if(cuenta != null) {
			FacesContext.getCurrentInstance().addMessage("retiro:usuario", new FacesMessage(cuenta.getUsuario().getNombre()+" "+cuenta.getUsuario().getApellido()));
		}else {
			FacesContext.getCurrentInstance().addMessage("retiro:usuario", new FacesMessage("No encontrado"));
		}
		
	}
	
	public void cargarUsuario1() {
		Cuenta cuenta = gc.obtenerCuentaNumero(cuentaDestino);
		if(cuenta != null) {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:usuario", new FacesMessage(cuenta.getUsuario().getNombre()+" "+cuenta.getUsuario().getApellido()));
		}else {
			FacesContext.getCurrentInstance().addMessage("formulario-deposito:usuario", new FacesMessage("No encontrado"));
		}
	}
	
	//GENERAR PDF ESTADO CUENTA
	
	public void generarEstadoCuentaPDF(String numeroCuenta) {
		  Document document = new Document();
		  
		  Cuenta cuenta=gc.obtenerCuentaNumero(numeroCuenta);
		  System.out.println(cuenta);
		  
		  try {
	        	String path = new File(".").getCanonicalPath();
	        
	        	String FILE_NAME = path + "/Reportes/informe"+cuenta.getNumero_cuenta()+".pdf";
	        	System.out.println(FILE_NAME);
	        	
	            PdfWriter.getInstance(document, new FileOutputStream(new File(FILE_NAME)));
	 
	            document.open();
	 
	            Font fuente = new Font(FontFamily.COURIER);
	            Paragraph saltoLinea = new Paragraph("\n",fuente);
	            
	            
	            Image imagen = Image.getInstance("Caja.png");  
	            document.add(imagen);
	            Paragraph detallesUsuario = new Paragraph("Detalles del Usuario"+"\n");
	            detallesUsuario.setAlignment(Element.ALIGN_JUSTIFIED);
	            document.add(detallesUsuario);
	            Paragraph cedulaDocumento = new Paragraph("CI: "+cuenta.getUsuario().getCedula(),fuente);
	            document.add(cedulaDocumento);
	            
	            Paragraph nombreDocumento = new Paragraph("Nombres: "+cuenta.getUsuario().getNombre() +" "+cuenta.getUsuario().getApellido(),fuente);
	            document.add(nombreDocumento);
	            
	            Paragraph telefonoDocumento = new Paragraph("Teléfono: "+cuenta.getUsuario().getTelefono(),fuente);
	            document.add(telefonoDocumento);
	            
	            Paragraph numero = new Paragraph("Cuenta: "+cuenta.getNumero_cuenta(),fuente);
	            document.add(numero);
	            
	            Paragraph correoCuenta = new Paragraph("Correo: "+cuenta.getCorreo_cuenta(),fuente);
	            document.add(correoCuenta);	
	            
	            Paragraph saldoCuenta = new Paragraph("Saldo: "+cuenta.getSaldo_cuenta(),fuente);
	            document.add(saldoCuenta);	
	            document.add(saltoLinea);
	            
	            Paragraph informacionDepositos = new Paragraph("Depositos Recibidos ",fuente);
	            document.add(informacionDepositos);
	            
	            document.add(saltoLinea);
	            
	            //generar tabla PDF
	            PdfPTable table = new PdfPTable(3);
	            
	            Paragraph columnaNumeroCuota = new Paragraph("Monto");
	            columnaNumeroCuota.getFont().setStyle(Font.BOLD);
	            columnaNumeroCuota.getFont().setSize(10);
	            
	            Paragraph columnaFechaPago= new Paragraph("Fecha y Hora");
	            columnaFechaPago.getFont().setStyle(Font.BOLD);
	            columnaFechaPago.getFont().setSize(10);
	            
	            Paragraph columnaMonto= new Paragraph("Desde la Cuenta");
	            columnaMonto.getFont().setStyle(Font.BOLD);
	            columnaMonto.getFont().setSize(10);
	            
	            table.addCell(columnaNumeroCuota);
	            table.addCell(columnaFechaPago);
	            table.addCell(columnaMonto);
	            
	            List<Transaccion> depositosRecibidos = gt.getDepositosRecibidos(numeroCuenta);
	            
	            if(depositosRecibidos ==null || depositosRecibidos.size()==0) {
	            	
//	            	Paragraph titulo = new Paragraph();
//	                titulo.setAlignment(Paragraph.ALIGN_CENTER);
//	                titulo.setFont(FontFactory.getFont("Times New Roman", 24, Font.BOLD, BaseColor.RED));
//	                titulo.add("***LISTADO DE CLIENTES***");
	            	
	            	
	            	 Paragraph noDeposito = new Paragraph();
	            	 noDeposito.setAlignment(Element.ALIGN_CENTER);
	            	 noDeposito.setFont(FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.ORANGE));
	            	 noDeposito.add("Al momento usted no cuenta con Depositos");
	 	             document.add(noDeposito);
	 	             document.add(saltoLinea);
	            }else {
	            	for (int i = 0; i < depositosRecibidos.size(); i++) {
						String monto=String.valueOf(depositosRecibidos.get(i).getMonto_transaccion());
//						monto=monto.substring(0,4);
						
						double val=depositosRecibidos.get(i).getMonto_transaccion();
		            	DecimalFormat formato1 = new DecimalFormat("#.00");
		            	monto=formato1.format(val);
		            	
						String fecha=depositosRecibidos.get(i).getFecha_transaccion();
						String descripcion=depositosRecibidos.get(i).getDescripcion_transaccion();
						
						table.addCell(monto);
						table.addCell(fecha);
						table.addCell(descripcion);
					}
	            	document.add(table);
		            document.add(saltoLinea);
		            
	            	
	            }
	            Font f = new Font();
	            f.setFamily(FontFamily.COURIER.name());
	            f.setStyle(Font.BOLDITALIC);
	            f.setSize(8);
	            
	            Paragraph p3 = new Paragraph();
	            p3.setFont(f);
	            p3.add("Archivo De Gran Importacia");
	 
	            
	            
	            //RETIROS REALIZADOS
	        
	            Paragraph informacionRetiros= new Paragraph("Retiros Realizados ",fuente);
	            document.add(informacionRetiros);
	            document.add(saltoLinea);
	            
	            PdfPTable table2 = new PdfPTable(2);
	            
	            Paragraph columnaMontoRetiro= new Paragraph("Monto");
	            columnaMontoRetiro.getFont().setStyle(Font.BOLD);
	            columnaMontoRetiro.getFont().setSize(10);
	            
	            Paragraph columnaFechaRetiro= new Paragraph("Fecha y Hora");
	            columnaFechaRetiro.getFont().setStyle(Font.BOLD);
	            columnaFechaRetiro.getFont().setSize(10);
	            
	            table2.addCell(columnaMontoRetiro);
	            table2.addCell(columnaFechaRetiro);
	            
	            
	            List<Transaccion> retirosRealizados = gt.getRetirosHechos(numeroCuenta);
	            
	            if(retirosRealizados ==null || retirosRealizados.size()==0) {
	            	
	            	 Paragraph noRetiro = new Paragraph();
	            	 noRetiro.setAlignment(Element.ALIGN_CENTER);
	            	 noRetiro.setFont(FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.ORANGE));
	            	 noRetiro.add("Al momento usted no cuenta con ningun Retiro");
	 	             document.add(noRetiro);
	 	            document.add(saltoLinea);
	            }else {
	            	for (int i = 0; i < retirosRealizados.size(); i++) {
		            	String monto=String.valueOf(retirosRealizados.get(i).getMonto_transaccion());
//		            	monto=monto.substring(0,3);
		            	
		            	double val=retirosRealizados.get(i).getMonto_transaccion();
		            	DecimalFormat formato1 = new DecimalFormat("#.00");
		            	monto=formato1.format(val);
						
						String fecha=retirosRealizados.get(i).getFecha_transaccion();
						
						table2.addCell(monto);
						table2.addCell(fecha);
						
					}
		            document.add(table2);
		            document.add(saltoLinea);
		            	
	            }
	            
	            //DEPOSITOS RELIZADOS CAJERA
	            
	            Paragraph informacionCajera= new Paragraph("Depositos Realizados en la Matriz ",fuente);
	            document.add(informacionCajera);
	            document.add(saltoLinea);
	            
	            PdfPTable table4 = new PdfPTable(3);
	            
	            Paragraph columnaMontoCajera= new Paragraph("Monto");
	            columnaMontoCajera.getFont().setStyle(Font.BOLD);
	            columnaMontoCajera.getFont().setSize(10);
	            
	            Paragraph columnaFechaCajera= new Paragraph("Fecha y Hora");
	            columnaFechaCajera.getFont().setStyle(Font.BOLD);
	            columnaFechaCajera.getFont().setSize(10);
	            
	            Paragraph columnaDescripcionCajera= new Paragraph("Descripcion");
	            columnaDescripcionCajera.getFont().setStyle(Font.BOLD);
	            columnaDescripcionCajera.getFont().setSize(10);
	            
	            table4.addCell(columnaMontoCajera);
	            table4.addCell(columnaFechaCajera);
	            table4.addCell(columnaDescripcionCajera);
	            
	            List<Transaccion> depositoCajera = gt.getDepositosCajera(numeroCuenta);
	            
	            if (depositoCajera == null || depositoCajera.size()==0) {
	            	Paragraph noCajera = new Paragraph();
	            	noCajera.setAlignment(Element.ALIGN_CENTER);
	            	noCajera.setFont(FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.ORANGE));
	            	noCajera.add("No a realizado ningun deposito en nuestra Matriz");
	 	            document.add(noCajera);
	 	            document.add(saltoLinea);
				} else {

					for (int i = 0; i < depositoCajera.size(); i++) {
						String monto=String.valueOf(depositoCajera.get(i).getMonto_transaccion());
//						monto=monto.substring(0,4);
						double val=depositoCajera.get(i).getMonto_transaccion();
		            	DecimalFormat formato1 = new DecimalFormat("#.00");
		            	monto=formato1.format(val);
						
						String fecha=depositoCajera.get(i).getFecha_transaccion();
						String descripcion= depositoCajera.get(i).getDescripcion_transaccion();
						System.out.println(descripcion);
						table4.addCell(monto);
						table4.addCell(fecha);
						table4.addCell(descripcion);
					}
					document.add(table4);
		            document.add(saltoLinea);
				}
	            
	            //TRANSFRENCIAS REALIZADAS
	            
	            Paragraph informacionTransferencias= new Paragraph("Transferencias Realizadas",fuente);
	            document.add(informacionTransferencias);
	            document.add(saltoLinea);
	            
	            PdfPTable table3 = new PdfPTable(3);
	            
	            Paragraph columnaMontoTransferencia= new Paragraph("Monto Depositado");
	            columnaMontoTransferencia.getFont().setStyle(Font.BOLD);
	            columnaMontoTransferencia.getFont().setSize(10);
	            
	            Paragraph columnaFechaTransferencia= new Paragraph("Fecha y Hora");
	            columnaFechaTransferencia.getFont().setStyle(Font.BOLD);
	            columnaFechaTransferencia.getFont().setSize(10);
	            
	            Paragraph columnaCuentaTransferencia= new Paragraph("A la Cuenta");
	            columnaCuentaTransferencia.getFont().setStyle(Font.BOLD);
	            columnaCuentaTransferencia.getFont().setSize(10);
	            
	            table3.addCell(columnaMontoTransferencia);
	            table3.addCell(columnaFechaTransferencia);
	            table3.addCell(columnaCuentaTransferencia);
	            
	            List<Transaccion> transferencias = gt.getDepositosRealizados(numeroCuenta);
	            
	            if(transferencias ==null || transferencias.size()==0) {
	            	
	            	Paragraph noTransferencia = new Paragraph();
	            	noTransferencia.setAlignment(Element.ALIGN_CENTER);
	            	noTransferencia.setFont(FontFactory.getFont("Arial", 11, Font.BOLD, BaseColor.ORANGE));
	            	noTransferencia.add("Al momento no cuenta con ninguna Tranferencia Bancaria");
	 	             document.add(noTransferencia);
	 	            document.add(saltoLinea);
	 	             
	            }else {
	            	
	 	            for (int i = 0; i < transferencias.size(); i++) {
	 	            	String monto=String.valueOf(transferencias.get(i).getMonto_transaccion());
//	 	            	monto=monto.substring(0,4);
	 	            	
	 	            	double val=transferencias.get(i).getMonto_transaccion();
		            	DecimalFormat formato1 = new DecimalFormat("#.00");
		            	monto=formato1.format(val);
	 					
	 					String fecha=transferencias.get(i).getFecha_transaccion();
	 					String descripcion=transferencias.get(i).getDescripcion_transaccion();
	 					
	 					table3.addCell(monto);
	 					table3.addCell(fecha);
	 					table3.addCell(descripcion);
	 					
	 				}
	 	            document.add(table3);
	 	            document.add(saltoLinea);
	            }
	            
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

	
	
	public String getTransacciones() {
		transaccionList = gt.getTransacciones();
		return null;
	}
	
	public void getDepositosRecibidos(String numeroCuenta) {
		transaccionList = gt.getDepositosRecibidos(numeroCuenta);
	}

	public void getDepositosRealizados(String numeroCuenta) {
		transaccionList = gt.getDepositosRealizados(numeroCuenta);
	}
	
	public void getRetirosHechos(String numeroCuenta) {
		transaccionList = gt.getRetirosHechos(numeroCuenta);
	}
	
	/*Getters y Setters*/
	public List<Transaccion> getTransaccionList() {
		return transaccionList;
	}

	public void setTransaccionList(List<Transaccion> transaccionList) {
		this.transaccionList = transaccionList;
	}

	public Transaccion getTransaccion() {
		return transaccion;
	}

	public void setTransaccion(Transaccion transaccion) {
		this.transaccion = transaccion;
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public String getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	
	
}
