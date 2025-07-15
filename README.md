# EletroStore - Loja de Eletrônicos

Aplicação de e-commerce para venda de produtos eletrônicos desenvolvida com Java/Quarkus no backend e React no frontend.

## Tecnologias Utilizadas

- **Backend**:
  - Java 17
  - Quarkus (Framework reativo para Java)
  - Hibernate e Panache ORM
  - H2 Database (Banco de dados embutido)
  - RESTEasy Reactive (API RESTful)
  - SmallRye JWT (Autenticação)

- **Frontend**:
  - React 18
  - TypeScript
  - Tailwind CSS
  - Vite (Build tool)
  - Zustand (State management)

## Funcionalidades

- Catálogo de produtos eletrônicos
- Sistema de busca e filtragem por categorias
- Carrinho de compras
- Cadastro e autenticação de usuários
- Painel administrativo
- Processo de checkout com cálculo de frete
- Histórico de pedidos
- Dashboard com estatísticas para administradores

## Como executar no Eclipse

### Pré-requisitos

- Java 17 ou superior
- Eclipse IDE for Enterprise Java and Web Developers
- Maven (integrado ao Eclipse)

### Configuração no Eclipse

1. **Importar o projeto**:
   - File → Import → Existing Maven Projects
   - Selecione a pasta do projeto
   - Clique em "Finish"

2. **Configurar o JDK**:
   - Clique com o botão direito no projeto → Properties
   - Java Build Path → Libraries → Modulepath/Classpath
   - Certifique-se de que está usando JDK 17+

3. **Executar a aplicação**:
   - Localize a classe principal (geralmente em `src/main/java`)
   - Clique com o botão direito → Run As → Java Application
   - Ou use o Maven: Run As → Maven build → Goals: `quarkus:dev`

### Banco de Dados H2

O projeto agora usa H2 Database (banco embutido), que não requer instalação externa:

- **Tipo**: In-memory database
- **Console Web**: Disponível em http://localhost:8080/h2-console
- **URL JDBC**: `jdbc:h2:mem:eletrostore`
- **Usuário**: `sa`
- **Senha**: (vazia)

### Executando a aplicação

1. **Backend (Quarkus)**:
   ```bash
   # No Eclipse: Run As → Maven build
   # Goals: quarkus:dev
   
   # Ou via terminal:
   ./mvnw quarkus:dev
   ```

2. **Frontend (React)**:
   ```bash
   # Em um terminal separado:
   npm install
   npm run dev
   ```

### URLs da aplicação

- **Backend API**: http://localhost:8080
- **Frontend**: http://localhost:5173
- **Console H2**: http://localhost:8080/h2-console
- **Swagger UI**: http://localhost:8080/q/swagger-ui (se habilitado)

### Dados de teste

A aplicação inicializa automaticamente com dados de exemplo:

**Usuários**:
- **Admin**: admin@eletrostore.com / admin123
- **Cliente**: cliente@exemplo.com / 123456

**Produtos**: Vários produtos de exemplo são criados automaticamente

### Estrutura do Projeto

```
├── src/main/java/com/eletronicos/
│   ├── model/       # Entidades JPA
│   ├── resource/    # Endpoints REST
│   ├── service/     # Lógica de negócio
│   └── auth/        # Autenticação e autorização
├── src/main/resources/
│   ├── application.properties  # Configurações
│   └── import.sql   # Dados iniciais
├── src/             # Frontend React
│   ├── components/  # Componentes React
│   ├── pages/       # Páginas da aplicação
│   └── services/    # Serviços de API
└── pom.xml          # Dependências Maven
```

### Desenvolvimento no Eclipse

1. **Hot Reload**: O Quarkus suporta hot reload automático
2. **Debug**: Use o modo debug do Eclipse normalmente
3. **Logs**: Visualize os logs no console do Eclipse
4. **Database**: Acesse o console H2 para visualizar dados

### Vantagens do H2 Database

- ✅ Não requer instalação externa
- ✅ Configuração automática
- ✅ Console web integrado
- ✅ Ideal para desenvolvimento e testes
- ✅ Dados são recriados a cada inicialização (sempre limpo)

### Troubleshooting

1. **Erro de compilação**: Verifique se está usando Java 17+
2. **Porta ocupada**: Altere a porta em `application.properties`
3. **Dependências**: Execute `mvn clean install` no terminal
4. **H2 Console**: Certifique-se de que a aplicação está rodando

## Contribuição

Para contribuir com o projeto:

1. Faça um fork do repositório
2. Crie uma branch para sua feature
3. Commit suas mudanças
4. Push para a branch
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a licença MIT.