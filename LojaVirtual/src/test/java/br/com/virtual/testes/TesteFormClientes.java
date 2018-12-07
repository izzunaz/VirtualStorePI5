package br.com.virtual.testes;

import static org.junit.Assert.;

import org.junit.*;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

import net.sourceforge.jwebunit.util.TestingEngineRegistry;

public class TesteFormClientes {

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
        beginAt("admin/lista_cliente.xhtml");
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
        clickLink("form:cliente");

        assertTitleEquals("Lista de Pessoas");
        assertTextInElement("form:tbl:1:pesId", "7");
        assertTextInElement("form:tbl:1:pesNome", "Administrador");
        assertTextInElement("form:tbl:1:pesCpf", "2134532");
        assertTextInElement("form:tbl:1:pesRg", "12345");
        assertTextInElement("form:tbl:1:pesData", "2018-11-07 00:00:00.0");
        assertTextInElement("form:tbl:1:pesRua", "c");
        assertTextInElement("form:tbl:1:pesBairro", "b");
        assertTextInElement("form:tbl:1:pesCidade", "a");
        assertTextInElement("form:tbl:1:pesUf", "go");
        assertTextInElement("form:tbl:1:pesCep", "214536");
        assertTextInElement("form:tbl:1:pesEmail", "admin@hotmail.com");
        assertTextInElement("form:tbl:1:pesSenha", "admin");
        assertTextInElement("form:tbl:1:pesTipo", "ROLE_ADMINISTRADOR");
assertTextInElement("form:tbl:2:pesId", "5");
        assertTextInElement("form:tbl:2:pesNome", "Wagner Zanuzzi Schmitt");
        assertTextInElement("form:tbl:2:pesCpf", "123");
        assertTextInElement("form:tbl:2:pesRg", "123");
        assertTextInElement("form:tbl:2:pesData", "2018-10-02 00:00:00.0");
        assertTextInElement("form:tbl:2:pesRua", "dfdaad");
        assertTextInElement("form:tbl:2:pesBairro", "asdas");
        assertTextInElement("form:tbl:2:pesCidade", "Goiania");
        assertTextInElement("form:tbl:2:pesUf", "as");
        assertTextInElement("form:tbl:2:pesCep", "74215110");
        assertTextInElement("form:tbl:2:pesEmail", "izzuzan@hotmail.com");
        assertTextInElement("form:tbl:2:pesSenha", "senha");
        assertTextInElement("form:tbl:2:pesTipo", "ROLE_CLIENTE");

    }

}