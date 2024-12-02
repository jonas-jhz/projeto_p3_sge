# SISTEMA DE GERENCIAMENTO DE EVENTOS

### Feito em conjunto por:
 - Jonas Mendes
 - José Roberval

\* _Este sistema é um case participativo dos três cases pedidos pelo professor Lucas Rodolfo, feitos em paralelo com o auxilio de Adryan Rafael, Lucas Vinícius e Gabriel de Souza._

## Como este sistema funciona?

O sistema de gerenciamento de eventos permite ao usuário realizar diversas operações relacionadas à gestão de eventos, como criação de eventos, gerenciamento de participantes e visualização de estatísticas. Com a refatoração recente, o sistema agora está mais modular e funcional, utilizando estruturas de dados como `ArrayList` para otimizar o gerenciamento das informações.

As funcionalidades disponíveis no sistema são:

- **Cadastro de eventos**: Permite criar novos eventos com nome, capacidade e local.
- **Exclusão de eventos**: Permite remover eventos do sistema.
- **Adição de participantes**: Permite cadastrar participantes em eventos, caso haja espaço disponível.
- **Remoção de participantes**: Permite remover participantes de eventos.
- **Exibição de estatísticas**: Exibe informações gerais sobre todos os eventos ou detalhes específicos sobre um evento.

## Estrutura de Classes

As classes foram refatoradas para serem mais coesas, com o objetivo de melhorar a reutilização de código e seguir boas práticas de orientação a objetos. As principais classes são:

### -> Event (Evento)
  
\* _Classe e métodos definidos por Jonas_

A classe **Event** representa um evento no sistema. Cada evento possui os seguintes atributos:

- **Nome**: O nome do evento.
- **Capacidade**: O número máximo de participantes do evento.
- **Participantes Inscritos**: A lista de participantes registrados no evento.
- **Local**: O local onde o evento ocorre.

A classe Event permite:

- Adicionar participantes ao evento, caso haja capacidade disponível.
- Remover participantes do evento.
- Exibir as estatísticas de participantes do evento, como o número de inscritos e o total de capacidade.

### -> Local (Local do Evento)

\* _Classe e métodos definidos por Roberval_

A classe **Local** representa os locais onde os eventos são realizados. Cada local possui os seguintes atributos:

- **Nome**: O nome do local.
- **Capacidade**: A capacidade máxima do local.
- **Disponibilidade**: Um indicador de se o local está disponível para novos eventos.
- **Eventos Atribuídos**: A lista de eventos realizados no local.

A classe Local permite:

- Atribuir eventos a um local.
- Verificar a ocupação do local (se já há eventos programados).
- Exibir informações detalhadas sobre o local.

### -> Participante (Participante do Evento)

\* _Classe e métodos definidos por Roberval_

A classe **Participante** representa um participante em um evento. Cada participante tem um nome e pode ser associado a um evento. Os participantes podem ser adicionados ou removidos dos eventos.

A classe Participante agora herda de **Usuario** para reaproveitar funcionalidades comuns, como o nome.

### -> MenuEventos (Menu de Navegação do Sistema)

\* _Classe e métodos feitos em conjunto por Jonas e Roberval_

A classe **MenuEventos** centraliza a interação com o usuário. Ela oferece um menu principal que permite o acesso a todas as funcionalidades do sistema, como a criação e exclusão de eventos, e a adição ou remoção de participantes. O menu é exibido repetidamente até que o usuário escolha sair.

As opções disponíveis no menu são:

- **Criar evento**: Permite criar um evento novo, especificando nome, capacidade e local.
- **Excluir evento**: Permite excluir um evento existente.
- **Adicionar participante**: Permite cadastrar um participante em um evento.
- **Remover participante**: Permite remover um participante de um evento.
- **Exibir estatísticas gerais**: Exibe informações gerais sobre todos os eventos.
- **Exibir estatísticas de evento específico**: Exibe informações detalhadas sobre um evento específico.

---

### Alterações Importantes no Sistema:

- **Uso de ArrayList**: O sistema agora utiliza `ArrayList` para gerenciar os participantes e eventos, proporcionando maior flexibilidade e simplificação no código.
- **Remoção de manipulação de arquivos**: A lógica de leitura e escrita de arquivos foi removida, e os dados agora são manipulados diretamente em memória.
- **Melhoria nas buscas e interações**: O sistema foi aprimorado com métodos de busca para eventos e participantes, tornando-o mais eficiente e fácil de usar.

Este sistema agora oferece uma experiência mais fluida e organizada, permitindo ao usuário gerenciar eventos e participantes de forma simplificada e com um design orientado a objetos mais eficiente.
