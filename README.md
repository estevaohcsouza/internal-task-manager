# 🚀 INTERNAL-TASK-MANAGER

## API RESTful desenvolvida para o gerenciamento de atividades

### 🧠 Nesta API você poderá cadastrar e gerenciar clientes, tarefas e subtarefas

---
### 🛠️ Tecnologias utilizadas

- Java 21.0.8
- Spring boot 4.0.4
- Spring Data JPA
- Docker 28.4.0
- OpenAPI
- PostgreSQL 18.0
- h2 database
- Lombok
- Maven 3.9.11

---
### 🧪 Testes

- Testes unitários com **JUnit**
- Testes da camada de controllers com **MockMvc** e **@MockitoBean**  
- Testes da camada de services com **@Mock** e **@InjectMocks**
- Testes de integração com **MockMvc**

---
### 🧱 Entidades

- ***User*** - representa os usuários do sistema 
- ***Task*** - representa as tarefas cadastradas
- ***Subtask*** - representa as subtarefas cadastradas

---
### 📬 Endpoints

**👤 usuarios**
- `GET /usuarios` → lista todos os usuários
- `POST /usuarios/{usuarioId}` → cria um usuário

**✅ tarefas**

- `GET /tarefas` → lista todas as tarefas
- `GET /tarefas?status=STATUS` → lista todas as tarefas de acordo com o filtro de status
- `GET /tarefas?status=STATUS&page=0&size=5&sort=titulo` → lista todas as tarefas de acordo com os filtros de status e paginação
- `POST /tarefas` → cria um tarefa
- `PATCH /tarefas/{tarefaId}/status` → altera o status da tarefa

**📌 subtarefas**

- `GET /tarefas/{tarefaId}/subtarefas` → lista todas as subtarefas de uma tarefa
- `GET /tarefas/{tarefaId}/subtarefas?page=0&size=5&sort=titulo` → lista todas as subtarefas de uma tarefa de acordo com os filtros de paginação
- `POST /tarefas/{tarefaId}/subtarefas` → cria uma subtarefa para uma tarefa
- `PATCH /subtarefas/{subtarefaId}/status` → altera o status da subtarefa

---
### 🔐 Regras de negócio

**🧑 Nome**

- Deve conter no mínimo duas strings com no mínimo duas letras cada
- No total deve conter de 5 a 50 caracteres
- Aceita apenas letras | não aceita números ou símbolos

**📧 E-mail**

- É único → um mesmo e-mail não pode ser cadastrado para dois ou mais usuários
- Deve ter o formato de um e-mail válido, com o uso de @ por exemplo

**⏳ Status**

- Status aceitos: ***EM_ANDAMENTO***, ***PENDENTE*** ou ***CONCLUIDA***
- Uma tarefa só poderá ter seu status alterado para ***CONCLUIDA*** quando todas as subtarefas relacionadas a ela também estiverem com status de ***CONCLUIDA***
- Após alterar o status de uma tarefa para ***CONCLUIDA***, não poderá se criar novas subtarefas a ela
- Após alterar o status de uma tarefa ou subtarefa para ***CONCLUIDA***, o status não poderá ser alterado novamente

**✏️ Titulo**

- Deve conter de 3 a 100 caracteres
- Aceita letras, números e símbolos

**📝 Descrição**

- Não é obrigatória, mas caso seja informada, deverá conter no máximo 500 caracteres
- Aceita letras, números e símbolos

---
### ⚙️ Configurações

**🔍 Escolhendo o ambiente de desenvolvimento**

- ***test***
  - Configurado em `/resources/application-test.properties`
  - Utiliza o banco h2
- ***dev***
  - Configurado em `/resources/application-dev.properties`
  - Utiliza o PostgreSQL
- Para escolher o ambiente basta acessar o arquivo `/resources/application.properties` e alterar a configuração `spring.profiles.active=` para ***test*** ou ***dev***

**🛢️ Banco de dados de dev - PostgreSQL**

- Para configurar o ambiente de **dev**, provavelmente será necessário realizar algumas alterações em `/resources/application-dev.properties`
  - `spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/internal_task_manager_db`
    - ***5432*** se refere a porta utilizada pelo PostgreSQL em sua máquina. Altere se necessário.
    - ***internal_task_manager_db*** se refere a base de dados do projeto, que deverá ser criada manualmente e localmente. Utilize o nome que quiser e faça a alteração.
  - `spring.datasource.username=postgres`
    - ***postgres*** é o nome do usuário com permissões na base de dados. Altere se necessário.
  - `spring.datasource.password=SENHA`
    - Em ***SENHA*** insira a senha de acesso ao seu BD.

**🌶️ Lombok**

- Neste projeto utilizamos o ***Lombok*** para criação de getters, setters e alguns construtores
- Sua dependência já se encontra no `pom.xml` do projeto, mas ainda será necessário fazer algumas configs no seu **Intellij**:
  - Instale o plugin **Lombok**: ***File → Settings → Plugins → Marketplace → Lombok***
  - Habilite **Annotation Processing**: ***File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing***

---
### 🔧 Como rodar o projeto

**☕ Pré-requisitos**

- Java 21 ou superior
- Maven 3.9.11 ou superior
- PostgreSQL (**aconselhável utilizar versão 18 ou superior**)
- Instalação e configuração de IDE - Intellij ou Eclipse (**preferencialmente Intellij**) 

**🖥️ Comandos**
 
- Para garantir que tudo compile corretamente, rode: `mvn clean install`
- Por fim, para subir o projeto, rode: `mvn spring-boot:run`

---
### 🌟 Após subir o projeto

- Utilize ferramentas de requisição para a realização de testes, como o **Postman** ou o próprio [**Swagger**](http://localhost:8080/swagger-ui/index.html#/) da aplicação
- Se estiver no ambiente de ***test***, acesse os dados via [**banco de dados h2**](http://localhost:8080/h2-console/)
- Se estiver no ambiente de ***dev***, acesse os dados utilizando o banco localmente instalado e configurado

---
### 🐳 Docker

**📦 Docker Compose**

- É disponibilizado o docker-compose do **projeto** com banco de dados **PostgreSQL**
  - Possibilidade de subir e testar o projeto via docker
  - Sem preocupação em instalar ferramentas e realizar configurações extras
  - Requisito: ter o docker instalado

**🖥️ Comandos**

- Na raíz do projeto rodar:
  - `docker-compose up --build` → para subir o compose
  - `docker-compose down` → para derrubar o compose
  - `docker-compose up` → para subir novamente
  - Outros:
    - `docker-compose stop` → para pausar o funcionamento do compose
    - `docker-compose start` → para reiniciar o compose
    - `docker-compose down -v` → Para remover containers e volumes associados (zerar o banco)

---
### 📄 Documentação

**📖 Você encontra informações complementares no [**Swagger**](http://localhost:8080/swagger-ui/index.html#/) da aplicação**