Per lo sviluppo è stato utilizzata la tecnica SCRUM, dove l'intero processo è stato pensato tenendo in considerazione le differenti possibilità che ogni membro del team aveva in termini di tempo e di fasce orarie di lavoro.
# 1.1 Divisione in itinere dei task
La gestione delle task è stata fatta durante la riunione dello sprint planning, creando epic e user story nel backlog e assegnandoli ai singoli componenti del team.
Successivamente, ogni componente estrapolava dalle proprie user story una o più specifiche sprint task da svolgere, che venivano poi aggiunte allo sprint backlog corrente.
# 1.2 Meeting/interazioni pianificate
Avendo ogni membro del team disponibilità diverse di orario, è stato deciso di svolgere un meeting in presenza il lunedì ogni due settimane per effettuare:
- La sprint review, per lo sprint corrente;
- Lo sprint planning, per il prossimo sprint.
Di conseguenza con ciò, anche gli sprint avevano durata bi-settimanale, con piccole riunioni e confronti da remoto durante lo sprint anche con solo parti del team per verificare l'andamento e per una eventuale ri-organizzazione degli assegnamenti di alcune sprint task.
# 1.3 Revisione in itinere dei task
Per verificare la completezza e la correttezza dei task sono stati adottati (quando possibile):
- Unit test, per la verifica delle singole sprint task e funzionalità;
- Acceptance test, per la verifica delle user story e del comportamento generale dei sistemi dell'applicazione;
Questi ultimi sono anche stati scelti come *definition of done*, ovvero come metodo di decisione se una certa user story era da considerare come completata.
# 1.4 Strumenti di Test, Build e CI
## 1.4.1 Test
Per scrivere i test abbiamo utilizzato scalatest, sia per gli unit test sia per gli acceptance test. Questo ci ha permesso, sfruttando le funzionalità scala e scalatest avanzate, di scrivere dei test in in linguaggio quasi naturale, che fosse semplice capire anche per chi non avesse scritto il test.
## 1.4.2 Build
Il build tool scelto è sbt, per la sua alta integrazione con Scala e per la sua natura multipiattaforma, in quando avevamo necessità di lavorare su più sistemi operativi.
## 1.4.3 CI/CD
Utilizzando GitHub come DVCS, è risultato naturale scegliere le GitHub Actions come strumento di CI/CD, non solo per il fatto che nel piano base di GitHub vengono forniti un numero di minuti di esecuzione a noi sufficenti (per evitare comunque di terminare il tempo di esecuzione disponibile abbiamo applicato le action solo sulla main branch), ma anche per la presenza di azioni predefinite per progetti in Scala con sbt, che richiedono una minima configurazione per poter essere operativi.
Sfruttando queste azioni avevamo non solo l'esecuzione dei test ad ogni commit, ma anche una verifica su aggiornamenti o vulnerabilità delle dipendenze del progetto tramite la funzionalità dependabot di GitHub.