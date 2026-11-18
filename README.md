# Six qui prend

## Présentation

Adaptation en Java, en mode console, du jeu de cartes **6 qui prend !** (Wolfgang Kramer, 1994). À chaque tour, les joueurs posent une carte en même temps. Les cartes viennent compléter quatre séries, et celui qui pose la 6e carte d'une série la ramasse avec toutes ses têtes de bœuf. La partie s'arrête dès qu'un joueur dépasse 66 têtes, et c'est le joueur qui en a le moins qui gagne.

Ce projet a été réalisé dans le cadre du cours de programmation orientée objet en Java à l'esaip. La **V1** correspond à la version rendue pour le TP. La **V2** ajoute quatre niveaux de bots, un benchmark pour les comparer et une architecture extensible pour les stratégies.

## Prérequis

- Un JDK Java (Java 17 ou plus récent recommandé).
- Un terminal ouvert à la racine du projet.

## Lancer le jeu

```bash
javac Application.java BenchmarkBots.java modele/*.java test/*.java
java Application
```

Le jeu se joue au clavier. Il faut entrer les numéros demandés (carte à jouer, série à ramasser) quand le programme les propose.

## Lancer les tests et le benchmark

```bash
java test.TestJeu        # règles du jeu et comportement des bots (58 tests)
java BenchmarkBots       # tournoi à 4 bots + duels (1000 parties chacun)
```

Chaque classe du modèle a aussi sa propre classe de test, exécutable de la même façon : `test.TestCarte`, `test.TestSerie`, `test.TestMain`, `test.TestPile`, `test.TestPioche` et `test.TestJoueur`.

## Les bots

| Bot | Stratégie |
| --- | --- |
| ALEATOIRE | Joue une carte au hasard et ramasse une série au hasard. Il sert de point de comparaison. |
| FACILE | Joue toujours sa plus petite carte. Quand il doit ramasser, il choisit la série qui contient le moins de têtes. |
| MOYEN | Joue la carte la plus proche de la fin d'une série, en écartant les cartes dangereuses (trop petites, ou qui seraient la 6e carte). Si toutes ses cartes sont dangereuses, il joue la plus petite. |
| DIFFICILE | Donne à chaque carte une **note de risque** : écart avec la série ÷ places restantes dans la série. Une carte qui suit de près une série presque vide est sûre, alors qu'une carte éloignée d'une série presque pleine risque d'être devancée. Il joue la carte la moins risquée. Si toutes ses cartes sont dangereuses, il joue celle qui lui fera ramasser le moins de têtes. |

## Résultats du benchmark

Les chiffres varient légèrement d'une exécution à l'autre (de quelques pour cent), mais le classement reste le même.

### Tournoi à 4 bots (1000 parties)

| Bot | Victoires | Score moyen (têtes) |
| --- | --- | --- |
| DIFFICILE | 529 | 27,3 |
| MOYEN | 409 | 31,2 |
| FACILE | 28 | 59,6 |
| ALEATOIRE | 10 | 71,2 |

Il y a eu 24 parties terminées sur une égalité pour la première place. Elles ne sont attribuées à aucun bot.

### Duels (1000 parties chacun)

| Duel | Victoires | Matchs nuls |
| --- | --- | --- |
| ALEATOIRE vs FACILE | 307 - 684 | 9 |
| ALEATOIRE vs MOYEN | 14 - 985 | 1 |
| ALEATOIRE vs DIFFICILE | 17 - 983 | 0 |
| FACILE vs MOYEN | 51 - 947 | 2 |
| FACILE vs DIFFICILE | 28 - 969 | 3 |
| MOYEN vs DIFFICILE | 457 - 542 | 1 |

**Analyse :**

- **La hiérarchie est respectée dans tous les duels** : chaque niveau bat tous les niveaux inférieurs.
- **Le plus grand écart sépare FACILE et MOYEN.** Éviter les cartes dangereuses compte beaucoup plus que jouer petit.
- **Le duel MOYEN contre DIFFICILE est le plus serré.** À deux joueurs, les séries se remplissent moins vite, donc les 6e cartes sont plus rares. Or c'est justement là que la note de risque de DIFFICILE fait la différence. À 4 joueurs, l'écart est plus net : 12 points (53 % contre 41 %), contre 8 points en duel (54 % contre 46 %).

## Architecture

```text
sixQuiPrend/
├── Application.java      interface console : affichage et saisie du joueur humain
├── BenchmarkBots.java    tournoi automatique entre bots
├── modele/               les règles du jeu, sans aucun affichage
│   ├── Jeu.java              déroulement d'une manche et d'une partie
│   ├── Carte, Serie, Main, Pile, Pioche, Joueur, CarteJouee
│   ├── TypeBot.java          les 4 niveaux de bots (enum)
│   ├── StrategieBot.java     interface commune à tous les bots
│   └── BotAleatoire, BotFacile, BotMoyen, BotDifficile
└── test/                 tests unitaires (une classe de test par classe du modèle)
```

**Séparation des règles et de l'affichage.** Toutes les règles se trouvent dans `modele/` (principalement `Jeu`), et `Application` ne fait qu'afficher et lire les saisies. C'est ce qui permet au benchmark de jouer des milliers de parties sans console, et ce qui permettra plus tard de brancher une interface graphique sur le même modèle.

**Les bots et le polymorphisme.** Chaque bot est une classe qui implémente l'interface `StrategieBot`, avec deux méthodes : `choisirCarte` et `choisirSerie`. `Jeu` ne connaît pas les différents bots : il demande simplement au joueur sa stratégie et l'appelle. Pour **ajouter un nouveau bot**, il suffit de :

1. créer une classe `BotXxx implements StrategieBot` ;
2. ajouter la valeur dans `TypeBot` et une ligne dans sa méthode `creerStrategie()`.
Il n'y a aucune modification à faire dans `Jeu`. Le benchmark prend automatiquement en compte le nouveau bot dans les duels.

## Versions

- **v1** (tag git `v1`) : version rendue pour le TP. Jeu complet en console contre des bots aléatoires.
- **v2** (en cours) :
  - les règles sont extraites dans `Jeu` et testées ;
  - quatre niveaux de bots ;
  - le benchmark (tournoi et duels) ;
  - une stratégie par classe grâce à l'interface `StrategieBot`.
  - **À venir :** une interface graphique en JavaFX, puis le multijoueur en ligne.
Le jeu se joue au clavier. Il faut entrer les numéros demandés (carte à jouer, série à ramasser) quand le programme les propose.
