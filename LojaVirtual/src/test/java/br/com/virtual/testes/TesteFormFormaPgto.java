package br.com.virtual.testes;

import static org.junit.Assert.*;

import org.junit.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class TesteFormFormaPgto {

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
        beginAt("admin/lista_formaPgto.xhtml");
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
        clickLink("form:formaPgto");

        assertTitleEquals("Lista de Formas de Pagamento");
        assertTextInElement("form:tbl:0:pgtoId", "1");
        assertTextInElement("form:tbl:0:pgtoDesc", "Boleto");
        assertTextInElement("form:tbl:0:pgtoParc", "1");

        assertTextInElement("form:tbl:1:pgtoId", "2");
        assertTextInElement("form:tbl:1:pgtoDesc", "Cheque");
        assertTextInElement("form:tbl:1:pgtoParc", "2");
    }
}