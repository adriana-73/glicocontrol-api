# 🩸 GlicoControl API

API RESTful em Java com Spring Boot para gestão e acompanhamento de controlo glicémico, perfis de pacientes e medicações.

---

## 📋 Pré-requisitos

* *Java Development Kit (JDK):* Versão 17 ou superior.
* *Maven:* Para gestão de dependências e construção do projeto (inclui Wrapper ./mvnw).
* *Git:* Para o controlo de versões.
* *Talend API Tester / Postman:* Para testes dos endpoints da API.

---

## 🚀 Como Clonar e Executar o Projeto Localmente

1. *Clonar o repositório:*
   ```bash
   git clone https://github.com/adriana-73/glicocontrol-api.git
   
   Aceder á pasta do projeto:
   cd glicocontrol-api/backend
   
   Execultar a aplicação:
   ./mvnw spring-boot:run
   
## A aplicação ficará disponível localmente em: ⁠http://localhost:8080

## Infraestrutura e Deploy (Ambiente Compartilhado)
 O projeto está configurado com Deploy Contínuo (CD):
 Repositório Central: GitHub (⁠adriana-73/glicocontrol-api⁠)
 Ambiente de Produção (Cloud): Hospedado no Render.
 Fluxo de Trabalho: Sempre que é feito um ⁠git push⁠ para a branch principal (⁠main⁠), 
 o Render deteta a atualização, executa o build automaticamente e atualiza a aplicação em tempo real.
## 📌 Endpoints Principais: 

### 👤 Utilizadores (/api/users)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| *POST* | /api/users | Regista um novo utilizado |
| *GET* | /api/users | Lista todos os utilizadores |
| *GET* | /api/users/{id} | Procura um utilizador pelo ID |
| *PUT* | /api/users/{id} | Atualiza os dados de perfil (nome, e-mail, idade, peso, altura, medicação)|
| *DELETE* | /api/users/{id} | Remove um utilizador |

*Exemplo de Corpo (Body JSON para POST/PUT):
'''json
{  
  "name": "Célia Santos",  
  "email": "celia01santos@hotmail.com",  
  "password": "SuaSenhaAqui",  
  "age": 30,  
  "height": 1.75,  
  "weight": 70.5,  
  "medications": "Insulina NPH"
  '''
}

### 🩺 Medições e Glicemia (/api/glucose / /api/measurements)

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| *POST* | /api/glucose | Regista uma nova medição de glicemia associada |
| *GET* | /api/glucose | Lista o histórico de medições |

⁠💻 Padrão de Commits
Este repositório segue as boas práticas de Conventional Commits:
 ⁠feat(user)⁠: Adição de novas funcionalidades.
 ⁠fix(user)⁠: Correções de erros ou mapeamento de campos.
 ⁠docs⁠: Atualizações na documentação (como o README)
👩‍💻 Autora
Desenvolvido por: Adriana 🚀
GitHub: https://github.com/adriana-73

   
