Feature: Gestão de Estratégias

  Scenario: Listar todas as estratégias
    Given que eu tenha acesso ao sistema
    When eu envio uma solicitação GET para "/api/estrategias"
    Then eu recebo uma lista de estratégias cadastradas

  Scenario: Criar uma nova estratégia
    Given que eu tenha uma estratégia válida para cadastro
    When eu envio uma solicitação POST para "/api/estrategias"
    Then a estratégia é criada com sucesso com status 201

  Scenario: Excluir uma estratégia existente
    Given que uma estratégia com ID 1 exista no sistema
    When eu envio uma solicitação DELETE para "/api/estrategias/1"
    Then a estratégia é excluída com sucesso
