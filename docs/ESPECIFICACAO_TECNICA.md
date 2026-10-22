# GlicoControl - Documentação Funcional e Técnica

**Projeto:** API RESTful GlicoControl  
**Versão:** 1.0.0  
**Tecnologias:** Java 17 | Spring Boot | PostgreSQL / H2 | GitHub Actions | Render Cloud  
**Autora:** Adriana  

---

## PART 1: DOCUMENTO FUNCIONAL (VISÃO DE PRODUTO & UPSTREAM)

### 1. Visão Geral do Produto
O **GlicoControl** é uma plataforma digital desenvolvida para auxiliar pacientes diagnosticados com diabetes e profissionais de saúde no acompanhamento e controle diário das taxas glicêmicas.

A solução unifica a gestão de dados biométricos (peso, altura, idade), o acompanhamento do tratamento medicamentoso e o registro histórico das medições de glicemia em tempo real.

#### 🎯 Proposta de Valor (Upstream Business Value)
* **Engajamento do Paciente:** Proporciona visibilidade clara da evolução do tratamento e parâmetros corporais.
* **Redução de Erros de Medicação:** Registro centralizado de medicamentos (ex: doses e horários de insulinas).
* **Preparação para Integrações Clínicas:** Estrutura escalável para permitir integração futura com prontuários eletrônicos (PEP/EMR) e relatórios automatizados para médicos.
* **Arquitetura Pronta para Escala:** API desacoplada, preparada para consumo por aplicações Web (React/Vite) e Mobile (iOS/Android).

---

### 2. Mapeamento de Épicos e Histórias de Usuário (User Stories)

#### 📍 EP01: Gestão do Perfil do Paciente
**Descrição:** Permitir o cadastro, consulta e atualização contínua dos dados biométricos e clínicos dos utilizadores.

##### 👤 US01.1 — Cadastro e Atualização do Perfil Biométrico e Medicamentoso
* **Como** paciente cadastrado no GlicoControl,
* **Quero** cadastrar e atualizar a minha idade, peso, altura e os medicamentos que utilizo,
* **Para que** a plataforma mantenha o meu histórico clínico atualizado e personalize meu acompanhamento.

**Critérios de Aceite (BDD - Given / When / Then):**
* **Cenário 1: Atualização com sucesso do perfil completo**
  * **Given** que o utilizador com `id = 1` ("Célia Santos") já está cadastrado no sistema,
  * **When** enviar uma requisição `PUT /api/users/1` contendo `age: 30`, `height: 1.75`, `weight: 70.5` e `medications: "Insulina NPH"`,
  * **Then** o sistema deve responder com status `200 OK`,
  * **And** os dados atualizados devem ser persistidos no banco de dados e retornados na resposta.
* **Cenário 2: Tentativa de atualização de dados inexistentes/inválidos**
  * **Given** que o utilizador envia um payload com campos não pertencentes à entidade `User` (ex: campo `"glucose"` na rota `/api/users`),
  * **When** a requisição `PUT /api/users/1` for processada,
  * **Then** a API deve rejeitar o payload retornando status `400 Bad Request` com mensagem informativa de erro.

---

#### 📍 EP02: Monitoramento e Registro de Glicemia
**Descrição:** Permitir ao utilizador registar e consultar as medições diárias de taxa de glicose no sangue.

##### 🩸 US02.1 — Registro e Histórico de Medições de Glicemia
* **Como** paciente acompanhado pelo sistema,
* **Quero** lançar o valor da minha medição de glicose (mg/dL) vinculada ao meu utilizador,
* **Para que** eu possa acompanhar a variação da glicemia ao longo do tempo.

**Critérios de Aceite (BDD - Given / When / Then):**
* **Cenário 1: Lançamento de nova medição de glicemia**
  * **Given** que o utilizador autenticado informa a sua taxa de glicose atual (ex: `140.5 mg/dL`),
  * **When** disparar o pedido `POST /api/glucose`,
  * **Then** o sistema deve vincular a medição ao utilizador e registrar a data/hora exata (`createdAt`),
  * **And** retornar o status `201 Created` com os detalhes da medição.

---

#### 📍 EP03: Notificações e Alertas
**Descrição:** Módulo responsável pelo envio de comunicações e alertas informativos sobre tratamentos e medições.

##### ✉️ US03.1 — Gestão de Alertas e Notificações por E-mail
* **Como** administrador ou utilizador da plataforma,
* **Quero** acionar notificações automatizadas de lembrete de medicação ou relatórios,
* **Para que** o paciente não se esqueça de realizar as medições diárias e tomar as doses corretas.

---

