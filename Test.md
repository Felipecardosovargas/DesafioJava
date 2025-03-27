# Testando o Jogo de Adivinhação de Modo Profissional

Para testar seu jogo de forma profissional, recomendo uma abordagem estruturada que combine testes manuais e automatizados, cobrindo diferentes aspectos da aplicação.

## 🔍 Estratégia de Testes Profissionais

### 1. Testes Unitários (JUnit)
Crie testes unitários para as principais funções lógicas:

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DesafioModuloTest {
    
    @Test
    void testGerarSequencia() {
        List<Integer> sequencia = DesafioModulo.gerarSequencia(50);
        assertEquals(3, sequencia.size());
        assertTrue(sequencia.get(0) >= 1 && sequencia.get(0) <= 50);
    }
    
    @Test
    void testObterDica() {
        assertEquals("O número secreto é maior.", DesafioModulo.obterDica(50, 30));
        assertEquals("O número secreto é menor.", DesafioModulo.obterDica(50, 70));
    }
    
    @Test
    void testAtualizarRecorde() {
        NivelDificuldade nivel = NivelDificuldade.FACIL;
        Score novoRecorde = new Score(nivel, 500);
        DesafioModulo.atualizarRecorde(novoRecorde);
        assertEquals(500, DesafioModulo.encontrarRecorde(nivel).getPontuacao());
    }
}
```

### 2. Testes de Integração
Teste o fluxo completo do jogo:

```java
@Test
void testFluxoCompletoJogoNormal() {
    // Simula entrada do usuário
    InputStream sysInBackup = System.in; 
    String input = "1\n1\n10\n20\n30\n40\n50\n";
    ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
    System.setIn(in);
    
    // Executa o jogo
    DesafioModulo.main(new String[]{});
    
    // Restaura System.in
    System.setIn(sysInBackup);
    
    // Verifica se a pontuação foi registrada
    assertFalse(DesafioModulo.historicoPontuacoes.isEmpty());
}
```

### 3. Testes de Interface (Selenium ou TestFX)
Para testes de UI automatizados:

```java
// Exemplo com TestFX (para JavaFX)
@Test
void testMenuPrincipal() {
    clickOn("#botaoNovoJogo");
    verifyThat("#telaJogo", Node::isVisible);
}
```

## 🧪 Tipos de Testes a Realizar

### 1. Testes Funcionais
- [ ] Todos os itens do menu funcionam
- [ ] Modo Normal com diferentes níveis
- [ ] Modo Sequência com diferentes níveis
- [ ] Sistema de pontuação calcula corretamente
- [ ] Histórico e recordes são atualizados

### 2. Testes de Validação de Entrada
- [ ] Entradas não numéricas
- [ ] Números fora do intervalo
- [ ] Opções de menu inválidas
- [ ] Teste de limites (1, 50, 100, 200)

### 3. Testes de Performance
```java
@Test
void testPerformanceGeracaoNumeros() {
    long startTime = System.nanoTime();
    for (int i = 0; i < 1000; i++) {
        DesafioModulo.gerarSequencia(200);
    }
    long duration = System.nanoTime() - startTime;
    assertTrue(duration < 100_000_000); // < 100ms
}
```

### 4. Testes de Segurança
- [ ] Injeção de comandos na entrada
- [ ] Overflow numérico
- [ ] Acesso concorrente às listas

## 🛠️ Ferramentas Recomendadas

1. **JUnit 5** - Para testes unitários e de integração
2. **Mockito** - Para mock de dependências
3. **TestFX** - Para testes de interface (se usar JavaFX)
4. **JaCoCo** - Para cobertura de código
5. **JMH** - Para microbenchmarks (opcional)

## 📊 Exemplo de Plano de Testes

| Caso de Teste | Tipo | Descrição | Critério de Aceitação |
|--------------|------|-----------|----------------------|
| CT001 | Unitário | gerarSequencia deve retornar 3 números | Lista com 3 elementos entre 1 e max |
| CT002 | Funcional | Jogo Normal nível fácil | Acerto dentro de 10 tentativas |
| CT003 | Validação | Entrada de caractere no menu | Mensagem de erro clara |
| CT004 | Performance | 1000 gerações de sequência | Tempo < 100ms |
| CT005 | Segurança | Entrada de texto no palpite | Tratamento adequado |

## 🔄 Processo de CI/CD (Integração Contínua)

Configure um pipeline automatizado com:

1. **Compilação** - `javac DesafioModulo.java`
2. **Testes Unitários** - `mvn test` ou `gradle test`
3. **Cobertura** - Verifique com JaCoCo (mínimo 80%)
4. **Análise Estática** - SonarQube ou Checkstyle
5. **Empacotamento** - Crie um JAR executável

## 📝 Relatório de Testes

Gere relatórios profissionais com:

```xml
<!-- Exemplo de configuração do Surefire para relatórios -->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <reportFormat>plain</reportFormat>
        <includes>
            <include>**/*Test.java</include>
        </includes>
    </configuration>
</plugin>
```

## 💡 Dicas para Testes Efetivos

1. **Teste os casos extremos**:
    - Primeira e última tentativa
    - Menor e maior número possível
    - Pontuações mínimas e máximas

2. **Automatize o máximo possível**:
   ```java
   @ParameterizedTest
   @ValueSource(ints = {1, 50, 100, 200})
   void testLimitesNumericos(int input) {
       assertDoesNotThrow(() -> {
           // Lógica que usa o input
       });
   }
   ```

3. **Simule falhas**:
   ```java
   @Test
   void testComFalhaNaEntrada() {
       provideInput("invalid\n1\n"); // Primeiro inválido, depois válido
       assertDoesNotThrow(() -> DesafioModulo.obterEscolhaDoUsuario());
   }
   ```

4. **Verifique estado após testes**:
   ```java
   @AfterEach
   void limpaEstado() {
       DesafioModulo.historicoPontuacoes.clear();
       DesafioModulo.recordes.clear();
   }
   ```

Esta abordagem profissional garantirá que seu jogo seja robusto, confiável e de alta qualidade antes da distribuição.