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
| Backlog item             | Assignee        | D0  | D1  | D2  | D3  | D4  | D5  | D6  | D7  | D8  | D9  | D10 | D11 | D12 | D13 | D14 |
| ------------------------ | --------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Player tank type         | Alberto Arduini | 1   | 1   | 1   | 1   | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Player input controller  | Alberto Arduini | 5   | 3   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 0   | 0   | 0   | 0   | 0   | 0   |
| Tank movement            | Alberto Arduini | 3   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Level collision          | Alberto Arduini | 5   | 5   | 2   | 2   | 2   | 2   | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Obstacle definitions     | Alberto Arduini | 2   | 2   | 5   | 5   | 5   | 5   | 5   | 3   | 3   | 3   | 0   | 0   | 0   | 0   | 0   |
| Tank view                | Alberto Arduini | 2   | 2   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 
| Tank Data Structure      | Stefano Guidi   | 3   | 2   | 0   |     |     |     |     |     |     |     |     |     |     |     |     |
| Enemy Tank Type          | Stefano Guidi   | 2   | 2   | 0   |     |     |     |     |     |     |     |     |     |     |     |     |
| Power-up functionalities | Stefano Guidi   | 8   | 8   | 8   | 8   | 6   | 4   | 3   | 3   | 3   | 2   | 2   | 0   |     |     |     |
| Power-up data types      | Stefano Guidi   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 0   |     |     |     |     |
| Power-up view            | Stefano Guidi   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 0   |
| Bullet Structure         | Andrea Bianchi  | 5   | 5   | 3   | 3   | 3   | 3   | 1   | 1   | 3   | 3   | 3   | 2   | 1   | 0   |     |
| Bullet Entity            | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 2   | 3   | 3   | 3   |
| Tank Shooting            | Andrea Bianchi  | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 5   | 5   | 5   | 5   | 3   | 0   |
| Tank Damage/Elimination  | Andrea Bianchi  | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 5   |

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

### 6.2.1.3 Review Stefano Guidi
Il primo sprint è stato complessivamente positivo. E' stato implementato correttamente sia  un primo sistema generale per i Powerup che le strutture dati di base per il Tank e le loro tipologie. Ci si è resi conto di aver sottostimato la complessità nel realizzare un sistema di powerup efficace e di qualità, pertanto altre feature correlate (e.g. tipologie di  effetti per i potenziamenti e meccanismi generali per il loro uso) verranno suddivise in un' ulteriori feature per lo sprint successivo.
## 6.2.2 Sprint 2 - Dal 30 ottobre al 12 novembre
| Backlog item                 | Assignee        | D0  | D1  | D2  | D3  | D4  | D5  | D6  | D7  | D8  | D9  | D10 | D11 | D12 | D13 | D14 |
| ---------------------------- | --------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Entity models container      | Alberto Arduini | 5   | 5   | 3   | 2   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Entity controllers container | Alberto Arduini | 3   | 3   | 3   | 3   | 3   | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Entity creation              | Alberto Arduini | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 1   | 0   | 0   | 0   | 0   | 0   | 0   |
| Entity deletion              | Alberto Arduini | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 1   | 0   | 0   | 0   |
| Entity views manager         | Alberto Arduini | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 0   | 0   | 
| Power-up effect types        | Stefano Guidi   | 5   | 5   | 5   | 5   | 4   | 5   | 4   | 3   | 0   |     |     |     |     |     |     |
| Entity state machine         | Stefano Guidi   | 6   | 6   | 5   | 5   | 5   | 5   | 4   | 4   | 2   | 0   |     |     |     |     |     |
| Enemy Controller             | Stefano Guidi   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 0   |     |     |     |
| Ai Movement logic            | Stefano Guidi   | 4   | 5   | 5   | 5   | 7   | 7   | 7   | 7   | 5   | 5   | 4   | 3   | 3   | 5   | 5   |
| Bullet Entity                | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 0   |     |     |
| Bullet View                  | Andrea Bianchi  | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 1   | 1   | 1   | 1   | 0   |     |     |
| Tank Damage/Elimination      | Andrea Bianchi  | 5   | 5   | 5   | 5   | 5   | 5   | 5   | 2   | 2   | 2   | 2   | 2   | 2   | 0   |     |
| PLayer Life Loss and Respawn | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   |
| Player Gameover controller   | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   |
| Gameover View                | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   |

