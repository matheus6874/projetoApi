package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import utils.GeraCpf;
import utils.Restricoes;
import utils.Simulacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidarSimulacaoDeCredito {
    String cpf;
    String nome;
    String email;
    Integer valor;
    Integer parcela;

    Restricoes restricoes = new Restricoes();
    Simulacao simulacao = new Simulacao();
    GeraCpf geraCpf = new GeraCpf();
    Response response;

    @Dado("que tenho um cpf com restricao {string}")
    public void que_tenho_um_cpf_com_restricao(String cpf) {
        this.cpf = cpf;
    }

    @Quando("busco o cpf na api de restricoes")
    public void busco_o_cpf_na_api_de_restricoes() {
        response = restricoes.consultaRestricaoCpf(cpf);
    }

    @Então("é retornado status code {int}")
    public void é_retornado_status_code(int statusCode) {
       Assert.assertEquals(statusCode,response.statusCode());
    }

    @Então("é retornado a mensagem {string}")
    public void é_retornado_a_mensagem(String mensagemEsperada) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String mensagem = jsonPathEvaluator.get("mensagem");
        Assert.assertEquals(mensagemEsperada,mensagem);
    }

    @Dado("que tenho um cpf sem restricao {string}")
    public void que_tenho_um_cpf_sem_restricao(String cpf) {
        this.cpf = cpf;
    }

    @Dado("que possou um cpf {string}")
    public void que_possou_um_cpf(String statusCpf) {
        if (statusCpf.equals("valido")){
            this.cpf = geraCpf.cpf(false);
            System.out.println(this.cpf);
        }
        if (statusCpf.equals("existente")){
            this.cpf = "07254556325";
        }
    }

    @Dado("nome {string}")
    public void nome(String nome) {
        this.nome = nome;
        System.out.println(this.nome);
    }
    @Dado("email {string}")
    public void email(String email) {
        this.email = email;
        System.out.println(this.email);
    }
    @Dado("valor {string}")
    public void valor(String valor) {
        this.valor = Integer.parseInt(valor);
        System.out.println(this.valor);
    }
    @Dado("parcela {string}")
    public void parcela(String parcela) {
        this.parcela = Integer.parseInt(parcela);
        System.out.println((this.parcela));
    }
    @Quando("insiro uma simulacao")
    public void insiro_uma_simulacao() {
        response = simulacao.insereSimulacao(this.nome,this.cpf,this.email,this.valor,this.parcela);

    }
    @Então("a mensagem {string}")
    public void a_mensagem(String mensagem) {
        JsonPath jsonPathEvaluator = response.jsonPath();

        if (mensagem.equals("sucesso")){
            String nome = jsonPathEvaluator.get("nome");
            String cpf = jsonPathEvaluator.get("cpf");
            String email = jsonPathEvaluator.get("email");
            Integer valor = jsonPathEvaluator.get("valor");
            Integer parcelas = jsonPathEvaluator.get("parcelas");
            Boolean seguro = jsonPathEvaluator.get("seguro");
            Assert.assertEquals(nome,this.nome);
            Assert.assertEquals(cpf,this.cpf);
            Assert.assertEquals(email,this.email);
            Assert.assertEquals(valor,this.valor);
            Assert.assertEquals(parcelas,this.parcela);
            Assert.assertEquals(seguro,true);
        }
        else{
            JsonPath erro = response.jsonPath();

            Assert.assertTrue(response.getBody().asString().contains(mensagem));
        }
    }

    @Dado("que tenho uma simulacao cadastrada")
    public void que_tenho_uma_simulacao_cadastrada() {
        this.cpf = geraCpf.cpf(false);
        response = simulacao.insereSimulacao("Teste alteracao",this.cpf,"teste@alteracao.com",900,2);
    }

    @Dado("desejo alterar o email para {string}")
    public void desejo_alterar_o_email_para(String email) {
        this.email = email;
    }
    @Quando("chamo a api de alterar simulacao")
    public void chamo_a_api_de_alterar_simulacao() {
        response = simulacao.alterarSimulacao("Teste alteracao",this.cpf,email,900,2);
    }
    @Então("o email e alterado")
    public void o_email_e_alterado() {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String emailAlterao = jsonPathEvaluator.get("email");
        Assert.assertEquals(this.email,emailAlterao);
    }

    @Dado("que tenho um cpf que nao possui cadastrado na base {string}")
    public void que_tenho_um_cpf_que_nao_possui_cadastrado_na_base(String cpf) {
        this.cpf = cpf;
    }

    @Então("a mensagem de erro {string}")
    public void a_mensagem_de_erro(String mensagem) {
        JsonPath jsonPathEvaluator = response.jsonPath();
        String mensagemErro = jsonPathEvaluator.get("mensagem");
        Assert.assertEquals(mensagem,mensagemErro);
    }

    @Quando("chamo a api de buscar simulacoes")
    public void chamo_a_api_de_buscar_simulacoes() {
        response = simulacao.consultarSimulacoesCadastradas();
    }

    @Quando("chamo a api de buscar simulacao por cpf")
    public void chamo_a_api_de_buscar_simulacao_por_cpf() {
        response = simulacao.consultarSimulacaoPorCpf(this.cpf);
    }

    @Quando("chamo a api de deltetar simulacao")
    public void chamo_a_api_de_deltetar_simulacao() {
        JsonPath jsonPath = response.jsonPath();
        String id = jsonPath.get("id");
        simulacao.deletarSimulacao(id);
    }
}
