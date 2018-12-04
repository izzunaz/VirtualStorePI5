package br.com.virtual.negocio;

import java.io.IOException;

import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import br.com.virtual.beans.Pedido;

public class RedirecionaPaginaPadrao implements AuthenticationSuccessHandler {
	private Pedido pedido = new Pedido();

	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		if (roles.contains("ROLE_ADMINISTRADOR")) {
			response.sendRedirect("/LojaVirtual/admin/principal.xhtml");
		} else if (roles.contains("ROLE_CLIENTE")) {
			if (pedido.getItensPedido().size() == 0) {
				response.sendRedirect("/LojaVirtual/cliente/principal.xhtml");
			} else {
				response.sendRedirect("/LojaVirtual/cliente/carrinho.xhtml");
			}

		}
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
