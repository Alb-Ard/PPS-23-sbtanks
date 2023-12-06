# 6.1 Evoluzione del Backlog
Il progetto è iniziato con l'individuazione di 3 task *epic* fondamentali per la base del gioco:
- User Interation: *user story* riguardanti l'interazione del giocatore e del su *tank* con il gioco;
- Enemy Actions: *user story* riguardanti l'AI dei nemici e delle loro interazioni con il mondo di gioco;
- Game Enviroment: *user story* riguardanti il gioco in sé e alcune delle sue meccaniche esterne a ciò che avviene durante una partita.
Con l'avanzare dello sviluppo, si sono aggiunte alcune voci riguardanti ciò che completa il gioco, ma che non fanno parte del *core*:
- Una *epic* Lifecycle riguardante la gestione del ciclo di vita dell'applicazione in generale;
- Alcune singole *user story* riguardanti la composizione del mondo di gioco e la sua visualizzazione;
- Alcune singole *user story* riguardanti i menù e la parte di *UI*.
# 6.2 Andamento degli sprint e delle iterazioni
## 6.2.1 Sprint 1 - Dal 16 al 29 ottobre
|Backlog item|Assignee|D0|D1|D2|D3|D4|D5|D6|D7|D8|D9|D10|D11|D12|D13|D14|
|---------|--------|--|--|--|--|--|--|--|--|--|--|---|---|---|---|---|
****N.B:** In questa tabella e nelle prossime a seguire i valori per singolo giorno sono riportati come numero di sprint point*
Il primo sprint ha prodotto le basi necessarie su cui si sarebbe poi sviluppata l'applicazione.
Nello specifico, è stata creata la base per il controllo del giocatore e per gli ostacoli, è stata iniziata la meccanica di sparo dei proiettili e sono state create le varie tipologie di *tank* avversari.
### 6.2.1.1 Review Alberto Arduini
Questo primo sprint è stato svolto senza grandi intoppi, riuscendo a portare a termine tutte le task a me assegnate.
Unico rallentamento è stato causato da un errore in fase di design che sono comunque stati corretti in breve tempo.
### 6.2.1.2 Review Andrea Bianchi
Il lavoro si è presentato più complesso del previsto, date le iniziali difficoltà nel capire come lavorare tramite Sprint e come adattare
il mio codice con il lavoro svolto dai miei colleghi, sottovalutandone decisamente i requisiti. Le settimane quindi sono state spese principalmente sull'imparare ad adattarsi a questo
nuovo modello di lavoro e a realizzare i compiti richiesti. Sono quindi riuscito a realizzare gli iniziali modelli a livello funzionale per il Bullet generato.
Nonostante ciò, il controller per gestire aspetti di collisione tra Bullet/Tanks/Obstacles e la
rispettiva view, sono ancora in fase di sviluppo e necessitano ulteriore tempo per completare le loro funzionalità.
## 6.2.2 Sprint 2 - Dal 30 ottobre al 12 novembre
### 6.2.2.2 Review Andrea Bianchi
Durante queste settimane numerosi imprevisti si sono presentati, limitando le mie ore di disponibilità necessarie per l'implementazione finale di questi user story. Malgrado tali difficoltà, sono riuscito ad usufruire ed integrare in una maniera che ritengo sia sufficientemente ottimizzata il lavoro dei miei colleghi, elaborando così una soluzione ideale per la risoluzione dei task necessari.
Durante le due settimane, ho creato una miglioria del sistema precedentemente utilizzato per sparare i proiettili del tank, offrendo la possibilità di stabilire la quantità di proiettili che ciascun tipo di tank sia in grado di sparare, oltre alla realizzazione finale della view e del controller per i bullets che vengono generati.
Delle 3 user story a cui stavo lavorando in queste due settimane, le prime due sono state svolte in maniera parallela, date le numerose funzionalità comuni richieste da entrambe. 
Purtroppo, l'ultima di queste è quella che ha più sofferto a causa dei problemi precedentemente citati: i numerosi imprevisti che hanno portato via *intere giornate di lavoro* mi hanno costretto a dedicare il tempo rimanente per il completamento dei task delle precedenti User Story.
Sono riuscito comunque a realizzare degli iniziali mockup per uno di questi e ho qualche idea per una possibile implementazione in grado di soddisfare quanto richiesto.
## 6.2.3 Sprint 3 - Dal 13 al 26 novembre
### 6.2.3.2 Review Andrea Bianchi
## 6.2.4 Sprint 4 - Dal 27 novembre al 9 dicembre
### 6.2.4.2 Review Andrea Bianchi
# 6.3 Commenti finali
Lo svolgimento di questo progetto ci ha permesso di provare con mano e analizzare tutto il processo di sviluppo di un *software* con un occhio più rigido e professionale, non solo per l'utilizzo di una metodologia (Scrum) più rigida e strutturata, ma anche per quanto riguarda le interazioni con il team, sia in termini di stesura del codice che di verifica immediata della qualità.
Inoltre, ci ha permesso di conoscere il funzionamento degli strumenti di supporto allo sviluppo (ClickUp e le GitHub Actions in questo), cosa che sarà utile conoscere già in partenza in futuro per impostare eventuali progetti futuri in modo più veloce e consapevole, anche in caso di cambiamento di questi strumenti.