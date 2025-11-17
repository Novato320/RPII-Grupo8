# 🧪 RP2 - Desenvolvimento e Validação de Testes Unitários

Este repositório contém a estrutura simulada de um sistema de gestão de cursos online simples, focado em três pilares principais: **Usuários**, **Cursos** e **Posts/Artigos**.

O desafio principal consiste em **completar a implementação dos testes unitários** nas classes `*ServiceTest` para garantir que o projeto atende a todos os requisitos de negócio especificados.

## 🎯 Proposta do Exercício

O sistema simula um ambiente de plataforma de aprendizado, onde diferentes tipos de usuários interagem com o conteúdo.

O desafio é **implementar o corpo de todos os métodos de teste (`@Test`)** nas classes de teste (`CursoServiceTest`, `PostServiceTest`, `UsuarioServiceTest`). Você deve utilizar os métodos do JUnit e as ferramentas de asserção (como `assertEquals`, `assertTrue`, etc.) para **validar** se a lógica de serviço (já implementada ou a ser simulada) cumpre os requisitos descritos.

**Seu foco:** Transformar os comentários `// TODO:` (que descrevem o que deve ser testado) em código de teste funcional.

## 📦 Estrutura Principal

Os testes unitários devem ser implementados nas seguintes classes:

| Módulo | Classe de Teste a Implementar | Foco dos Testes |
| :--- | :--- | :--- |
| **Usuário** | `UsuarioServiceTest` | Validação de cadastro, alteração de níveis de acesso (Admin), busca/filtro e ordenação. |
| **Curso** | `CursoServiceTest` | Validação de criação, edição, aprovação (Admin), visualização de catálogo e regras de ingresso (com/sem PIN). |
| **Post** | `PostServiceTest` | Validação de remoção (Admin), contagem de curtidas, filtros por tags e métricas de engajamento. |

## ✅ Requisitos Detalhados (O que Testar)

Para cada método `@Test`, siga as instruções fornecidas nos comentários `// TODO:` (que você já viu no código). O teste deve:

1.  **Chamar** o método de serviço correspondente.
2.  **Verificar** o valor de retorno (`true`/`false`).
3.  **Consultar** a persistência simulada (`dataManager`) para garantir que o estado interno do sistema foi alterado corretamente.
4.  **Utilizar** as Asserções do JUnit (e Assunções, se necessário) para validar o comportamento.

---

### 🚀 Como Começar

1.  Clone este repositório.
2.  Abra as classes de teste (ex: `UsuarioServiceTest`).
3.  Substitua o conteúdo de cada `// TODO:` pelo código de teste funcional, utilizando o `dataManager` e o `usuarioService` para validar os requisitos.
4.  O projeto estará concluído quando **todos os testes estiverem implementados e passando**.

Bom trabalho! 🧪
