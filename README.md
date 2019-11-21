# projet_coo

### Liste utilisateurs
La base de données du serveur contient une liste de tous les utilisateurs avec les champs suivant :
- ID
- Pseudo
- Actif
- Adresse IP

### Liste historiques
Chaque utilisateur contient une liste d'historiques en local.\
Cette liste d'historiques est composée des champs :
- ID d'un utilisateur avec qui on a communiqué
- L'historique de messages en lui même\

### Initialisation application
#### Réseau local
Au lancement de l'application on envoie un message en broadcast à tous les utilisateurs qui sont sur notre
réseau pour leur notifier que l'on vient de se connecter. Chacun des utilisateurs va nous répondre en nous envoyant
leur information, cela nous permettra de remplir notre liste des utilisateurs.\
Il nous est ensuite demandé de rentrer un pseudo. Avec la liste que nous venons de remplir nous pouvons savoir si
le pseudo choisi est déjà pris, si c'est le cas alors il faut en saisir un autre sinon le pseudo est accepté et on
envoie un message en broadcast pour notifier tous les utilisateurs de notre pseudo.

#### Réseau distant
Pour un réseau distant lorsque l'on se connecte on en informe le serveur distant. Si l'utilisateur n'est pas déjà
répertorié dans la liste des utilisateurs alors il créée un utilisateur supplémentaire. Le serveur nous envoie ensuite
la liste des utilisateurs actifs. Il nous est ensuite demandé de rentrer un pseudo. Avec la liste que nous venons de
remplir nous pouvons savoir si le pseudo choisi est déjà pris, si c'est le cas alors il faut en saisir un autre sinon on
modifie le champs "pseudo" de l'utilisateur dans la BDD du serveur avec le pseudo saisi.

### Changer pseudo
Même principe que dans Initilisation application

### Fermeture application
#### Réseau local
Lorsque l'on ferme l'application on envoie un message en broadcast à tous les utilisateurs du réseau local
pour leur informer que l'on est inactif. De cette façon ils savent qu'ils ne peuvent plus communiquer avec
nous.

#### Réseau distant
Pour un réseau distant on informe le serveur de notre déconnexion et nous mets inactif dans la BDD.

### Clavardage
Un utilisateur peut initier une session de clavardage avec un autre utilisateur.
Il établit la connexion puis il va chercher dans sa liste d'historiques s'il a un historique de conversation
avec l'utilisateur avec qui il veut parler.\
Il peut ensuite envoyer des messages ou bien fermer la session de clavardage. Lors de la fermeture de la session les
utilisateurs vont regarder s'ils ont un historique avec l'autre. Si c'est le cas alors ils ajoutent la conversation
qu'ils viennent d'avoir à la suite de l'historique sinon ils crééent un historique pour l'utilisateur avec qui ils
viennent de parler.

### Rafraichisseur
Il sert pour un réseau distant. Il permet de rafraichir la liste des utilisateurs visibles par l'agent. Toutes
les 5 secondes il envoie à l'agent le pseudo et si les utilisateurs sont actifs ou non.

### Suppositions
Utilisateur sur un seul poste, donc historique stocké sur le poste.
Pour récupérer l'historique qui est stocké sur la machine on associe les historiques avec les ids des utilisateurs
avec qui on a parlé Id = adresse mac de la machine de l'utilisateur avec qui on a parlé