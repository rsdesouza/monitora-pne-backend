# Backend do Projeto Java com Spring Boot

Este projeto é o backend de uma aplicação web, desenvolvido com Spring Boot em Java e hospedado no Google App Engine. Ele interage com um frontend Angular hospedado no Firebase Hosting e utiliza Firestore como banco de dados NoSQL.

## Estrutura do Projeto

O projeto segue a estrutura padrão de diretórios do Spring Boot:

- `src/`: Contém os arquivos principais do código-fonte.
  - `main/`: Contém o código-fonte da aplicação.
    - `java/`: Contém os pacotes e classes Java.
    - `resources/`: Contém arquivos de configuração e recursos estáticos.
  - `test/`: Contém os testes da aplicação.
- `pom.xml`: Arquivo de configuração do Maven, listando as dependências do projeto e plugins de build.

## Pré-requisitos

Certifique-se de ter o Java Development Kit (JDK) e o Maven instalados em sua máquina:

- [JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (recomendado: versão 11 ou superior)
- [Maven](https://maven.apache.org/)

## Configuração do Google Cloud

Este projeto utiliza o Google App Engine para hospedagem e Firestore como banco de dados. Siga os passos abaixo para configurar o Google Cloud:

1. Instale o Google Cloud SDK:

   [Instruções de instalação](https://cloud.google.com/sdk/docs/install)

2. Faça login no Google Cloud:

   ```sh
   gcloud auth login
