package br.com.virtual.testes;

import static org.junit.Assert.*;

import org.junit.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class TesteCarrinho {

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
        beginAt("publico/index.xhtml");
        assertTitleEquals("Loja Virtual");
        assertElementPresent("form:carrinhoIndex");
        assertElementPresent("form:produtos:0:comprar");
        clickButton("form:produtos:0:comprar");
        clickButton("form:produtos:1:comprar");
        clickButton("form:carrinhoIndex");

        gotoPage("publico/carrinho.xhtml");
        assertTitleEquals("Carrinho");
        assertTextInElement("form-itens:tbl:0:idProd", "8");
        assertTextInElement("form-itens:tbl:0:nomeProd", "açúcar");
        assertTextInElement("form-itens:tbl:0:preçoUnit", "10.0");
        assertTextInElement("form-itens:tbl:0:quantidade", "1");
        assertTextInElement("form-itens:tbl:0:subtotal", "10.0");

        assertTextInElement("form-itens:tbl:1:idProd", "7");
        assertTextInElement("form-itens:tbl:1:nomeProd", "Açúcar mascavo");
        assertTextInElement("form-itens:tbl:1:preçoUnit", "13.1");
        assertTextInElement("form-itens:tbl:1:quantidade", "1");
        assertTextInElement("form-itens:tbl:1:subtotal", "13.1");

        assertTextInElement("form-subtotal:subtotalCarrinho", "23.1");
    }
}