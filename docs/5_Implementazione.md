Implementazione (per ogni studente, una sotto-sezione descrittiva di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti non già presenti nel design, come ad esempio relativamente all'uso di meccanismi avanzati di Scala).
# 5.1 Alberto Arduini
Io mi sono occupato principalmente della parte di codice riguardante il *backend* del gioco, quindi la gestione delle entità e delle viste, oltre che del ciclo di vita.
Per la parte di *frontend*, invece, ho sviluppato il menù principale e quello di pausa, oltre che le il comportamento e viste di *tank* e ostacoli.
## 5.1.1 Sistema delle entità
Il [sistema delle entità](4_Design_di_dettaglio.md#4%204%20Gestione%20delle%20entità), come già illustrato nel [design di dettaglio](4_Design_di_dettaglio.md), è composto da un oggetto repository unito a specifiche estensioni per l'integrazione con altri sistemi.
Alcune note aggiuntive sull'implementazione sono:
- L'utilizzo di eventi per segnalare l'aggiunta e la rimozione delle entità, oltre che per il rimpiazzo delle *View* delle entità.
- La possibilità di fare delle *query* sulla repository grazie ai metodi `entitiesOfModelType[M]`, `entitiesOfViewType[V]` e `entitiesOfMvTypes[M, V]`.
	- Questi metodi sfruttano il sistema dei `ClassTag` di scala, ovvero un sistema che, sfruttando i parametri `using`, permette di mantenere le informazioni di un tipo generico a *run-time*. Si è scelto di limitarsi a questo e non usare i più complessi `TypeTag` perché non necessari allo scopo qui richiesto.
- Tutte le operazioni di modifica della repository principale sono effettuate in modo *deferred* facendo il *wrapping* delle operazioni con il metodo `queueCommand`. Queste operazioni saranno eseguite al termine dello *step* di gioco corrente dal `GameLoop` tramite il metodo `executeQueueCommands`. In questo modo è possibile modificare la repository senza preoccuparsi di impattare l'esecuzione di altre componenti aggiornate successivamente.
- Nelle estensioni per i *Presenter* (`EntityControllerRepository` e `EntityControllerReplacer`), la modifica e l'aggiornamento dei *Presenter* sono eseguiti in modo funzionale, infatti:
	- All'interno dello *step* ogni *Controller* è mappato su un nuovo controller, restituito dal metodo `step` del *Controller* stesso;
	- A seguito del rimpiazzo di una *View* tramite il metodo `editControllers` viene mappato il *Controller* associato al *Model* la cui *View* sta venendo rimpiazzata su un nuovo *Controller*.
- Le *factory* e i *replacer* dei *Controller* sono stati implementati sfruttando il sistema delle *partial application* dei metodi di scala. In particolare, ogni *factory* possiede due gruppi di parametri (più uno eventualmente per i parametri `using`):
	- Il primo per impostare i parametri della *factory*;
	- Il secondo con i parametri richiesti dalla repository.
  In questo modo, durante la registrazione della *factory* viene applicata solo la prima parte del metodo, mentre sarà poi la repository ad eseguire la seconda parte al momento di creazione dell'entità.
## 5.1.2 Contesto di visualizzazione
Una nota aggiuntiva sul sistema dei contesti di visualizzazione è il *mixin* `EntityMvRepositoryContextAware` che viene utilizzato come controllo aggiuntivo sulla coerenza dei contesti usati nell'applicazione.
Infatti, esso obbliga chi lo estende a fornire un contesto tra i parametri `using` con i parametri generici di tipo specificati.
## 5.1.3 Gestione del *tank* e del player
Per gestire i tank è stata creata una gerarchia di *Controller*:
- `TankController`: gestisce una sequenza di *tank* e che va ad aggiornare le loro *View*;
- `TankInputController`: aggiunge la reattività del controller rispetto a degli eventi di input, nello specifico quelli nel *mixin* `TankInputEvents`;
- `EnemyController`: gestisce un *tank* tramite una macchina a stati che reagisce allo stato di gioco. Generalmente usato per i nemici;
- `JFXPlayerTankController`: gestisce un *tank* controllato del giocatore tramite gli eventi di input ricevuti dallo `Stage` di scalafx tramite un `JFXPlayerInputProvider`.
## 5.1.4 Sistema fisico
Il sistema fisico è utilizza un modello molto semplice in cui ogni entità ha un proprio *axis-aligned bounding box* (o *AABB*), ovvero un rettangolo con una posizione e una dimensione che rappresenta lo spazio che l'entità occupa.
Questi `AABB` sono forniti dalle classi che implementano `Collider` che, nel caso delle entità, è fatto tramite il `CollisionBehaviour`, che fornisce anche alcune funzionalità aggiuntive.
Ogni `Collider`, inoltre, appartiene ad un `CollisionLayer` e possiede una maschera con alcuni `CollisionLayer`. Questo permette, durante il controllo dell'*overlap*, di fare dei filtri sui `Collider` che possono effettivamente collidere tra loro.
Il controllo delle collisioni vero e proprio è fatto, invece, tramite il `trait` `PhysicsContainer` che contiene tutti i `Collider` attivi e va a fare il controllo sull'*overlap* di un `AABB` con tutti i `Collider`.
Un istanza di questo `trait` è passata come parametro `given` a chi la necessita, e ne esiste normalmente una sola istanza *singleton* tramite un `object` `PhysicsWorld` che estende `PhysicsContainer`.
## 5.1.5 Gestione del punteggio
Il punteggio è gestito tramite:
- Un'istanza di `PointsContainer` che va a contenere i punti attuali e fornisce un evento per reagire a quando il loro valore cambia;
- Un'istanza di `PointsGiver` che reagisce agli eventi di gioco per aggiornare il punteggio;
- Un *singleton* `PointsBonuses` che fornisce alcuni metodi di utilità per ottenere i punti da assegnare per ogni evento.
## 5.1.6 Lifecycle applicazione
Un'aspetto non citato in precedenza sul ciclo di vita è la gestione dell'avanzamento nei livelli di gioco. Questo è gestito da un `LevelSequencer` che, prendendo in ingresso tutti i livelli da giocare, ne crea una coda e permette di avanzare di livello in livello, mantenendo un riferimento al livello corrente.
## 5.1.7 Sistema Audio
Il sistema audio è stato costruito come un *wrapper* attorno alle funzionalità audio di *scalafx*, fornendo un `trait` base `JFXSoundMixer` che effettua la gestione dei volumi e permette di riprodurre un dato suono su una certa `SoundMixerLane`, i cui valori sono `Music`, `Sound` e `UI`.
Esso è poi ereditato da `JFXPersistentSoundMixer` che permette di salvare e caricare i valori dei volumi, in modo che siano persistenti tra le sessioni di gioco.
Infine, quest'ultimo `trait` è esteso da un `object` `JFXMediaPlayer` che fornisce alcune costanti fisse per i suoni del gioco e un metodo per per-caricare i suoni in memoria, per evitare blocchi durante il gioco alla prima riproduzione di un suono.
# 5.2 Andrea Bianchi
## 5.2.1 Realizzazione e gestione dei proiettili
I proiettili che vengono utilizzati nel gioco, vengono realizzati a partire da un semplice *modello* iniziale definito come  `class Bullet`, contentente solo alcune informazioni minimali. Il fulcro della sua piena realizzazione
si concentra invece all'interno di `TankMultipleShootingBehaviour`, un `trait` ereditabile dai diversi *tank* del gioco per usufruire di una funzione di sparo del proiettile. Si genera così una sequenza di oggetti di `CompleteBullet`,
ovvero dei proiettili studiati per la piena interazione con la mappa di gioco e che ereditano i comportamenti di:
- `PositionBehaviour` per determinarne e gestire la posizione;
- `MovementBehaviour` per controllarli e muoverli nella mappa;
- `DirectionBehaviour` per stabilire la direzione che i proiettili dovranno mantenere fino al loro momento della collisione;
- `CollisionBehaviour` per stabilire i loro parametri di collisione e con quali altri entità possono collidere;
- `DamageableBehaviour` per gestire la loro eliminazione dalla mappa.

I proiettili così realizzati vengono gestiti da un *controller* apposito `BulletController`: questa `class` prende come parametri una coppia *modello-view* `(Bullet, BulletView)` che aggiorna la *view* in base ai movimenti compiuti
dal *modello* bullet preso. Oltre a fare ciò, il `BulletController` osserva se si sono verificate delle collisioni tra il *bullet* e altre entità della mappa, richiamando un evento `overlapping` che nota con quale di queste il *bullet* è andato
a scontrarsi e inviando le appropriate *query* all' `EntityRepository` per *distruggere* il bullet ed effettuare le appropriate modifiche alle restanti entità (eliminazione di un tank, distruzione di un ostacolo...).
## 5.2.2 Caricamento dei Livelli da file
Ogni livello è costituito da due *stringhe* fondamentali: la prima rappresenta il *layout* del livello stesso, mentre la seconda indica la *sequenza* di
nemici che appariranno nel corso del livello. Le stringhe sono state realizzate utilizzando termini chiave rappresentanti i diversi tipi di ostacoli e nemici che si troveranno al loro interno.
Ciascun livello viene salvato come risorsa del programma come un *file .txt*, alla quale vi si accede tramite l'utilizzo di una `class` di caricamento `LevelLoader`. Questa recupera e riorganizza nella maniera
appropriata le stringhe dei file in maniera tale che siano immediatamente utilizzabili dalle altre classi quali `LevelSequencer`.
## 5.2.3 Salvataggio e Lettura di dati in file
Per poter mantenere le informazioni sull'username e sul punteggio più alto raggiunto, è stata creata una classe `JFXSavedDataLoader` apposita. Questa è in grado di *leggere* le informazioni contenute in un file esterno al gioco, rendendole disponibili al `trait SavedDataContainer` e alla sua istanza `SavedDataManager`. Durante l'uso dell'applicazione, lo *score* e lo *username* potrebbero essere modificati: in tale casistica, le nuove informazioni vengono inizialmente salvate all'intero del manager `object` locale, per poi venire a seguito salvate nel file esterno richiamando la funzione apposita sempre fornita da `JFXSavedDataLoader`.
## 5.2.3 Schermata di Game Over
Nel caso di sconfitta, viene richiamata una *View* apposita `JFXGameOverView` che funge da schermata di game over della partita. Questa descrive:
- il livello in cui il giocatore ha perso;
- il punteggio che è stato raggiunto;
- il punteggio più alto corrente;
- due pulsanti: il primo per poter ricominciare la partita, il secondo per poter chiudere l'applicazione.
## 5.2.4 Menù delle opzioni
A partire dal menù iniziale, è possibile accedere ad un secondo menù `JFXOptionsMenu` incentrato su svariate opzioni di gioco per migliorare l'esperienza ludica.
Tra le funzioni offerte da questo, vi sono:
- un'area dove poter inserire il proprio username (il quale verrà immediatamente salvato su file esterno);
- una serie di slider per impostare il volume di `Music`, `Sound` e le dimensioni di `UI`;
- un pulsante per resettare il punteggio massimo raggiunto, "cancellando" i salvataggi;
- un pulsante per tornare al menù iniziale.
Tutto questo è stato realizzato integrando e usufruendo gli strumenti del [sistema delle entità.](5_Implementazione.md#511-sistema-delle-entità)

# 5.3 Stefano Guidi
Ho realizzato le seguenti componenti:
 - sistema dei potenziamenti e dell'applicazione dei loro effetti sulle entità di gioco 
 - AI del movimento dei nemici nella mappa
 - AI del focus shooting dei nemici e Line of Sight (quest'ultimo realizzato in collaborazione)
 - Sistema di generazione dei nemici e potenziamenti sulla mappa

## Sistema dei potenziamenti
Si è cercato di mantenere indipendente la logica del concetto di potenziamento mantenendosi il piu' generici e flessibili possibile:
 - `PowerUp[E]` rappresenta la struttura di base per l'applicazione e inversione dell'effetto su un'entità. E' stato scelta di definirlo come abstract class invece che come trait, ciò per semplificare l'integrazione con trait di middleware come `PowerUpConstrait[E]`.
	 - NOTA: per quanto riguarda i metodi di apply e revert si è scelto di imporre una limitazione sul un tipo generico: `apply[A <: E](entity: A): A`. Ciò permette di mantenere un'invarianza sul sottotipo specifico dell'entità, rendendo piu' semplice la gestione funzionale dei potenziamenti ed evitando ambiguità' di typing troppo generico
- `PowerUpConstraint[E]`: trait di "middleware" che estende `PowerUp[E]`. Esegue un override per l'`apply`   di `PowerUp[E]` ponendo un predicato di tipo `E => Boolean` sull'applicazione di un dato potenziamento su un'entità
- `FuncPowerUp[E]`: classe concreta per i potenziamenti, definisce un powerup come una struttura definita da una funzione di applicazione e inversione `E => E`
- `ContextualFuncPowerUp[C, E]`: ulteriore estensione di `FuncPowerUp[E]`. Permette di fornire un'informazione di contesto supplementare `C` oltre all'entità `E`. E' costituito da una funzione di mapping `(C, E) => E` 

## Sistema di Binding dei potenziamenti sulle entità
Per semplificare l'applicazione dei potenziamenti si è scelto di utilizzare una serie di meccanismi per semplificarne l'utilizzo e la gestione delle entità affette da essi:
 - `PowerUpChain[E]`: estensione del `PowerUp[E]`,  prende in ingresso una sequenza di potenziamenti e permette di applicarli in maniera unitaria ad un'entità. E' possibile inoltre eseguire il `chain` ed `unchain` di ulteriori potenziamenti da aggiungere alla sequenza, tutto ciò mantenendosi completamente immutabile
 - `DualBinder[E]`:  Trait necessario a mantenere lo stato delle entità consistente con i loro relativi aggiornamenti in seguito all'applicazione dei potenziamenti si è deciso di realizzare un sistema a doppio riferimento, permettendo di eseguire il `bind` e `unbind` delle entità, nello specifico tale riferimento è stato realizzato nel seguente modo:
	 - `EntityBinding`: case class, mantiene un doppio riferimento ad ogni entità tramite un `supplier: () => E ` per ottenere lo stato dell'entità aggiornata e un `consumer: E => Unit ` per aggiornare il suo stato corrente
	Questa soluzione permette di gestire in maniera piu' semplice la gestione delle entità bindate senza ulteriori complicazioni e problemi di consistenza
Entrambi i meccanismi sono stati integrati in una componente `PowerUpChainBinder[E]` che fornisce entrambe le funzionalità  mantenendosi sempre generico rispetto al tipo di entità

## AI dei nemici
Per incapsulare al meglio la logica di AI dei nemici si è scelto di strutturarle come automi a stati finiti. Per quanto tale approccio sia molto utilizzato in contesto AI risulta essere spesso una scelta poco idiomatica in ambiti funzionali in quanto ciò porta a lavorare spesso in maniera mutabile modificando lo stato degli oggetti.
Si è scelto pertanto di adottare un approccio di tipo monadico sfruttando lo `State Monad pattern`. L'dea è quella di mantenere una costrutto monadico che permetta,dichiarativamente, di applicare una modifica sullo stato interno di un oggetto ma ritornare il risultato di una computazione. Ciò permette di incapsulare side-effects in maniera funzionale, permettendo di tracciare piu' semplicemente modifiche allo stato. Alcuni dettagli:
- `State[S, A]`: case class state datatype, rappresenta una famiglia di costrutti monadici ed è computabile come una funzione `S => (A, S)` (prende uno stato e ritorna il nuovo stato modificato e un valore di ritorno della computazione)
- `StateModifier[S[_], E]`: trait che fornisce le funzionalità necessarie ad eseguire modifiche e sullo stato, è parametrizzato da un `S[_]` monadico e un tipo `E` che definisca l'inner type da utilizzare
- `StateMachine[S[_], E, A]`: trait che permette di manipolare lo stato interno di un tipo monadico sulla base di valori di transizione `A` ,  definisce un unico metodo di transizione `transition(value: A): S[Unit]` che si limita ad applicare un side effect funzionale di modifica dello stato
Problematica: adattare `State` ai 2 precedenti trait monadici non è possibile di base (E' necessario infatti avere a disposizione un 1-kinded type `S[_]`), inoltre lo `State` datatype definisce una famiglia di 1-kinded type che possono essere trattati in maniera monadica (tutti quelli ottenibili fissando un constraint su uno dei due tipi). Fortunatamente in questo caso si è interessati a fissare il type class dello stato e mantenere un grado di libertà sul tipo di ritorno, sostanzialmente ci sono 2 possibilità per fare ciò:
 - `type EnemyState[A] = State[FOO, A]`: il problema è che in questo modo è necessario definire un nuovo tipo per ogni nuovo stato che si vuole utilizzare
- `type F[A] = State[E,A]})#F`: utilizzato il type projection di Scala è possibile definire type alias in-place. Per rendere trasparente l'utilizzo della type projection è stata realizzata una `AbstractStateMachine[S, A]`
Inizialmente si era adottato il primo approccio, ma durante la sviluppo si è passati al secondo.
Partendo da ciò sono stati realizzati 2 automi:
 - `AiMovementStateMachine`: è una `AbstractStateMachine[MovementEntity, DirectionMovePolicy]`. Computa la prossima direzione lungo la quale spostarsi per il nemico
 - `AiFocusShootingStateMachine`: è un `AbstractStateMachine[ShootingEntity, FocusPolicy]`. Fa in modo che i nemici rimangano concentrati su obbiettivi target (nel nostro case sulla base del giocatore)
NOTA: per mantenere uno stile dichiarativo, si è scelto di utilizzare come tipi dello stato (come `MovementEntity` e `ShootingEntity`), non dei tipi concreti veri e propri ma type alias che definiscano mixin di constraint sulle caratteristiche che debba avere un oggetto per essere elaborato dalla State Machine 

## Raccolta dei potenziamenti
Si è voluta denotare una separazione tra il concetto di `PowerUp` in termini di costrutto che altera le caratteristiche di un'entità e potenziamento come oggetto presente nel livello di gioco che può essere raccolto `PickablePowerUp`. Quest'ultimo è un potenziamente ma esteso con i mixin `PositionBehaviour` e `CollisionBehaviour`, permettendo quindi di aver assegnnabile una posizione nel modo e di poter entrare in collisione con il giocatore per la raccolta.  


## Line of sight
Questa funzionalità fornisce la possibilità (solo per i nemici) di di visualizzare le collisioni lungo la linea perpendicolari dei nemici per individuare obbiettivi su quali concentrarsi.
Ho realizzato solo la porzione di frontend di questa feature(tramite un trait mixin `LineOfSigt`), ma il backend del suo funzionamento è stata fornito dalle estensioni  `RayCast` per i `PhysicsContainer` sviluppato da Arduini.
In sostanza `LineOfSight` permette ad un'entità di filtrare determinati tipi di `Collider` (in questo caso la base del giocatore) lungo le direzioni cardinali rispetto alla sua posizione

## Generazione dei nemici 
La generazione dei nemici permette di creare iterativamente i nemici all'interno di un livello tramite una stringa di valori che ne definisce la tipologia (e.g. sequenza di basic tank, armor tank e di nuovo basic tank: "BAB"). 
Tramite un object `EnemyFactory` che fornisce:
 - la possibilità di generare nemici potenziati ogni `eachCharged` nemici generati (tale valore è stato mantenuto di default a quello del gioco originale, ossia 4)
 - Oltre a generare  i nemici relativamente alla loro tipologia, viene loro assegnata una posizione tramite un `PositionProvider` sulla base di una determinata `PositionStrategy` (il default è stato assegnato come generazione di posizioni random nella porzione superiore del livello, come da gioco originale)


