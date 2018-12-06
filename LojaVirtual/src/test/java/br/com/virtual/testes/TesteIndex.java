package br.com.virtual.testes;



import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import org.junit.Before;
import org.junit.Test;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class TesteIndex {

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
    public void testListaProdutos() {
        beginAt("publico/index.xhtml");
        assertTitleEquals("Loja Virtual");
        assertButtonPresent("form:carrinhoIndex");
        assertTextPresent("Produtos");
        assertElementPresent("form:produtos");

        assertTextInElement("form:produtos:0:idProduto", "8");
        assertTextInElement("form:produtos:0:nomeProduto", "açúcar");
        assertTextInElement("form:produtos:0:precoProduto", "10.0");

        assertTextInElement("form:produtos:1:idProduto", "7");
        assertTextInElement("form:produtos:1:nomeProduto", "Açúcar mascavo");
        assertTextInElement("form:produtos:1:precoProduto", "13.1");

        assertTextInElement("form:produtos:2:idProduto", "9");
        assertTextInElement("form:produtos:2:nomeProduto", "farinha");
        assertTextInElement("form:produtos:2:precoProduto", "10.0");
    }

}