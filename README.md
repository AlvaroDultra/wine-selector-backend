# 🍷 Wine Selector - Backend

Sistema inteligente de recomendação de vinhos baseado em regras determinísticas. Analisa três dimensões (prato, ocasião e intimidade) para sugerir o perfil de vinho ideal.

## 🎯 Sobre o Projeto

O Wine Selector não usa IA generativa ou algoritmos probabilísticos. Toda recomendação é baseada em um **motor de regras transparente e calibrável**, que combina conhecimento enológico com contexto social para oferecer sugestões personalizadas e explicáveis.

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.2**
- **Maven**
- **Lombok**
- **Jakarta Validation**

## 📐 Arquitetura
```
├── model/          # Enums (perfis, pratos, ocasiões) e DTOs
├── rules/          # Motor de regras (DishRules, OccasionRules, IntimacyRules)
├── service/        # Lógica de negócio (ScoreCalculator, RecommendationService)
├── controller/     # API REST
├── config/         # Configurações (CORS)
└── exception/      # Tratamento de erros
```

## 🧮 Como Funciona

### Dimensões de Análise

1. **Prato Principal (50% do peso)**: Harmonização técnica baseada em características do prato
2. **Ocasião (30% do peso)**: Contexto social e formalidade do evento
3. **Nível de Intimidade (20% do peso)**: Regula risco social da escolha

### Sistema de Pontuação
```
Pontuação Final = (Prato × 0.5) + (Ocasião × 0.3) + (Intimidade × 0.2)
```

O sistema retorna:
- **Perfil recomendado** com justificativa detalhada
- **Perfil alternativo** quando houver empate técnico (diferença ≤ 10 pontos)

## 📊 Opções Disponíveis

### Perfis de Vinho (7)
- Tinto Leve, Tinto Médio, Tinto Encorpado
- Branco Leve, Branco Estruturado
- Rosé, Espumante

### Pratos (12)
Carnes vermelhas, Carnes brancas, Peixes e frutos do mar, Massas (molho vermelho/branco), Risoto, Pizza, Churrasco, Comida asiática, Queijos e frios, Vegetariano, Comida apimentada

### Ocasiões (7)
Reunião de negócios, Jantar romântico, Primeiro encontro, Entre amigos, Celebração, Jantar em família, Casual

### Níveis de Intimidade (5)
Primeiro encontro, Conhecido, Colega de trabalho, Amigo próximo, Íntimo/Familiar

## 🔧 Como Rodar

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Executar
```bash
# Clonar repositório
git clone https://github.com/AlvaroDultra/wine-selector-backend.git
cd wine-selector-backend

# Compilar
mvn clean install

# Rodar
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`

## 📡 Endpoints

### POST `/api/recommendation`
Retorna recomendação de vinho

**Request:**
```json
{
  "occasion": "JANTAR_ROMANTICO",
  "intimacyLevel": "PRIMEIRO_ENCONTRO",
  "mainDish": "CARNES_VERMELHAS"
}
```

**Response:**
```json
{
  "recommendedProfile": "TINTO_ENCORPADO",
  "displayName": "Tinto Encorpado",
  "description": "Vinho tinto intenso...",
  "justification": "Harmoniza perfeitamente com carnes vermelhas...",
  "score": 33,
  "alternativeProfile": "TINTO_MEDIO",
  "alternativeDisplayName": "Tinto Médio",
  "alternativeScore": 31
}
```

### GET `/api/recommendation/health`
Health check da API

## 🧪 Exemplos de Uso
```bash
# Churrasco entre amigos
curl -X POST http://localhost:8080/api/recommendation \
  -H "Content-Type: application/json" \
  -d '{
    "occasion": "ENTRE_AMIGOS",
    "intimacyLevel": "AMIGO_PROXIMO",
    "mainDish": "CHURRASCO"
  }' | jq

# Sushi em primeiro encontro
curl -X POST http://localhost:8080/api/recommendation \
  -H "Content-Type: application/json" \
  -d '{
    "occasion": "PRIMEIRO_ENCONTRO",
    "intimacyLevel": "PRIMEIRO_ENCONTRO",
    "mainDish": "COMIDA_ASIATICA"
  }' | jq
```

## 👨‍💻 Autor

**Alvaro Dultra**
- GitHub: [@AlvaroDultra](https://github.com/AlvaroDultra)
- LinkedIn: [Alvaro Dultra](https://linkedin.com/in/alvarodultra)

## 📄 Licença

MIT License

---

⭐ Se este projeto te ajudou, considere dar uma estrela!
