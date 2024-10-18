# SISTEMA DE GERENCIAMENTO DE EVENTOS

### Feito em conjunto por:
 - Jonas Mendes
 - José Roberval

\* _Este sistema é um case participativo dos três cases pedidos pelo professor Lucas Rodolfo, feitos em paralelo com o auxilio de Adryan Rafael, Lucas Vinícius e Gabriel de Souza._


## Como este sistema funciona?

O sistema de gerenciamento de eventos possui um sistema simples de utilização, estando nas mãos do usuário a utilização plena do sistema com as seguintes funções:

- Cadastro de eventos
- Exclusão de eventos
- Adicição de participantes
- Remoção de participantes
- Análises referente aos eventos

## Classes

As classes contemplam a orientação aos objetos definidos ao longo do projeto, contendo uma classe para definição de "Local", "Event", "Participante" e uma classe adicional para o condensamento das classes referidas e utilização efetiva das mesmas.

### -> Event
  
\* _Classe e métodos definidos por Jonas_

Esta classe possui a finalidade de definir um evento. O evento possui: Nome, Capacidade, Participantes Inscritos (CONTAGEM NUMÉRICA), um Array de participantes e o local. Seus atributos estão protegidos por encapsulamento e o seu construtor está definido, bem como os métodos _getters_ e _setters._

A classe Event pode: 
- Adicionar participantes a si mesma(caso o objeto evento permita), bem como removê-los.
- Salvar eventos criados
- Mostrar estatísticas de eventos
- Atualizar os eventos no arquivo CSV

### -> Local

\* _Classe e métodos definidos por Roberval_

Esta classe possui a finalidade de definir um local. Um local possui: Nome, Capacidade, Disponibilidade e um Array de eventos atribuídos a ele. Seus atributos estão protegidos por encapsulamento e seu construtor está definido, bem como os métodos _getters_ e _setters_.

A classe Local pode:
- Receber um evento
- Salvar um local
- Mostrar um local
- Verificar a ocupação

### -> Participante

\* _Classe e métodos definidos por Roberval_

Esta classe possui a finalidade de definir um participante. Um participante possui: Nome. Os participantes podem ser adicionados a eventos e serem removidos deles.

### -> MenuEventos

\* _Classe e métodos feitos em conjunto por Jonas e Roberval_

Esta classe possui a finalidade de usar as classes e métodos descritos anteriormente de forma coesa e funcional para o usuário final. A classe possui uma função `main(String[] args)` que inicia os locais durante uma nova execução e oferece ao usuario uma serie de opções de utilização do sistema de acordo com um input numérico.

O usuário, agora, pode:

- Criar eventos
- Excluir eventos
- Adicionar participantes a eventos
- Remover participantes de eventos
- Exibir opções de análise de estatisticas
- Exibir informações gerais dos eventos
- Exibir informações especificas dos eventos

Dentro das opções de análise, temos 5 opções que respondem e facilitam uma tomada de decisão no gerenciamento de eventos. São elas:

- Qual o evento registrado com maior taxa de adesão?
- Qual o evento registrado com a menor taxa de adesão?
- Qual o evento com a menor capacidade?
- Qual o total de participantes em todos os eventos?
- Qual o local mais popular para fazer eventos?


