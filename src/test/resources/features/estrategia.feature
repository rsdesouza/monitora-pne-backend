Feature: Gestão de Estratégias

  Scenario: Criar uma nova estratégia
    Given que eu tenha uma nova estratégia válida
    When eu envio a solicitação para criar a estratégia
    Then a estratégia é criada com sucesso
