## Facebook Scraping

Essa ferramente foi desenvolvida para auxiliar pesquisadores, específicamente historiadores, nos estudos de postagens de grupos públicos do Facebook. A partir do recebimento de um link de postagem o código faz a raspagem dos comentários associados à postagem. Permitindo que o trabalho de colega de informações ganhe agilidade. A ferramenta foi pensada para o oficio do historiador nos meios digitais, por isso sua inclinação para conteúdos que possuem informações sobre História.

---

[//]: # (**Configurações para execução:**)

[//]: # ()
[//]: # (- **Execução a partir do Jar**)

[//]: # (    * Se posicionar na raiz do projeto e executar os comandos abaixo:)

[//]: # (    * `mvn clean package`)

[//]: # (    * `java -jar target/*.jar arquivo_com_lista_de_links.txt`)

-----

**Características da API**

- Ela foi desenvolvida utilizando Java 17
- Entre as bibliotecas utilizadas estão
    * Selenium 4.15.0

-----

**Funcionalidades da atual versão**

- Raspagem de todos os comentários (inclusive os inicialmente ocultos e as respostas de comentários) de postagems públicas de grupos do Facebook;
- A impressão dos comentários no output;
- adição das informações do usuário e da distância que o comentário foi postado com relação à data da raspagem;
- adição para salvar um .csv com as informações de data raspagem, nome, comentário, distancia da dadata da raspagem e link da publicação original;