Implementazione (per ogni studente, una sotto-sezione descrittiva di cosa fatto/co-fatto e con chi, e descrizione di aspetti implementativi importanti non già presenti nel design, come ad esempio relativamente all'uso di meccanismi avanzati di Scala)
# 5.1 Alberto Arduini
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
- `TankController`: gestisce una *sequenza* di *tank* e che va ad aggiornare le loro *View*;
- `TankInputController`: aggiunge la reattività del controller rispetto a degli eventi di input, nello specifico quelli nel *mixin* `TankInputEvents`;
- `EnemyController`: gestisce un *tank* tramite una macchina a stati che reagisce allo stato di gioco. Generalmente usato per i nemici;
- `JFXPlayerTankController`: gestisce un *tank* controllato del giocatore tramite gli eventi di input ricevuti dallo `Stage` di *scalafx* tramite un `JFXPlayerInputProvider`.
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
# 5.3 Stefano Guidi