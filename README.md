# Tech Challenge Fase III - Pós Tech FIAP
## API Gerenciamento de Restaurantes

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql&logoColor=white)
![Azure](https://img.shields.io/badge/Azure-2173b9?style=for-the-badge&logo=microsoft-azure&logoColor=white)
<hr>
Por meio desta aplicação utilizamos todos os conhecimentos obtidos na Fase 3, sendo eles Deploy da aplicação, Clean Architecture e Qualidade de Software por meio de testes unitários.

## Instalação

1. Clone the repository:

```bash
git clone https://github.com/peresricardo/postech-restaurante.git
```

2. Instale as dependencias com Maven

## Comece a usar

1. Inicie a aplicação com Maven
2. A API pode ser acessada pelo endpoint http://localhost:8080
3. Swagger doc:

```bash
servidor OnRender.com
https://fiap-restaurante.onrender.com/documentacao

Actuator - Verifica a saúde do app
local -> http://localhost:8080/actuator/health
onRender -> https://fiap-restaurante.onrender.com/actuator/health
```

## Execução de testes no projeto
<hr>

- Para executar os testes unitários:

```sh
mvn test
```
- Para executar os testes integrados:
```sh
mvn test -P integration-test
```
- Para executar os testes de sistema:
```sh
mvn test -P system-test
```