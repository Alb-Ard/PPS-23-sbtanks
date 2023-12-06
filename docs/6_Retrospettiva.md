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
## 6.2.2 Sprint 2 - Dal 30 ottobre al 12 novembre
## 6.2.3 Sprint 3 - Dal 13 al 26 novembre
## 6.2.4 Sprint 4 - Dal 27 novembre al 9 dicembre
# 6.3 Commenti finali
Lo svolgimento di questo progetto ci ha permesso di provare con mano e analizzare tutto il processo di sviluppo di un *software* con un occhio più rigido e professionale, non solo per l'utilizzo di una metodologia (Scrum) più rigida e strutturata, ma anche per quanto riguarda le interazioni con il team, sia in termini di stesura del codice che di verifica immediata della qualità.
Inoltre, ci ha permesso di conoscere il funzionamento degli strumenti di supporto allo sviluppo (ClickUp e le GitHub Actions in questo), cosa che sarà utile conoscere già in partenza in futuro per impostare eventuali progetti futuri in modo più veloce e consapevole, anche in caso di cambiamento di questi strumenti.