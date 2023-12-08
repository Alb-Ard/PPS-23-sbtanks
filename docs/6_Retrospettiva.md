# 6.1 Avviamento ed gestione del Backlog
## 6.1.1 Impostazione della repository
L'avviamento del progetto ha riguardato l'impostazione della repository GitHub, della creazione della GitHub Action di scala per la *CI/CD* comprendente:
1. La compilazione del progetto;
2. L'esecuzione dei test;
3. La notifica delle dipendenze utilizzate a GitHub per poter poi verificare eventuali criticità.
## 6.1.2 Creazione del workspace ClickUp
Parallelamente, è stato creato il workspace su ClickUp, con la creazione del Backlog principale con le *user story* e gli *epic* del progetto, più un Backlog separato per le task di gestione e manutenzione del progetto.
E' stata fatta questa scelta per poter mantenere più pulita la visualizzazione delle task, sfruttando poi il sistema di aggregazione degli elenchi che ClickUp fornisce per avere una visione di insieme quando necessario.
## 6.1.3 Evoluzione del backlog
Il progetto è iniziato con l'individuazione di tre task *epic* fondamentali per la base del gioco:
- User Interation: *user story* riguardanti l'interazione del giocatore e del su *tank* con il gioco;
- Enemy Actions: *user story* riguardanti l'AI dei nemici e delle loro interazioni con il mondo di gioco;
- Game Enviroment: *user story* riguardanti il gioco in sé e alcune delle sue meccaniche esterne a ciò che avviene durante una partita.
Con l'avanzare dello sviluppo, si sono aggiunte alcune voci riguardanti ciò che completa il gioco, ma che non fanno parte del *core*:
- Una *epic* Lifecycle riguardante la gestione del ciclo di vita dell'applicazione in generale;
- Alcune singole *user story* riguardanti la composizione del mondo di gioco e la sua visualizzazione;
- Alcune singole *user story* riguardanti i menù e la parte di *UI*.
# 6.2 Andamento degli sprint e iterazioni
## 6.2.1 Sprint 1 - Dal 16 al 29 ottobre
|Backlog item|Assignee|D0|D1|D2|D3|D4|D5|D6|D7|D8|D9|D10|D11|D12|D13|D14|
|---------|--------|--|--|--|--|--|--|--|--|--|--|---|---|---|---|---|
***N.B.:** In questa tabella e nelle prossime a seguire i valori per singolo giorno sono riportati come numero di sprint point*
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
### 6.2.3.1 Review Alberto Arduini
Questo sprint si è concentrato sullo sviluppo dell'interfaccia utente da mostrare durante il gioco.
Per questo è stato abbastanza semplice, tuttavia, per poter accomodare vari layout in modo generico e avere un sistema per cambiarli in modo semplice, ha richiesto l'implementazione del sistema del [contesto di visualizzazione](4_Design_di_dettaglio.md#4%202%20Contesto%20di%20visualizzazione) delle *View*.
Inoltre, è stata implementa anche la gestione base dei punti, ma è rimasta però in sospeso per lo sprint successivo l'integrazione delle sue funzionalità con il resto del sistema.
### 6.2.3.2 Review Andrea Bianchi
Vi si è dapprima concentrati sull'implementazione di una semplice funzione di *respawn* associata al PlayerTank. Una volta elaborato ciò, si è passato allo sviluppo di un controller dedicato alla *gestione del game over.*
Un lavoro non troppo difficile fatta eccezione per la funzione necessaria per ricominciare la partita si è rilevata più complessa del previsto.
Inoltre, durante la seconda settimana dello sprint, sono stato in grado di sviluppare un sistema di *caricamento appropriato dei livelli*, recuperando così sia il layout, sia la sequenza di nemici per ciascun livello.
Per concludere, ho poi cercato di ottimizzare gli strumenti e le funzioni create nei precedenti sprint per facilitarne l'integrazione e l'utilizzo con gli altri lavori
svolti dai colleghi: un compito che ha richiesto grande concentrazione e attenzione agli aspetti più *funzionali* per sostituire i vecchi metodi fin troppo *object-oriented.*
## 6.2.4 Sprint 4 - Dal 27 novembre al 9 dicembre
### 6.2.4.2 Review Andrea Bianchi
L'ultimo sprint è stato quello decisivo: ho passato la prima metà a realizzare i componenti finali, quali la *lettura di dati da un file esterno al programma*
e la creazione di un *nuovo menù* secondario contenente una serie di svariate *opzioni* per il giocatore.
Terminate queste iniziali implementazioni, ho *finalizzato* le view di game over e delle opzioni, sistemando errori presenti nel metodo di restart e altre imprecisioni di minor importanza.
Durante ciò, grazie ai confronti e l'aiuto dei miei colleghi, ho *esteso il lavoro svolto precedentemente per il modello e il controller dei proiettili del gioco*, integrandolo appieno
con il mondo di gioco e correggendo i problemi di movimento, direzione e collisione che si erano riscontrati durante le fasi di testing.
# 6.3 Commenti finali
Lo svolgimento di questo progetto ci ha permesso di provare con mano e analizzare tutto il processo di sviluppo di un *software* con un occhio più rigido e professionale, non solo per l'utilizzo di una metodologia (Scrum) più rigida e strutturata, ma anche per quanto riguarda le interazioni con il team, sia in termini di stesura del codice che di verifica immediata della qualità.
Inoltre, ci ha permesso di conoscere il funzionamento degli strumenti di supporto allo sviluppo (ClickUp e le GitHub Actions in questo), cosa che sarà utile conoscere già in partenza in futuro per impostare eventuali progetti futuri in modo più veloce e consapevole, anche in caso di cambiamento di questi strumenti.