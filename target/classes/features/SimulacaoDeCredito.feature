# language: pt
# encoding: UTF-8

Funcionalidade: Simular tomada de empréstimo

  Cenário: 1 - Consultar cpf com restricao
    Dado que tenho um cpf com restricao "24094592008"
    Quando busco o cpf na api de restricoes
    Então é retornado status code 200
    Então é retornado a mensagem "O CPF 24094592008 tem problema"

  Cenário: 2 - Consultar cpf sem restricao
    Dado que tenho um cpf sem restricao "99999999999"
    Quando busco o cpf na api de restricoes
    Então é retornado status code 204

  Esquema do Cenario:  <CT> - Validar insercao de simulacao <descricao>
    Dado que possou um cpf "<statusCpf>"
    E nome "<nome>"
    E email "<email>"
    E valor "<valor>"
    E parcela "<parcela>"
    Quando insiro uma simulacao
    Então é retornado status code <statusCode>
    E a mensagem "<mensagem>"

    Exemplos:
      | CT | descricao                            | statusCpf  |nome               | email            | valor | parcela | statusCode | mensagem                                  |
      | 3  | valida                               | valido    |  Matheus Cristino  | teste1@gmail.com | 900   | 3       |    201     | sucesso                                   |
      | 4  | simulacao com valor maior que 40.000 | valido    |  Jessica Pereira   | teste3@gmail.com | 41000 | 3       |    400     | Valor deve ser menor ou igual a R$ 40.000 |
      | 5  | simulacao com parcela menor que 2    | valido    |  Igor Gomes        | teste4@gmail.com | 900   | 1       |    400     | Parcelas deve ser igual ou maior que 2    |
      | 6  | simulacao com cpf existente          | existente |  Yasmin siLVA      | teste6@gmail.com | 900   | 3       |    400     | CPF duplicado                             |

  Cenário: 7 - Validar alteracao de email de uma simulacao
    Dado que tenho uma simulacao cadastrada
    E desejo alterar o email para "testealteraca123@gmail.com"
    Quando chamo a api de alterar simulacao
    Então é retornado status code 200
    E o email e alterado

  Cenário: 8 - Validar erro na alteracao de dados quando se passa um cpf que nao existe na base
    Dado que tenho um cpf que nao possui cadastrado na base "999999"
    E desejo alterar o email para "testealteraca123@gmail.com"
    Quando chamo a api de alterar simulacao
    Então é retornado status code 404
    E a mensagem de erro "CPF 999999 não encontrado"

  Cenário: 9 - Consulta de simulacoes cadastadas
    Dado que tenho uma simulacao cadastrada
    Quando chamo a api de buscar simulacoes
    Então é retornado status code 200

  Cenário: 10 - Consulta de simulacao por cpf
    Dado que tenho uma simulacao cadastrada
    Quando chamo a api de buscar simulacao por cpf
    Então é retornado status code 200

  Cenário: 11 - Consulta de simulacao por cpf inexistente
    Dado que tenho um cpf que nao possui cadastrado na base "999999"
    Quando chamo a api de buscar simulacao por cpf
    Então é retornado status code 404

  Cenário: 11 - Deletar uma simulacao cadastrada
    Dado que tenho uma simulacao cadastrada
    Quando chamo a api de deltetar simulacao
    Então é retornado status code 200






