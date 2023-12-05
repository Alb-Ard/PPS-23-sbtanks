# 4.1 Eventi
Un aspetto molto pervasivo nell'applicazione è la gestione della reattività ad azioni e modifiche di dati.
Per fornire un sistema facile da utilizzare e flessibile è stato implementato un tipo `EventSource[A]`, ispirato al sistema di eventi di C#.
Esso modella un evento con un certo tipo di parametro sul quale è possibile registrarsi o deregistrarsi tramite gli operatori `+=` e `-=` come ascoltatori, e che può essere invocato (generalmente fatto dall'oggetto che li espone).
# 4.2 Contesto di visualizzazione
## 4.2.1 Funzionamento
Per permettere la generalizzazione della struttura dell'interfaccia utente e un eventuale cambio di layout a *runtime* il più semplice possibile, è stato creato un sistema di contesti di visualizzazione per i *Model-View*. Questo sistema:
1. A livello base, è completamente astratto dal tipo di libreria grafica utilizzata;
2. Permette di creare dei *preset* di layout dell'interfaccia tramite degli oggetti `ContextInitializer`;
3. Permette ad ogni *View* di controllare la propria presenza nell'interfaccia impostando in essa la zona di layout in cui deve essere visibile tramite uno *slot*, senza la necessità di avere un oggetto di livello superiore che la crei e la aggiunga al layout manualmente.
Nello specifico, il sistema è composto da una classe principale `EntityMvRepositoryContext`, che contiene una mappa da *view slot* a *view container*, e un metodo `switch` per cambiare il contesto tramite un `EntityMvRepositoryContextInitializer`.
## 4.2.1 Utilizzo
Nel nostro caso, abbiamo due contesti:
- Il contesto *view*, che possiede un singolo slot per mostrare un menù all'utente;
- Il contesto *level*, che possiede il layout di gioco, ovvero uno slot centrale per mostrare il campo di gioco, uno sulla destra per la barra laterale con le informazioni della partita, e uno di *overlay* che permette di sovrapporre degli elementi a tutto il resto (utilizzato per il menù di pausa).
Inoltre, per renderne l'utilizzo più semplice, è stato sfruttato il sistema dei `given`/`using` di Scala, grazie al quale abbiamo impostato un'istanza del contesto come `given`, semplificando la creazione degli oggetti che lo utilizzano.
# 4.3 Ciclo di vita dell'applicazione
```plantuml
@startuml
Main -> EntityMvContext ** : new
Main -> EntityMvContext : switch

== Game starts ==

JFXMainMenu -> Main : startRequested

create JFXBootstrapper
Main -> JFXBootstrapper ++ : new
JFXBootstrapper -> GameLoop ** : new
return

Main -> JFXBootstrapper : startGame
JFXBootstrapper -> EntityMvContext : switch

== Game ends ==

JFXBootstrapper -> Main : gameEnded
Main -> JFXBootstrapper !!
JFXBootstrapper -> GameLoop !!
Main -> EntityMvContext : switch
@enduml
```
## 4.3.1 *Bootstrapper*
L'inizializzazione della partita è incapsulata da un oggetto `JFXGameBootstrapper` che va ad inizializzare le componenti richieste e che fornisce un metodo `startGame()` per passare al contesto di gioco e iniziare la partita.
Questo oggetto, inoltre, fornisce gli eventi `gameEnded` e `gameRestarted`, che vengono utilizzati da chi crea il *bootstrapper* (e quindi da chi avvia il gioco) per tornare al menù principale o resettare la partita.
## 4.3.2 GameLoop
Durante il gioco, l'avanzamento e la gestione del tempo è effettuata da un oggetto `GameLoop` che va ad effettuare ad ogni *frame* di gioco (nel nostro caso tramite un `AnimationTimer`)  uno *step* sulla [*repository* delle entità](4_Design_di_dettaglio.md#3%202%202%20Repository%20delle%20entità).
Sarà poi la *repository* stessa ad effettuare lo *step* sui singoli componenti dinamici del gioco, ovvero i *Presenter* nel nostro caso.
Tutto questo è gestito tramite una semplice interfaccia `Steppable` che richiede l'implementazione di un metodo `step(delta: Double): this.type`, al cui interno verrà eseguita la logica di gioco.
## 4.3.3 Gestione dei livelli

## 4.3.4 Pausa del gioco
La pausa è stata gestita tramite un *mixin* `Pausable` che fornisce la funzionalità di poter impostare un oggetto come in-pausa in modo osservabile.
Questo è stato implementato all'interno dei controller di `Tank` e `Bullet` (in quanto sono le due entità dinamiche dell'applicazione) per interromperne il normale funzionamento mentre il gioco è in pausa.
## 4.3.5 Gameover
Quando il giocatore esaurisce le vite, il gioco viene terminato e si passa ad un contesto in cui è mostrata una interfaccia di *gameover*, tramite la quale l'utente può visionare il proprio punteggio, tornare al menù o iniziare una nuova partita.
# 4.4 Gestione delle entità
Nell'applicazione, i *Model* delle entità sono definiti da:
1. Un tipo specifico per l'entità che rappresentano, che ne contiene le proprietà specifiche (ad esempio, le classi `Tank`, `Bullet`, `LevelObstacle`);
2. Un certo numero di *Behaviour* aggiuntivi che forniscono alle entità le informazioni e le operazioni necessarie per implementare un dato comportamento.
## 4.4.1 Composizione delle entità
Sfruttando il meccanismo dei *mixin* di Scala, i *Behaviour* sono stati implementati come `trait`, ognuno dei quali fornisce una funzionalità molto specifica che si può basare su altri *Behaviour*.
Inoltre, utilizzando i *self-type*, è stato possibile fornire un meccanismo di dipendenze tra essi senza dover però utilizzare l'ereditarietà.
L'elenco dei *Behaviour* disponibili è il seguente:
- `PositionBehaviour`: Fornisce una posizione modificabile e osservabile `(x, y)`;
- `DirectionBehaviour`: Fornisce una direzione modificabile e osservabile `(x, y)`, oltre che l'ultima direzione valida (non-zero);
- `CollisionBehaviour`: Estende `Collider` e fornisce reattività e gestione delle collisioni con altre entità;
- `MovementBehaviour`: Fornisce un metodo per il movimento dell'entità in base ad un certo spostamento;
- `ConstrainedMovementBehaviour`: Aggiunge il controllo delle collisioni al `MovementBehaviour`;
- `MultipleTankShootingBehaviour`: Utilizzato dalle entità `Tank`, permette di istanziare proiettili in base alla propria posizione e direzione;
- `DestroyableBehaviour`: Permette all'entità di essere danneggiata e/o distrutta on modo osservabile;
Lo schema seguente mostra una parziale gerarchia dei *Behaviour* e dell'utilizzo dei *self-type* oltre che della normale ereditarietà:
```plantuml
@startuml
interface PositionBehaviour <<mixin>> {
+{field}positionChanged: EventSource[(Double, Double)]
..
-{field}position: (Double, Double) 
__
+{method}positionX: Double
+{method}positionY: Double
+setPosition(x: Double, y: Double): this.type
}

interface CollisionBehaviour <<mixin>> {
+overlapping: EventSource[Seq[Collider]]
__
+{method}overlapsAnything: Boolean
+{method}overlappedColliders: Seq[Collider]
+{method}boundingBox: AABB
}
PositionBehaviour <|.. CollisionBehaviour

interface MovementBehaviour <<mixin>> {
+moveRelative(x: Double, y: Double): this.type
}
PositionBehaviour <|.. MovementBehaviour

interface ConstrainedMovementBehaviour <<mixin>> {
+moveRelative(x: Double, y: Double): this.type
}
MovementBehaviour <|-- ConstrainedMovementBehaviour
CollisionBehaviour <|.. ConstrainedMovementBehaviour
@enduml
```
In questo modo, quando le entità vengono costruite creando istanze degli oggetti dell'entità specifica, i *Behaviour* vengono aggiunti in modo modulare e, nel caso vi fossero dipendenze non rispettate tra loro, l'errore è segnalato già a *compile-time* dal compilatore.
Nello schema seguente è mostrato un esempio (parziale) di come possono essere composte due entità diverse:
```plantuml
@startuml
package Behaviours {
interface PositionBehaviour <<mixin>>
interface DirectionBehaviour <<mixin>>

interface MovementBehaviour <<mixin>>
PositionBehaviour <|.. MovementBehaviour
}

package entities.obstacles {
class LevelObstacle {
+obstacleType: LevelObstacleType
+images: Seq[Image]
}

entity Obstacle
LevelObstacle <|-- Obstacle
PositionBehaviour <|-- Obstacle
}

package entities.bullet {
class Bullet {
+speed: Double
+isPlayerBullet: Boolean
}
entity CompleteBullet
Bullet <|-- CompleteBullet
MovementBehaviour <|-- CompleteBullet
DirectionBehaviour <|-- CompleteBullet
PositionBehaviour <|-- CompleteBullet
}
@enduml
```
## 4.4.2 Repository delle entità
Tutte le coppie *Model-View* delle entità sono mantenute in una repository detta `EntityMvRepositoryContainer` dove ogni *Model* è considerata l'istanza in gioco dell'entità.
Per ogni *Model* può esistere una *View* associata corrispondente che, durante la vita dell'entità, può essere rimpiazzata se necessario senza modificare il *Model*.
### 4.4.2.1 Gestione dei Presenter
Ogni volta che un *Model* o una coppia *Model-View* viene aggiunta alla repository, la creazione del loro *Controller*/*Presenter* è affidata ad una estensione della repository, la `EntityControllerRepository`.
Questa permette di:
- Registrare dei metodi *factory* di *Presenter*, assieme ad un dati predicato che indicherà per quali *Model* eseguirlo;
- Rimuovere il *Presenter* associato ad un dato *Model* quando quest'ultimo viene eliminato dalla *repository*.
Inoltre, una ulteriore estensione `EntityControllerReplacer`, permette in modo similare di registrare dei *replacer* allo stesso modo delle *factory* per rimpiazzare un *Presenter* di un *Model* quando viene rimpiazzata la *View* ad esso associata.
### 4.4.2.2 Altre estensioni della repository
- `EntityViewAutoManager`: Permette di aggiungere e rimuovere in automatico le *View* in un dato *view slot* del [contesto di visualizzazione](3_Architettura.md#3%202%201%20Contesto%20di%20visualizzazione) corrente;
- `EntityColliderAutoManager`: Permette di registrare e deregistrare in automatico i *Model* fisici, ovvero quelli che derivano da `Collider`, sul mondo fisico (`PhysicsWorld`);
- `DestroyableEntityAutoManager`: Permette di rimuovere dalla repository le entità i cui *Model* derivano da `DestroyableBehaviour` quando viene invocato l'evento `destroyed` di esse;
- `EntityRepositoryPausableAdapter`: Rende la repository un oggetto `Pausable` nel quale, quando viene cambiato lo stato di pausa, esso viene impostato anche su tutti i *Presenter* che derivano da `Pausable`.
- `EntityRepositoryTagger`: Permette di assegnare un *tag* ad ogni entità, ovvero un valore che può essere utilizzato per raggrupparle. Nel nostro caso è stato utilizzato per marcare tutti i *Model* che sono collegati ad una singola entità (come il caso dell'ostacolo `BrickWall` che, per ogni entità, crea $16$ coppie *Model*-*View* per ogni frammento).
```plantuml
@startuml
together {
class MvRepository <M, V> {
+modelViewAdded: EventSource[(M, Option[V])]
+modelViewReplaced: EventSource[(M, Option[V], Option[V])]
+modelViewRemoved: EventSource[(M, Option[V])]
..
-modelRepository: Seq[M]
-modelViewReferences: Map[M, V]
__
+addModelView(model: M, view: Option[V]): this.type
+replaceModelView(model: M, view: Option[V]): this.type
+removeModelView(model: M): this.type
}
note top of MvRepository : Type names have been\nreduced for clarity
}

together {
interface ControllerRepository <M, V, C, Ctx extends MvContext> <<mixin>> {
-controllers: Map[M, C]
-{field}factories: Seq[Predicate, Factory]
+registerFactory(predicate: Predicate, factory: Factory): this.type
#editControllers((c: C, m: Option[M]) => C): this.type
}
MvRepository <|.. ControllerRepository

interface ControllerReplacer <M, V, C, Ctx extends MvContext> <<mixin>> {
-{field}replacers: Seq[Predicate, Replacer]
+registerReplacer(predicate: Predicate, replacer: Replacer): this.type
}
MvRepository <|.. ControllerReplacer
}

interface ViewAutoManager <Ctx extends MvContext> <<mixin>> {
-context: Ctx
}
MvRepository <|.. ViewAutoManager
@enduml
```

# 4.5 Gestione powerUp sulle entità 
Il sistema di gestione ed applicazione dei powerup di gioco è stata reso generico in termini del tipo di entità sul quale vengono applicati.
- `PowerUp`: Permette  di applicare ed invertire l'effetto di un powerUp su di una entità, una volta fatto ciò è ritornata la nuova entità modificata
- `PowerUpChain`: Fornisce le funzionalità necessarie a concatenare un numero variabile di `PowerUp` , si mantiene identico nell'uso in quanto rimane anch'esso un `PowerUp`
c Dual-Binding delle entità
Per fornire un sistema che potesse mantenere traccia e aggiornare in maniera reattiva le entità affette da powerUp si è scelto di realizzare un doppio binding tra le queste e il sistema in questione
- `DualBinder`: Permette di legare e slegare un doppio riferimento ad un'entità
- `EntityBinding`: Case class mantenuta all'interno del  `DualBinder`.  Rappresenta il doppio riferimento ad un'entità, è costituita da 2 componenti:
	- `supplier`: Permette di ottenere lo stato corrente dell'entità. Ciò permette di lavorare direttamente con lo stato attuale delle entità a prescindere da cambiamenti successivi alla fase di binding
	- `consumer`: Permette di aggiornare lo stato di un'entità mantenendo consistente il suo uso dall'esterno
```plantuml
scale 300 width

@startuml
Entity -> DualBinder: supply
Entity <- DualBinder: consume
@enduml

```

Questo approccio fornisce diversi vantaggi:
 - è possibile lavorare sulle entità semplificando il tracciamento dei suoi cambiamenti di stato da parte di altri sistemi
 - Ogni modifica sull'entità è riflessa immediatamente, se alcun bisogno di lavorare su sue  copie o stati 
## 4.5.2 Descrizione generale del sistema
Entrambe le componenti precedentemente descritte:
 - `PowerUpChain`
 - `DualBinder`

Sono state integrate in una nuova componente `PowerUpChainBinder`, che ne integra le funzionalità, permettendo di:
 - registrare le entità sulle quali verranno applicati i powerup
 - concatenare powerup al sistema facendo in modo che questi vengano eseguiti

Alcune note:
 - il `PowerUpChainBinder` applica i powerup solo su entità attualmente registrate, facendo in modo che il sistema si mantenga dinamico e flessibile anche a seguito di multiple chiamate di `bind` e `unbind`
 - la logica di applicazione rimane vincolata al solo powerup, nessun altro componente possiede informazioni sul loro risultato effettivo

```plantuml

@startuml

abstract class PowerUp < E > {
	+ apply[A <: E](entity: A): A
	+ revert [A <: E](entity: A): A
}

class PowerUpChain < E > {
  - powerUps: Seq[PowerUp[E]]
  + apply(powerUps: PowerUp[E]*): PowerUpChain[E]
  + chain(next: PowerUp[E]): PowerUpChain[E]
  + unchain(last: PowerUp[E]): PowerUpChain[E]
}

interface DualBinder < E > {
  # entities: Seq[EntityBinding]
  + bind(entity: E): Unit
  + unbind(entity: E): Unit
}

class EntityBinding < E > {
  - {field} supplier: () => E
  - {field} consumer: E => Unit
}

class PowerUpChainBinder < E > {
  - powerUpBindings: Map[PowerUp[E], Seq[EntityBinding]]
  + getPowerUps(): Seq[PowerUp[E]]
  + chain(next: PowerUp[E]): this.type
  + unchain(last: PowerUp[E]): this.type
}

PowerUp <-- PowerUpChainBinder: use
PowerUp <|-- PowerUpChain
PowerUpChainBinder <|-- PowerUpChain
PowerUpChainBinder <|.. DualBinder
DualBinder o-- EntityBinding

@enduml


```