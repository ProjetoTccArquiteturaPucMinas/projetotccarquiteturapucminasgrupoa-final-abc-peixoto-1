## Cenários de testes de bancada

### Como usar estes cenários
1. Execute a aplicação Spring Boot(Como esta descrito no README.md)
2. Acesse http://localhost:8080
3. Para cada cenário, selecione os produtos e quantidades indicadas
4. Clique em "Montar carrinho"
5. Verifique se os valores calculados correspondem aos valores esperados

### Produtos disponíveis
| ID | Nome | Preço | Categoria |
|----|------|-------|-----------|
| 1 | Capinha Premium | R$ 49,90 | CAPINHA |
| 2 | Carregador Turbo 30W | R$ 119,90 | CARREGADOR |
| 3 | Fone Bluetooth AirSound | R$ 199,90 | FONE |
| 4 | Película 3D | R$ 29,90 | PELICULA |
| 5 | Suporte Veicular Magnético | R$ 59,90 | SUPORTE |

### Cenário 1: Um único item (PELICULA)
**Entrada:** 1x Película 3D (ID: 4)
- Subtotal: R$ 29,90
- Desconto por quantidade: 0% (1 item)
- Desconto por categoria: 2% (PELICULA)
- Desconto total: 2%
- Valor do desconto: R$ 0,60
- **Total esperado: R$ 29,30**

### Cenário 2: Um único item (CAPINHA)
**Entrada:** 1x Capinha Premium (ID: 1)
- Subtotal: R$ 49,90
- Desconto por quantidade: 0% (1 item)
- Desconto por categoria: 3% (CAPINHA)
- Desconto total: 3%
- Valor do desconto: R$ 1,50
- **Total esperado: R$ 48,40**

### Cenário 3: Dois itens de categorias diferentes
**Entrada:** 1x Película 3D (ID: 4) + 1x Suporte Veicular (ID: 5)
- Subtotal: R$ 89,80
- Desconto por quantidade: 5% (2 itens)
- Desconto por categoria: 4% (2% PELICULA + 2% SUPORTE)
- Desconto total: 9%
- Valor do desconto: R$ 8,08
- **Total esperado: R$ 81,72**

### Cenário 4: Três itens
**Entrada:** 1x Capinha (ID: 1) + 1x Película (ID: 4) + 1x Suporte (ID: 5)
- Subtotal: R$ 139,70
- Desconto por quantidade: 7% (3 itens)
- Desconto por categoria: 7% (3% CAPINHA + 2% PELICULA + 2% SUPORTE)
- Desconto total: 14%
- Valor do desconto: R$ 19,56
- **Total esperado: R$ 120,14**

### Cenário 5: Quatro itens (desconto máximo NÃO atingido)
**Entrada:** 1x Capinha (ID: 1) + 1x Película (ID: 4) + 1x Suporte (ID: 5) + 1x Fone (ID: 3)
- Subtotal: R$ 339,60
- Desconto por quantidade: 10% (4 itens)
- Desconto por categoria: 10% (3% CAPINHA + 2% PELICULA + 2% SUPORTE + 3% FONE)
- Desconto total: 20%
- Valor do desconto: R$ 67,92
- **Total esperado: R$ 271,68**

### Cenário 6: Quatro itens (desconto máximo ATINGIDO)
**Entrada:** 1x Carregador (ID: 2) + 1x Fone (ID: 3) + 1x Capinha (ID: 1) + 1x Película (ID: 4)
- Subtotal: R$ 399,60
- Desconto por quantidade: 10% (4 itens)
- Desconto por categoria: 13% (5% CARREGADOR + 3% FONE + 3% CAPINHA + 2% PELICULA)
- Desconto total calculado: 23%
- Desconto total aplicado: 23% (não excede 25%)
- Valor do desconto: R$ 91,91
- **Total esperado: R$ 307,69**

### Cenário 7: Cinco itens (testando limite de 25%)
**Entrada:** 1x Carregador (ID: 2) + 1x Fone (ID: 3) + 1x Capinha (ID: 1) + 1x Película (ID: 4) + 1x Suporte (ID: 5)
- Subtotal: R$ 459,50
- Desconto por quantidade: 10% (5 itens)
- Desconto por categoria: 15% (5% CARREGADOR + 3% FONE + 3% CAPINHA + 2% PELICULA + 2% SUPORTE)
- Desconto total calculado: 25%
- Desconto total aplicado: 25% (limite máximo)
- Valor do desconto: R$ 114,88
- **Total esperado: R$ 344,62**

### Cenário 8: Dois itens da mesma categoria
**Entrada:** 2x Capinha Premium (ID: 1) - quantidade = 2 do mesmo produto
- Subtotal: R$ 99,80
- Desconto por quantidade: 5% (2 itens)
- Desconto por categoria: 6% (3% CAPINHA x2 itens)
- Desconto total: 11%
- Valor do desconto: R$ 10,98
- **Total esperado: R$ 88,82**

### Cenário 9: Três itens com categorias repetidas
**Entrada:** 2x Película (ID: 4) + 1x Suporte (ID: 5)
- Subtotal: R$ 119,70
- Desconto por quantidade: 7% (3 itens)
- Desconto por categoria: 6% (2% PELICULA x2 + 2% SUPORTE x1)
- Desconto total: 13%
- Valor do desconto: R$ 15,56
- **Total esperado: R$ 104,14**

### Cenário 10: Produto mais caro (Fone)
**Entrada:** 1x Fone Bluetooth (ID: 3)
- Subtotal: R$ 199,90
- Desconto por quantidade: 0% (1 item)
- Desconto por categoria: 3% (FONE)
- Desconto total: 3%
- Valor do desconto: R$ 6,00
- **Total esperado: R$ 193,90**

### Cenário 11: Testando se limite de 25% funciona com múltiplos carregadores
**Entrada:** 4x Carregador Turbo 30W (ID: 2)
- Subtotal: R$ 479,60
- Desconto por quantidade: 10% (4 itens)
- Desconto por categoria: 20% (5% CARREGADOR x4 itens)
- Desconto total calculado: 30%
- Desconto total aplicado: 25% (limite máximo)
- Valor do desconto: R$ 119,90
- **Total esperado: R$ 359,70**

### Cenário 12: Dois produtos de categorias com desconto baixo
**Entrada:** 1x Película (ID: 4) + 1x Suporte (ID: 5)
- Subtotal: R$ 89,80
- Desconto por quantidade: 5% (2 itens)
- Desconto por categoria: 4% (2% PELICULA + 2% SUPORTE)
- Desconto total: 9%
- Valor do desconto: R$ 8,08
- **Total esperado: R$ 81,72**