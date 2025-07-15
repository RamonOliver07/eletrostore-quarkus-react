# EletroStore: Loja Virtual com Quarkus e React

Este é um projeto de e-commerce desenvolvido como parte de um estudo, utilizando Quarkus para o backend e React para o frontend. A aplicação simula as funcionalidades básicas de uma loja virtual, incluindo listagem de produtos, carrinho de compras e autenticação de usuários.

---

## ⚠️ Aviso Importante sobre o Ambiente

Este projeto foi extensivamente modificado para garantir a compatibilidade com um ambiente de desenvolvimento mais antigo (especificamente **macOS 10.12 Sierra**). 

Como resultado, as versões das principais tecnologias (Quarkus, Node.js, Vite, etc.) foram rebaixadas (downgrade) e podem não corresponder às práticas mais modernas ou aos tutoriais padrão. O código-fonte do frontend também foi adaptado para funcionar com estas versões antigas (ex: migração de `jakarta.*` para `javax.*` no backend, e de React 18/Router v6 para React 17/Router v5 no frontend).

---

## 🛠️ Tecnologias Utilizadas

Esta é a pilha de tecnologias específica que está configurada e funcionando neste projeto:

#### **Backend**
* **Quarkus:** `2.16.12.Final`
* **Java:** `17`
* **Maven:** `3.6.3`

#### **Frontend**
* **Node.js:** `12.22.12`
* **npm:** `6.14.x`
* **Vite:** `2.6.14`
* **React:** `17.0.2`
* **React Router:** `5.3.0`
* **TailwindCSS:** `2.2.19`

---

## ⚙️ Pré-requisitos

Antes de começar, garanta que você tem as seguintes ferramentas instaladas nas versões corretas. O uso de uma ferramenta como o **SDKMAN!** é altamente recomendado para gerenciar as versões de Java e Node.js.

1.  **Java 17:**
    ```bash
    # Exemplo com SDKMAN!
    sdk install java 17.0.11-tem
    ```
2.  **Maven 3.6.3:**
    * Pode ser instalado manualmente ou via SDKMAN! (`sdk install maven 3.6.3`).
3.  **Node.js 12.22.12:**
    ```bash
    # Exemplo com SDKMAN!
    sdk install node 12.22.12
    ```

---

## 🚀 Configuração e Instalação

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/RamonOliver07/eletrostore-quarkus-react.git](https://github.com/RamonOliver07/eletrostore-quarkus-react.git)
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd eletrostore-quarkus-react
    ```

3.  **Instale as dependências do Frontend:**
    ```bash
    npm install
    ```

---

## ▶️ Executando a Aplicação

Para a aplicação funcionar completamente, você precisará ter **dois terminais abertos** simultaneamente, um para o backend e um para o frontend.

### **Terminal 1 - Executando o Backend (Quarkus)**

1.  Navegue até a pasta do projeto.
2.  Execute o comando do Maven:
    ```bash
    mvn quarkus:dev
    ```
3.  O backend estará rodando e disponível em `http://localhost:8080`.

### **Terminal 2 - Executando o Frontend (Vite)**

1.  Abra um novo terminal e navegue até a mesma pasta do projeto.
2.  Execute o comando do npm:
    ```bash
    npm run dev
    ```
3.  A interface do site estará acessível em `http://localhost:3000` (ou a porta que o Vite indicar no terminal).