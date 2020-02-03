package utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			
			// Si es una pagina que NO SE NECESITA LOGUEAR se permite
			if (noLoguear(reqURI)) {
				chain.doFilter(request, response);
			}
			// Si es una pagina de ADMIN
			else if (soloAdmin(reqURI) && (ses != null && ses.getAttribute("username") != null)) {
				String userName = (String) ses.getAttribute("rol");
				if (userName.equals("2")) {
					chain.doFilter(request, response);
				}else {
					resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
				}
			}
			// Si es una pagina de USUARIO
			else if (soloUsuario(reqURI) && (ses != null && ses.getAttribute("username") != null)) {
				String userName = (String) ses.getAttribute("rol");
				if (userName.equals("1")) {
					chain.doFilter(request, response);
				}else {
					resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
				}
			}
			// Si es una pagina de CAJERA
						else if (soloCajera(reqURI) && (ses != null && ses.getAttribute("username") != null))
						{
							String userName = (String) ses.getAttribute("rol");
							if (userName.equals("3")) {
								chain.doFilter(request, response);
							}else {
								resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
							}
						}
			
			
			else {
				resp.sendRedirect(reqt.getContextPath() + "/faces/login.xhtml");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static final String[] noLogin = { 
			"/faces/register.xhtml", "/faces/coopexpress.xhtml", "/faces/login.xhtml",
			"/faces/404.xhtml" };

	private static final String[] soloAdmin = { 
			"/faces/admin/404.xhtml", "/faces/admin/actualizar-cuenta.xhtml",
			"/faces/admin/actualizar-tipo-cuenta.xhtml", "/faces/admin/actualizar-tipo-transaccion.xhtml",
			"/faces/admin/actualizar-usuario.xhtml", "/faces/admin/crear-cuenta.xhtml",
			"/faces/admin/crear-tipo-cuenta.xhtml", "/faces/admin/crear-tipo-transaccion.xhtml",
			"/faces/admin/crear-usuario.xhtml", "/faces/admin/eliminar-tipo-cuenta.xhtml",
			"/faces/admin/eliminar-tipo-transaccion.xhtml", "/faces/admin/listar-cuenta.xhtml",
			"/faces/admin/listar-tipo-cuenta.xhtml", "/admin/listar-usuario.xhtml",
			"/faces/admin/lista-tipo-transaccion.xhtml"
	};

	private static final String[] soloUsuario = { 
			"/faces/Usuario/404.xhtml", "/faces/Usuario/actualizar-cuenta.xhtml",
			"/faces/Usuario/actualizar-usuario.xhtml", "/faces/Usuario/buscar-usuario.xhtml",
			"/faces/Usuario/deposito.xhtml", "/faces/Usuario/home-Usuario.xhtml", "/faces/Usuario/listar-usuario.xhtml",
			"/faces/Usuario/perfil.xhtml", "/faces/Usuario/retiro.xhtml", "/faces/Usuario/solicitud-credito.xhtml",
			"/faces/Usuario/informe-credito.xhtml", "/faces/Usuario/tabla-pagos.xhtml", 
	};
	
	private static final String[] soloCajera = { 
			"/faces/cajera/deposito.xhtml","/faces/cajera/retiro.xhtml","/faces/cajera/home-cajera.xhtml",
			"/faces/cajera/solicitud-credito.xhtml",
		
	};

	private boolean noLoguear(String url) {
		for (String noLogin : noLogin) {
			if (url.contains(noLogin)) {
				return true;
			}
		}
		return false;
	}

	private boolean soloAdmin(String url) {
		for (String admin : soloAdmin) {
			if (url.indexOf(admin) >= 0) {
				return true;
			}
		}
		return false;
	}

	private boolean soloUsuario(String url) {
		for (String usuario : soloUsuario) {
			if (url.indexOf(usuario) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	private boolean soloCajera(String url) {
		for (String cajera : soloCajera) {
			if (url.indexOf(cajera) >= 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {

	}

}