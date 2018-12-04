package br.com.virtual.negocio;

import java.io.Serializable;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.virtual.beans.ItensPedido;
import br.com.virtual.beans.FormaPgto;
import br.com.virtual.beans.Pedido;
import br.com.virtual.beans.Pessoa;
import br.com.virtual.beans.Produto;
import br.com.virtual.persistencia.PedidoDAO;
import br.com.virtual.persistencia.PessoaDAO;

@ManagedBean(name = "carrinho")
@SessionScoped
public class CarrinhoCtrl implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	private Produto produto = new Produto();
	private FormaPgto formaPgto = new FormaPgto();
	private Pessoa pessoa;
	private boolean desabilitarParcelas = true;

	// Adicionar produtos ao carrinho
	public String actionAddProdutosAoCarrinho(Produto produto) {
		ItensPedido itensPedido = new ItensPedido();
		itensPedido.setPedido(pedido);
		itensPedido.setProduto(produto);
		itensPedido.setIpe_valorUnit(produto.getPreco());
		itensPedido.setIpe_qtde(1);
		itensPedido.setIpe_subTotal(produto.getPreco());
		this.pedido.getItensPedido().add(itensPedido);
		return "/publico/index?faces-redirect=true /cliente/principal?faces-redirect=true";
	}

	public Float calcValorTotal() {
		Float total = 0f;

		if (pedido.getItensPedido().isEmpty()) {
			return total;
		} else {
			for (int i = 0; i < pedido.getItensPedido().size(); i++) {
				total += (pedido.getItensPedido().get(i).getIpe_subTotal());
			}
			return total;
		}
	}

	public String actionAddOne(ItensPedido itensPedido) {
		for (int i = 0; i < pedido.getItensPedido().size(); i++) {
			if (itensPedido.getProduto().getNome().equals(pedido.getItensPedido().get(i).getProduto().getNome())) {
				itensPedido.setIpe_qtde(itensPedido.getIpe_qtde() + 1);
			}
		}
		return "/publico/carrinho?faces-redirect=true";
	}

	public String actionRemoveOne(ItensPedido itensPedido) {
		for (int i = 0; i < pedido.getItensPedido().size(); i++) {
			if (itensPedido.getProduto().getNome().equals(pedido.getItensPedido().get(i).getProduto().getNome())) {
				itensPedido.setIpe_qtde(itensPedido.getIpe_qtde() - 1);
			}
		}
		return "/publico/carrinho?faces-redirect=true";
	}

	// Lê a lista de produtos e soma todos os preços
	public void valorDoPedido() {
		float valorTotal = 0;
		for (int i = 0; i < pedido.getItensPedido().size(); i++) {
			valorTotal += pedido.getItensPedido().get(i).getIpe_valorUnit()
					* pedido.getItensPedido().get(i).getIpe_qtde();
		}
		this.pedido.setPed_total(valorTotal);
	}

	public String actionPedido() {
		actionAtivarProduto();
		valorDoPedido();
		return "/publico/carrinho?faces-redirect=true";
	}

	public void actionAtivarProduto() {
		float total = 0;
		float soma = 0;
		for (int i = 0; i < pedido.getItensPedido().size(); i++) {
			total = soma + pedido.getItensPedido().get(i).getProduto().getPreco();
			pedido.getItensPedido().get(i).setIpe_valorUnit(pedido.getItensPedido().get(i).getProduto().getPreco());
			pedido.getItensPedido().get(i).setIpe_subTotal(pedido.getItensPedido().get(i).getProduto().getPreco());
			pedido.getItensPedido().get(i).setIpe_qtde(1);
		}
		pedido.setPed_total(total);
	}

	public int getQtdeTotal() {
		if (pedido.getItensPedido().isEmpty()) {
			return 0;
		}
		int total = 0;
		for (int i = 0; i < pedido.getItensPedido().size(); i++) {
			total += (this.pedido.getItensPedido().get(i).getIpe_qtde());
		}
		return total;
	}

	public String actionGravarPedido() {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			Pessoa clienteLogado = PessoaDAO.searchUsuarioLogado(getUsuarioLogado());

			pedido.setPessoa(clienteLogado);
			pedido.setFormaPgto(formaPgto);
			pedido.setPed_dataEmissao(new Date());
			pedido.setPed_status("ABERTO");
			pedido.setPed_dataAutorizacao(new Date());
			PedidoDAO.inserir(pedido);
		} catch (Exception e) {
			System.out.println("Erro ao salvar o pedido!" + e.getMessage());
		}

		pedido.getItensPedido().clear();
		context.addMessage(null, new FacesMessage("Sucesso", "Compra realizada com sucesso"));
		return "/publico/index?faces-redirect=true";
	}

	// Pega a quantidade de produtos que o cliente solicitou e o preço (subtotal)
	public String calcQuantidadeProduto(ItensPedido itensPedido) {
		itensPedido.setIpe_valorUnit(itensPedido.getProduto().getPreco());
		itensPedido.setIpe_subTotal(itensPedido.getIpe_qtde() * itensPedido.getIpe_valorUnit());
		valorDoPedido();
		return null;
	}

	public void setDesabilitarParcelas(boolean desabilitarParcelas) {
		this.desabilitarParcelas  = desabilitarParcelas;
	}

	// Para saber a forma de pagamento (credito ou debito ou boleto)
	public String actionDefinirParcelas() { 
		if (this.formaPgto.getId() == 6) {
			this.desabilitarParcelas = false;
		} else {
			this.desabilitarParcelas = true;
			pedido.setPed_qtdeParc(0);
		}
		return null;
	}

	public String actionPagar() {
		return "/cliente/finalizar_compra?faces-redirect=true";
	}

	public String actionExcluirProdutoDoCarrinho(ItensPedido itensPedido) {
		for (int i = 0; i < pedido.getItensPedido().size(); i++) {
			if (pedido.getItensPedido().size() >= 1) {
				pedido.getItensPedido().remove(itensPedido);
			}
		}
		return null;
	}

	public String getUsuarioLogado() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getUsername();
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public FormaPgto getFormaPgto() {
		return formaPgto;
	}

	public void setFormaPgto(FormaPgto formaPgto) {
		this.formaPgto = formaPgto;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

}