### 6.2.2.1 Review Alberto Arduini
Questo sprint è stato sicuramente più impegnativo del precedente, in quanto le task hanno necessitato un'attenzione particolare durante la progettazione.
Questo perché il sistema della gestione delle entità avrebbe dovuto aveva una alta integrazione con tutte le altre parti di progetto, sviluppate da me o da altri membri del team. E' stata quindi necessaria fare particolare attenzione ad avere non solo un sistema solido e ben strutturato, ma anche con un interfaccia esposta semplice e completa.
Da questa progettazione è risultata la repository delle entità con le sue estensioni, uno dei punti cardine dell'architettura del progetto.
I risultati li ho potuti vedere anche già nell'implementazione delle task finali dello sprint, in quanto si appoggiavano sul lavoro precedente. Queste sono state, infatti, molto semplici da sviluppare grazie alle fondamenta fornite dal sistema che ho creato in precedenza.
Inoltre, in questo sprint, ho anche aiutato Andrea Bianchi nello sviluppo della logica del *Controller* dei proiettili
### 6.2.2.2 Review Andrea Bianchi
Durante queste settimane numerosi imprevisti si sono presentati, limitando le mie ore di disponibilità necessarie per l'implementazione finale di questi user story. Malgrado tali difficoltà, sono riuscito ad usufruire ed integrare in una maniera che ritengo sia sufficientemente ottimizzata il lavoro dei miei colleghi, elaborando così una soluzione ideale per la risoluzione dei task necessari.
Durante le due settimane, ho creato una miglioria del sistema precedentemente utilizzato per sparare i proiettili del tank, offrendo la possibilità di stabilire la quantità di proiettili che ciascun tipo di tank sia in grado di sparare, oltre alla realizzazione finale della view e del controller per i bullets che vengono generati.
Delle 3 user story a cui ho lavorato in queste due settimane, le prime due sono state svolte in maniera parallela, date le numerose funzionalità comuni richieste da entrambe (creazione dei proiettili, sparo ed eliminazione dei tank). 
Purtroppo, l'ultima di queste, quella incentrata sul game over, è quella che ha più sofferto a causa dei problemi precedentemente citati: i numerosi imprevisti che hanno portato via *intere giornate di lavoro* mi hanno costretto a dedicare il tempo rimanente per il completamento dei task delle precedenti User Story.
Sono riuscito comunque a realizzare degli iniziali mockup per uno di questi e ho qualche idea per una possibile implementazione in grado di soddisfare quanto richiesto.

