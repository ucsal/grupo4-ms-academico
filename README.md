# MS-Acadêmico — Microsserviço Acadêmico

Responsável pela gestão da grade curricular e horários do sistema. Faz parte da arquitetura de microsserviços do Grupo 4 — Disciplina de Desenvolvimento de Sistemas (UCSAL).

---

## Tecnologias

Java 21 · Spring Boot 4.0.6 · Spring Data JPA · Spring Cloud Netflix Eureka Client · PostgreSQL · Apache POI · Lombok · Maven

---

## Responsabilidades

Gerenciar **Disciplinas** (criação, atualização, inativação, reativação e exportação) e **Horários** (criação, atualização, exclusão e exportação), expondo endpoints REST consumidos pelo API Gateway.

---

## Estrutura principal

`entity/` — entidades `Disciplina` e `Horario`.  
`enums/` — enums `Dia` (dias da semana) e `Turno` (MANHA, TARDE, NOITE).  
`dto/` — `DisciplinaDTO` e `HorarioDTO` para recebimento dos dados nas requisições.  
`repository/` — `DisciplinaRepository` e `HorarioRepository` com queries customizadas.  
`service/` — `DisciplinaService` e `HorarioService` com a lógica de negócio.  
`controller/` — `DisciplinaController` e `HorarioController` expondo os endpoints REST.

---

## Ordem de inicialização

1. Eureka Server
2. MS-Acadêmico
3. API Gateway

---

## Desenvolvido por

Grupo 4 — Arquitetura de Software T1 — Universidade Católica de Salvador  
Microsserviços e API Gateway: [Lista de Repositórios](https://github.com/stars/eduardaleall/lists/repos-ms-disp)