#### 📍 EP04: Infraestrutura, Segurança e CI/CD Cloud
**Descrição:** Garantir a entrega contínua do código para ambiente cloud com alta disponibilidade e integridade.

##### 🚀 US04.1 — Publicação e Deploy Automático (Continuous Deployment)
* **Como** desenvolvedora e integradora do sistema,
* **Quero** que cada alteração efetuada na branch principal (`main`) do GitHub seja automaticamente compilada e implantada no servidor Cloud (Render),
* **Para que** novas funcionalidades e correções fiquem disponíveis em tempo real sem necessidade de intervenção manual.

---

## PART 2: DOCUMENTAÇÃO TÉCNICA E ARQUITETURA DE SOFTWARE

### 1. Arquitetura da Solução
O GlicoControl Backend é construído sob a arquitetura **Monolítica Modular em Camadas (Layered Architecture)**, seguindo os princípios do Spring Framework e Clean Code.

* `controllers/`: Expõem os endpoints REST (`@RestController`).
* `services/`: Concentram as regras de negócio e validações.
* `repositories/`: Camada de acesso a dados via Spring Data JPA.
* `models/`: Entidades de domínio mapeadas para o banco de dados.

---

### 2. Diagrama de Entidades e Modelo Conceitual (ER)

```mermaid
erDiagram
    USER ||--o{ GLUCOSE_MEASUREMENT : "possui"
    
    USER {
        Long id PK
        String name
        String email
        String password
        Integer age
        Double weight
        Double height
        String medications

    }

    GLUCOSE_MEASUREMENT {
        Long id PK
        Double value
        LocalDateTime createdAt
        Long userId FK
```
    }

### 3. Mapeamento de Endpoints e Contratos de API REST

#### 3.1. Módulo de Utilizadores (`/api/users`)

| Método | Endpoint | Descrição | Status Sucesso | Status Erro |
| :--- | :--- | :--- | :--- | :--- |
| **POST** | `/api/users` | Regista um novo utilizador | `201 Created` | `400 Bad Request` |
| **GET** | `/api/users` | Lista todos os utilizadores cadastrados | `200 OK` | `500 Server Error` |
| **GET** | `/api/users/{id}` | Busca um utilizador específico pelo ID | `200 OK` | `404 Not Found` |
| **PUT** | `/api/users/{id}` | Atualiza o perfil completo do utilizador | `200 OK` | `400 Bad Request` / `404 Not Found` |
| **DELETE**| `/api/users/{id}` | Remove o registo do utilizador | `204 No Content` | `404 Not Found` |

#### Contract JSON — `PUT /api/users/{id}` (Request Body)
```json
{
  "name": "Célia Santos",
  "email": "celia.santos@email.com",
  "age": 30,
  "height": 1.75,
  "weight": 70.5,
  "medications": "Insulina NPH"
```
}

### 4. Mapeamento na Camada de Serviço (UserService.java)
A lógica de sincronização dos campos no método atualizar garante a integridade e persistência de todas as variáveis biométricas:

@Transactional
public UserDTO atualizar(Long id, UserDTO dto) {
    User entity = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

    entity.setName(dto.getName());
    entity.setEmail(dto.getEmail());
    entity.setAge(dto.getAge());
    entity.setHeight(dto.getHeight());
    entity.setWeight(dto.getWeight());
    entity.setMedications(dto.getMedications());

    entity = repository.save(entity);
    return new UserDTO(entity);
}

### 5. Padrão de Versionamento e Conventional Commits
O repositório adota o padrão internacional de Conventional Commits para manter o rastreamento limpo do histórico de auditoria do código:

feat(user): Adição de novos atributos ao modelo (ex: age, weight, height).

fix(user): Correção e mapeamento de campos no UserService.java.

docs: Atualizações estruturais da documentação (README.md, artefatos técnicos).

refactor: Reestruturação de código sem alteração no comportamento funcional.

### 6. Fluxo de Integração e Deploy Contínuo (CI/CD Pipeline

graph LR
    A[Dev Local] -->|git commit| B[GitHub Repo]
    B -->|Webhook Trigger| C[Render Cloud]
    C -->|Build & Deploy| D[API Live]

** Desenvolvimento Local: O desenvolvedor faz as alterações e testa localmente através do Maven (./mvnw spring-boot:run).

** Versionamento Seguro: O código é adicionado via IDE/Terminal (git add .) e comitado utilizando a convenção (git commit -m "fix(user): ...").

** Trigger de Deploy: Ao realizar o git push, o GitHub recebe as alterações e aciona o Webhook do Render.

** Build & Live: O Render compila a aplicação Spring Boot, provisiona o ambiente Java e disponibiliza a API no endereço público em menos de 3 minutos.
