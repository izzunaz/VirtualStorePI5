package br.com.virtual.testes;

import static org.junit.Assert.;

import org.junit.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class TesteFormProdutos {

    @Before
    public void prepare() {
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_WEBDRIVER);
        setTestingEngineKey(TestingEngineRegistry.TESTING_ENGINE_HTMLUNIT);
    }

    @Before
    public void setUp() throws Exception {
        setBaseUrl("http://localhost:8080/LojaVirtual/");
    }

    @Test
    public void test() {
        beginAt("admin/lista_produto.xhtml");
        assertTitleEquals("Login");
        assertTextPresent("Login");
        setTextField("j_username", "admin@admin.com");
        assertTextPresent("Senha");
        setTextField("j_password", "admin");
        submit();

        assertTitleEquals("Loja Virtual");
        assertTextPresent("Área - Administrador");
        assertElementPresent("form:produto");
        assertElementPresent("form:cliente");
        assertElementPresent("form:formaPgto");
        clickLink("form:produto");

        gotoPage("admin/lista_produto.xhtml");
        assertTextPresent("Lista de Produtos");
        assertElementPresent("form:tbl");
        assertTextInElement("form:tbl:0:prodId", "8");
        assertTextInElement("form:tbl:0:prodNome", "açúcar");
        assertTextInElement("form:tbl:0:prodPreco", "10.0");

        assertTextInElement("form:tbl:1:prodId", "7");
        assertTextInElement("form:tbl:1:prodNome", "Açúcar mascavo");
        assertTextInElement("form:tbl:1:prodPreco", "13.1");

        assertTextInElement("form:tbl:5:prodId", "4");
        assertTextInElement("form:tbl:5:prodNome", "Pamonha");
        assertTextInElement("form:tbl:5:prodPreco", "12.32");
    }

}