### 6.2.2.3 Stefano Guidi
Questo sprint ci sono state diverse problematiche. In questa fase ci si è concentrati sulla realizzazione di un sistema di AI per il movimento dei nemici e delle tipologie di powerup (feature introdotta alla fine dello sprint precedente). La realizzazione è risultata complicata a causa della genericità dei requisiti del dominio (il gioco originale presentava potenziamenti di natura molto diversa tra loro), inoltre essendo il progetto ancora in fase iniziale alcune funzionalità necessarie ad implementarlo era ancora mancanti, si è cercato pertanto di concentrarsi sulle caratteristiche comuni per costruire un sistema robusto sul quale possa risultare semplice aggiungere elementi mancanti in sprint successivi. 
Per quanto riguarda l'AI del movimento non si è riusciti a completare la realizzazione a causa di diverse motivazione (e.g. problemi nella correttezza dello unit testing e consistenza di alcuni stati del nemico), pertanto si è scelto di portare per la fine dello sprint una prima versione minimale ma funzionante per ottimizzare per quanto possibile il valore percepito per la fine dello sprint.
## 6.2.3 Sprint 3 - Dal 13 al 26 novembre
| Backlog item                              | Assignee        | D0  | D1  | D2  | D3  | D4  | D5  | D6  | D7  | D8  | D9  | D10 | D11 | D12 | D13 | D14 |
| ----------------------------------------- |-----------------| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Points UI                                 | Alberto Arduini | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Remaining enemies UI                      | Alberto Arduini | 2   | 2   | 2   | 1   | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Remaining player lifes UI                 | Alberto Arduini | 2   | 2   | 1   | 1   | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Level sequence and progression controller | Alberto Arduini | 5   | 5   | 5   | 5   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   |
| Damaged event on DamageableBehaviour      | Alberto Arduini | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Save player health on level completion    | Alberto Arduini | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   |
| Restore player health on level start      | Alberto Arduini | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 
| Global points manager                     | Alberto Arduini | 3   | 3   | 3   | 3   | 1   | 1   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Ai Movement Logic                         | Stefano Guidi   | 5   | 3   | 2   | 1   | 2   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   | 0   |
| Ai Focus Shooting                         | Stefano Guidi   | 4   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 4   | 3   |
| Enemy Timing Spawn Controller             | Stefano Guidi   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 1   | 0   |     |     |     |     |     |     |
| Sequence of enemies factory               | Stefano Guidi   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 0   |     |     |     |     |
| Generator Controller                      | Stefano Guidi   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 1   | 1   | 0   |     |     |     |
| Line of sight (frontend)                  | Stefano Guidi   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 1   | 0   |
| Player Gameover controller                | Andrea Bianchi  | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 1   | 0   |     |     |     |     |     |
| Player Life Loss and Respawn              | Andrea Bianchi  | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 0   |     |     |     |     |     |     |
| Gameover View                             | Andrea Bianchi  | 3   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 0   |     |     |     |     |     |     |
| Exit and Restart functions in Gameover    | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   |
| Level File Loader                         | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 1   | 1   | 1   | 1   | 0   |
| Enemy Sequence Loader                     | Andrea Bianchi  | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 1   | 0   |
| Level Tile Design                         | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 1   | 1   | 1   | 1   | 1   |
### 6.2.3.1 Review Alberto Arduini
Questo sprint si è concentrato sullo sviluppo dell'interfaccia utente da mostrare durante il gioco.
Per questo è stato abbastanza semplice, tuttavia, per poter accomodare vari layout in modo generico e avere un sistema per cambiarli in modo semplice, ha richiesto l'implementazione del sistema del [contesto di visualizzazione](4_Design_di_dettaglio.md#4%202%20Contesto%20di%20visualizzazione) delle *View*.
Inoltre, è stata implementa anche la gestione base dei punti, ma è rimasta però in sospeso per lo sprint successivo l'integrazione delle sue funzionalità con il resto del sistema.
Una nota riguardo a questo sprint è che, per via di impegni personali e di lavoro, non ho potuto fare progressi durante la seconda settimana, quindi è stato necessario anche un lavoro di organizzazione più puntuale per la decisione di quali task fossero più importanti e urgenti, in modo da poterle svolgere prima e lasciare in sospeso solo attività minori.
### 6.2.3.2 Review Andrea Bianchi
Vi si è dapprima concentrati sull'implementazione di una semplice funzione di *respawn* associata al PlayerTank. Una volta elaborato ciò, si è passato allo sviluppo di un controller dedicato alla *gestione del game over.*
Un lavoro non troppo difficile fatta eccezione per la funzione necessaria per ricominciare la partita si è rilevata più complessa del previsto.
Inoltre, durante la seconda settimana dello sprint, sono stato in grado di sviluppare un sistema di *caricamento appropriato dei livelli*, recuperando così sia il layout, sia la sequenza di nemici per ciascun livello.
Per concludere, ho poi cercato di ottimizzare gli strumenti e le funzioni create nei precedenti sprint per facilitarne l'integrazione e l'utilizzo con gli altri lavori
svolti dai colleghi: un compito che ha richiesto grande concentrazione e attenzione agli aspetti più *funzionali* per sostituire i vecchi metodi fin troppo *object-oriented.*

