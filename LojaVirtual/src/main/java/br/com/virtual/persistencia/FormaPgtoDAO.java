package br.com.virtual.persistencia;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.virtual.beans.FormaPgto;

public class FormaPgtoDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static void inserir(FormaPgto formaPgto) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(formaPgto);
		t.commit();
		sessao.close();
	}
	
	public static void alterar(FormaPgto formaPgto) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.update(formaPgto);
		t.commit();
		sessao.close();
	}
	
	public static void excluir(FormaPgto formaPgto) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(formaPgto);
		t.commit();
		sessao.close();
	}
	
	public static List<FormaPgto> listagem(String filtro){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		if(filtro.trim().length() == 0) {
			consulta = sessao.createQuery("from FormaPgto order by fpg_descricao");
		}
		else {
			consulta = sessao.createQuery("from FormaPgto where fpg_descricao like: parametro order by fpg_descricao");
			consulta.setString("parametro", "%" + filtro + "%");
		}
		List lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static FormaPgto pesqId(int valor) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta = sessao.createQuery("from FormaPgto where fpg_id = :parametro");
		consulta.setInteger("parametro", valor);
		sessao.close();
		return (FormaPgto) consulta.uniqueResult();
	}
	
}
