Feature: Gestão de Planos de Ação

  Scenario: Listar todos os planos de ação
    Given que eu tenha acesso ao sistema
    When eu envio uma solicitação GET para "/api/planos-acao"
    Then eu recebo uma lista de planos de ação cadastrados

  Scenario: Criar um novo plano de ação
    Given que eu tenha um plano de ação válido para cadastro
    When eu envio uma solicitação POST para "/api/planos-acao"
    Then o plano de ação é criado com sucesso com status 201

  Scenario: Excluir um plano de ação existente
    Given que um plano de ação com ID 1 exista no sistema
    When eu envio uma solicitação DELETE para "/api/planos-acao/1"
    Then o plano de ação é excluído com sucesso
