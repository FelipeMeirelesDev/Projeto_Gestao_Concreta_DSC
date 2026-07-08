# 🏗️ Gestão Concreta - Sistema de Gestão para Loja de Materiais de Construção

<p align="center">
  <img src="docs/prints/dashboard.png" width="700">
</p>

O **Gestão Concreta** é um sistema desktop desenvolvido em **Java com JavaFX** e banco de dados **MySQL**, seguindo o padrão **MVC (Model–View–Controller)**.

O sistema foi desenvolvido para auxiliar no gerenciamento de lojas de materiais de construção, permitindo o controle de produtos, estoque, vendas, usuários e emissão de relatórios em PDF através de uma interface moderna e intuitiva.

## 🧱 Tecnologias Utilizadas

* **Java 17**
* **JavaFX**
* **FXML**
* **MySQL**
* **JDBC**
* **Maven**
* **OpenPDF**
* **Padrão MVC**

---

## 📂 Estrutura do Projeto

```text
src/
 └── main/
     ├── java/
     │   └── com.meirelesdev.gestao_concreta
     │       ├── controller
     │       ├── dao
     │       ├── factory
     │       ├── model
     │       ├── util
     │       └── Application.java
     │
     └── resources/
         └── com.meirelesdev.gestao_concreta
             ├── Configuracoes.fxml
             ├── EditarProduto.fxml
             ├── Inicio.fxml
             ├── Login.fxml
             ├── Produtos.fxml
             ├── Relatorio.fxml
             ├── TelaPrincipal.fxml
             ├── Usuarios.fxml
             └── Vendas.fxml
```

---

## 🎯 Objetivo do Projeto

Este projeto foi desenvolvido com o objetivo de:

* Aplicar Programação Orientada a Objetos na prática
* Utilizar o padrão MVC em aplicações desktop
* Trabalhar com JavaFX e FXML
* Integrar Java com banco de dados MySQL
* Desenvolver um sistema completo de gestão comercial
* Gerar relatórios em PDF
* Criar um projeto completo para portfólio no GitHub

---

## ⚙️ Pré-requisitos

Antes de executar o projeto, certifique-se de ter instalado:

* **Java JDK 17 ou superior**
* **MySQL Server**
* **Git**
* **Maven**
* Uma IDE Java (IntelliJ IDEA, Eclipse ou NetBeans)

---

## 🗄️ Banco de Dados

O sistema utiliza **MySQL**.

### 📌 Criar o banco de dados

```sql
CREATE DATABASE gestao_concreta;
USE gestao_concreta;
```

### 📌 Criar as tabelas

```sql
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil ENUM('Administrador','Funcionario') NOT NULL
);

CREATE TABLE produtos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    preco_compra DECIMAL(10,2) NOT NULL,
    preco_venda DECIMAL(10,2) NOT NULL,
    estoque INT NOT NULL,
    estoque_minimo INT NOT NULL
);

CREATE TABLE vendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_venda DATE NOT NULL,
    forma_pagamento VARCHAR(30) NOT NULL,
    valor_total DECIMAL(10,2) NOT NULL
);

CREATE TABLE itens_venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venda_id INT NOT NULL,
    produto_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (venda_id) REFERENCES vendas(id),
    FOREIGN KEY (produto_id) REFERENCES produtos(id)
);
```
### 📌 Usuário padrão

O sistema já possui um usuário administrador para facilitar os testes.

```text
Login: admin
Senha: admin
Perfil: Administrador
```

Caso ele não exista, execute:

```sql
INSERT INTO usuarios
(nome, login, senha, perfil)
VALUES
(
'Administrador',
'admin',
'admin',
'Administrador'
);
```

### 📌 Consultas úteis

```sql
SELECT * FROM usuarios;
SELECT * FROM produtos;
SELECT * FROM vendas;
SELECT * FROM itens_venda;
```

---

# 🚀 Como Executar o Projeto

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/FelipeMeirelesDev/Projeto_Gestao_Concreta_DSC.git
```

### 2️⃣ Importar na IDE

* Abra sua IDE
* Selecione **Import Project**
* Escolha o projeto Maven
* Aguarde o download das dependências

### 3️⃣ Configuração da Conexão com o Banco de Dados

Após criar o banco de dados, configure as informações de conexão na classe:

```text
src/main/java/com/meirelesdev/gestao_concreta/factory/ConnectionFactory.java
```

Altere os dados conforme a configuração do seu MySQL:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/gestao_concreta";

private static final String USUARIO = "root";

private static final String SENHA = "sua_senha";
```

Salve as alterações e certifique-se de que o servidor MySQL esteja em execução antes de iniciar a aplicação.


### 4️⃣ Executar a aplicação

Execute a classe principal:

```text
src/main/java/com/meirelesdev/gestao_concreta/Application.java
```

Após iniciar o sistema, utilize o usuário padrão:

```text
Login: admin
Senha: admin
```

---

## 📌 Observações

* Projeto desenvolvido utilizando JavaFX + FXML.
* Interface desenvolvida com auxílio do Scene Builder.
* Arquitetura organizada seguindo o padrão MVC.
* Comunicação com o banco de dados realizada através de JDBC.
* Relatórios gerados em PDF utilizando a biblioteca OpenPDF.
* Controle de permissões por perfil de usuário.
* Dashboard atualizado automaticamente conforme as movimentações do sistema.

---

## 📄 Licença

Este projeto foi desenvolvido para fins acadêmicos e de aprendizado, podendo ser utilizado como base para estudos e evolução de aplicações Java Desktop.

