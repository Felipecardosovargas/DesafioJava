# Documentação do Jogo de Adivinhação

## 📝 Visão Geral
Este é um jogo de adivinhação de números desenvolvido em Java que oferece dois modos de jogo:
1. **Modo Normal**: Adivinhe um único número secreto
2. **Modo Sequência**: Adivinhe uma sequência de 3 números

O jogo possui três níveis de dificuldade e mantém um histórico de pontuações e recordes.

## 🎮 Funcionalidades

### 1. Menu Principal
- Iniciar Novo Jogo
- Ver Regras
- Ver Histórico de Pontuações
- Ver Recordes
- Sair do Jogo

### 2. Modos de Jogo
#### Modo Normal
- Adivinhe um único número secreto
- Receba dicas (maior/menor)
- Pontuação baseada em tentativas restantes

#### Modo Sequência
- Adivinhe uma sequência de 3 números
- Tentativas compartilhadas entre os números
- Bônus por completar a sequência

### 3. Níveis de Dificuldade
| Dificuldade | Faixa de Números | Tentativas | Pontuação Base |
|-------------|------------------|------------|----------------|
| Fácil       | 1-50             | 10         | 100            |
| Médio       | 1-100            | 7          | 200            |
| Difícil     | 1-200            | 5          | 300            |

## ⚙️ Mecânicas do Jogo

### Sistema de Pontuação
- **Pontuação base**: Varia conforme a dificuldade
- **Bônus**: +50 pontos por tentativa restante ao acertar
- **Penalidades**:
    - -10 pontos por palpite errado
    - -10 pontos por usar dica

### Dicas
- Disponível após cada palpite incorreto
- Revela se o número secreto é maior ou menor
- Custa 10 pontos por dica

### Histórico e Recordes
- Mantém as últimas 10 pontuações
- Armazena a maior pontuação para cada nível de dificuldade

## 📋 Regras do Jogo
1. Escolha um modo de jogo (Normal ou Sequência)
2. Selecione um nível de dificuldade
3. Tente adivinhar o número(s) secreto(s) dentro do limite de tentativas
4. A pontuação é calculada com base:
    - No nível de dificuldade
    - Número de tentativas usadas
    - Uso de dicas
5. Você pode consultar o histórico e recordes a qualquer momento

## 🖥️ Como Executar
1. Certifique-se de ter o Java instalado (JDK 11 ou superior recomendado)
2. Compile o arquivo `DesafioModulo.java`:
   ```
   javac DesafioModulo.java
   ```
3. Execute o programa:
   ```
   java DesafioModulo
   ```

## 🧩 Estrutura do Código

### Classes Principais
1. **DesafioModulo**: Classe principal contendo a lógica do jogo
2. **NivelDificuldade**: Enum que define os parâmetros de cada dificuldade
3. **Score**: Classe que armazena pontuações e níveis

### Métodos Chave
- `iniciarJogoNormal()`: Lógica do modo normal
- `iniciarJogoSequencia()`: Lógica do modo sequência
- `obterEscolhaDoUsuario()`: Valida entrada do usuário
- `atualizarRecorde()`: Gerencia os recordes do jogo
- `gerarSequencia()`: Cria a sequência para o modo sequência

## 📊 Exemplo de Jogada
```
Menu Principal:
1. Iniciar Novo Jogo
2. Ver Regras
3. Ver Histórico de Pontuações
4. Ver Recordes
5. Sair
Escolha uma opção: 1

Escolha o modo de jogo:
1. Modo Normal
2. Modo Sequência
Escolha: 1

Escolha o nível de dificuldade:
1. Fácil (1-50, 10 tentativas)
2. Médio (1-100, 7 tentativas)
3. Difícil (1-200, 5 tentativas)
Escolha: 2

Jogo iniciado! Tente adivinhar o número.
Tentativas restantes: 7. Insira seu palpite: 50
Palpite incorreto. O número secreto é menor.
Deseja uma dica? (Custo: 10 pontos)
1. Sim / 2. Não: 2
Tentativas restantes: 6. Insira seu palpite: 25
Parabéns! Você acertou o número 25!
Sua pontuação: 290
```

## 📌 Observações
- O jogo trata entradas inválidas de forma robusta
- Todas as operações são protegidas contra exceções
- O histórico é limitado às 10 últimas pontuações
- O sistema de recordes é mantido por nível de dificuldade

Divirta-se jogando! 🎉