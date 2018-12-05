package br.com.virtual.persistencia;

import java.io.Serializable;
import java.util.List;
import org.hibernate.*;

import br.com.virtual.beans.Fone;
import br.com.virtual.beans.Pessoa;
import br.com.virtual.persistencia.HibernateUtil;

public class PessoaDAO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static void inserir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.save(pessoa);
		t.commit();
		sessao.close();
	}
	
	public static void alterar(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.update(pessoa);
		t.commit();
		sessao.close();
	}
	
	public static void excluir(Pessoa pessoa) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(pessoa);
		t.commit();
		sessao.close();
	}
	
	public static List<Pessoa> listagem(String filtro){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		if(filtro.trim().length() == 0) {
			consulta = sessao.createQuery("from Pessoa order by pes_nome");
		}
		else {
			consulta = sessao.createQuery("from Pessoa where pes_nome like: parametro order by pes_nome");
			consulta.setString("parametro", "%" + filtro + "%");
		}
		List lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static void excluirFone(Fone fone) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction t = sessao.beginTransaction();
		sessao.delete(fone);
		t.commit();
		sessao.close();
	}
	
	public static List<Fone> listagemFone(Pessoa pessoa){
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Query consulta;
		consulta = sessao.createQuery("from Fone f where f.pessoa = :pes_id").setParameter("pes_id", pessoa);
		List lista = consulta.list();
		sessao.close();
		return lista;
	}
	
	public static Pessoa searchUsuarioLogado(String usuarioLogado) {
        Session sessao = HibernateUtil.getSessionFactory().openSession();
        try {
            Query consulta = sessao.createQuery("from Pessoa where pes_email = :parametro");
            consulta.setString("parametro", usuarioLogado);
            return (Pessoa) consulta.uniqueResult();
        } catch (RuntimeException e) {
            System.out.println("Erro ao pesquisar usuário logado. "+e.getMessage());
        } finally {
            sessao.close();
        }
		return null;
    }

}