### 6.2.3.3 Review Stefano Guidi
In questo sprint ci si è occupato di risolvere le problematiche sorte alla fine del precedente sprint, ossia il completamento e miglioramento del sistema di Ai per il movimento (per terminare tale lavoro è stato richiesto circa un 40% dello sprint). Inoltre in questa fase si è iniziato ad integrare con il lavoro degli altri, in particolare il contesto di visualizzazione e l'entity repository. Ciò ha permesso di aggiungere ulteriore valore al risultato dello sprint.
Nel tempo rimanente ci si è occupati di sviluppare il sistema AI di focus shooting e alcune funzionalità correlate, la quale (seppur in maniera minima) è stata lasciata in sospeso per lo sprint successivo. Inoltre si è sviluppato un primo semplice sistema per la generazione di sequenze di nemici.
## 6.2.4 Sprint 4 - Dal 27 novembre al 9 dicembre
| Backlog item                               | Assignee        | D0  | D1  | D2  | D3  | D4  | D5  | D6  | D7  | D8  | D9  | D10 | D11 | D12 | D13 | D14 |
| ------------------------------------------ | --------------- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| Title screen view                          | Alberto Arduini | 5   | 5   | 3   | 2   | 0   |     |     |     |     |     |     |     |     |     |     |
| Pause menù action                          | Alberto Arduini | 2   | 2   | 2   | 2   | 0   |     |     |     |     |     |     |     |     |     |     |
| Pause UI                                   | Alberto Arduini | 1   | 1   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |     |
| Point bonus preset                         | Alberto Arduini | 2   | 2   | 2   | 2   | 2   | 0   |     |     |     |     |     |     |     |     |     |
| Add points on events                       | Alberto Arduini | 2   | 2   | 2   | 2   | 2   | 1   |     |     |     |     |     |     |     |     |     |
| Pause game logic                           | Alberto Arduini | 2   | 2   | 1   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |
| Level Sequence and Progressione Controller | Alberto Arduini | 2   | 2   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |     |
| Save player health on level completion     | Alberto Arduini | 1   | 1   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |     |
| Restore player health on level start  | Alberto Arduini | 1   | 1   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |     |
| Powerup object Controller                  | Stefano Guidi   | 3   | 2   | 0   |     |     |     |     |     |     |     |     |     |     |     |     |
| Ai Focus Shooting                          | Stefano Guidi   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 3   | 1   | 2   | 2   |     |     |     |
| Charged Tanks                              | Stefano Guidi   | 2   | 2   | 2   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |
| Position provider mechanism                | Stefano Guidi   | 3   | 3   | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 0   |     |     |     |     |     |
| PickablePowerUp spawn                      | Stefano Guidi   | 2   | 2   | 1   | 1   | 1   | 2   | 2   | 1   | 1   | 0   |     |     |     |     |     |
| Powerup Binder Controller                  | Stefano Guidi   | 4   | 3   | 4   | 4   | 3   | 3   | 3   | 2   | 0   |     |     |     |     |     |     |
| Level Tile Design                          | Andrea Bianchi  | 1   | 1   | 0   |     |     |     |     |     |     |     |     |     |     |     |     |
| Game Option View                           | Andrea Bianchi  | 3   | 3   | 2   | 2   | 1   | 1   | 1   | 0   |     |     |     |     |     |     |     |
| Reset Best Score Option                    | Andrea Bianchi  | 2   | 2   | 1   | 1   | 1   | 1   | 0   |     |     |     |     |     |     |     |     |
| Set Username Option                        | Andrea Bianchi  | 2   | 2   | 1   | 1   | 1   | 1   | 0   |     |     |     |     |     |     |     |     |
| Exit and Restart functions in Gameover     | Andrea Bianchi  | 2   | 2   | 2   | 2   | 2   | 1   | 1   | 1   | 1   | 1   | 0   |     |     |     |     |
| Bullet Fixes                               | Andrea Bianchi  | 3   | 3   | 3   | 3   | 3   | 2   | 2   | 1   | 1   | 0   |     |     |     |     |     |
| Data Saved and Loaded from External File   | Andrea Bianchi  | 2   | 2   | 2   | 2   | 2   | 2   | 2   | 1   | 1   | 0   |     |     |     |     |     |
### 6.2.4.1 Review Alberto Arduini
Per quest'ultimo sprint mi sono concentrato sugli ultimi aspetti dell'interfaccia utente, in modo da poter avere il ciclo di vita dell'applicazione completo.
In particolare, ho implementato l'astrazione del game loop e dell'inizializzazione del gioco, oltre che i menù iniziali e di pausa.
Infine, mi sono occupato dell'integrazione finale di tutti i sistemi di gioco, aiutato per ogni feature dall'implementatore originale e facendo eventuali modifiche di adattamento dove necessario.
Ho avuto alcune difficolta, soprattutto negli ultimi giorni dello sprint, per vari problemi di salute e personali, cosa che mi ha molto rallentato nello sviluppo, anche se sono riuscito a svolgere la maggior parte del lavoro pianificato in tempo.
### 6.2.4.2 Review Andrea Bianchi
L'ultimo sprint è stato quello decisivo: ho passato la prima metà a realizzare i componenti finali, quali la *lettura di dati da un file esterno al programma*
e la creazione di un *nuovo menù* secondario contenente una serie di svariate *opzioni* per il giocatore.
Terminate queste iniziali implementazioni, ho *finalizzato* le view di game over e delle opzioni, sistemando errori presenti nel metodo di restart e altre imprecisioni di minor importanza.
Durante ciò, grazie ai confronti e l'aiuto dei miei colleghi, ho *esteso il lavoro svolto precedentemente per il modello e il controller dei proiettili del gioco*, integrandolo appieno
con il mondo di gioco e correggendo i problemi di movimento, direzione e collisione che si erano riscontrati durante le fasi di testing.
### 6.2.4.3 Review Stefano Guidi
Quest'ultimo sprint ci si è concentrati sul realizzare aspetti di lifecycle per feature consistenti con il lavoro precedentemente svolto (in particolare l'integrazione di ulteriori funzionalità e miglioramenti per la generazione di sequenze dei nemici in ogni livello, oltre che una gestione dei potenziamenti raccoglibili nel livello). Inoltre ci si è occupati di sistemare alcuni dettagli su alcune feature che precedentemente erano rimaste bloccanti (tali modifiche non hanno apportato problemi ne eccessivo tempo per attuarle). In generale le problematiche principali sono state quelle relative a problemi di integrazione su alcune componenti che, fino a quel momento, non erano state ancora messe in relazione tra loro.
# 6.3 Commenti finali
Lo svolgimento di questo progetto ci ha permesso di provare con mano e analizzare tutto il processo di sviluppo di un *software* con un occhio più rigido e professionale, non solo per l'utilizzo di una metodologia (Scrum) più rigida e strutturata, ma anche per quanto riguarda le interazioni con il team, sia in termini di stesura del codice che di verifica immediata della qualità.
Inoltre, ci ha permesso di conoscere il funzionamento degli strumenti di supporto allo sviluppo (ClickUp e le GitHub Actions in questo), cosa che sarà utile conoscere già in partenza in futuro per impostare eventuali progetti futuri in modo più veloce e consapevole, anche in caso di cambiamento di questi strumenti.