package br.com.virtual.negocio;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import br.com.virtual.beans.Fone;
import br.com.virtual.beans.Pessoa;
import br.com.virtual.persistencia.PessoaDAO;

@ManagedBean(name="pessoa")
@SessionScoped
public class PessoaCtrl implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private String filtro="";
	private Fone fone = new Fone();
	
	public Fone getFone() {
		return fone;
	}

	public void setFone(Fone fone) {
		this.fone = fone;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public List<Pessoa> getListagem(){
		return PessoaDAO.listagem(filtro);
	}
	
	public String actionGravar() {
		FacesContext context = FacesContext.getCurrentInstance();
		if(pessoa.getId() == 0) {
			PessoaDAO.inserir(pessoa);
			context.addMessage(null, new FacesMessage("Sucesso", "Inserido com sucesso!"));
		}
		else {
			PessoaDAO.alterar(pessoa);
			context.addMessage(null, new FacesMessage("Sucesso", "Alterado com sucesso!"));
		}
		return "/publico/form_cliente?faces-redirect=true /admin/lista_cliente?faces-reditect=true";
	}
	
	public String actionInserir() {
		pessoa = new Pessoa();
		return "/admin/lista_pessoa";
	}
	
	public String actionExcluir() {
		PessoaDAO.excluir(pessoa);
		return "/admin/lista_pessoa";
	}

	
	public void onRowSelect(SelectEvent event) {
		FacesMessage msg = new FacesMessage("Pessoa Selecionada",
				String.valueOf(((Pessoa) event.getObject()).getId()));
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void actionInserirFone() {
		Fone fone = new Fone();
		fone.setPessoa(pessoa);
		pessoa.getFones().add(fone);
//		return "/admin/lista_pessoa";
	}
	
	public String actionExcluirFone() {
		PessoaDAO.excluirFone(fone);
		pessoa.setFones(PessoaDAO.listagemFone(pessoa));
		return "/admin/lista_pessoa";
	}

}
