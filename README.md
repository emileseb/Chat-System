# projet_coo

### Liste utilisateurs
La base de donn�es du serveur contient une liste de tous les utilisateurs avec les champs suivant :
- ID
- Pseudo
- Actif
- Adresse IP

### Liste historiques
Chaque utilisateur contient une liste d'historiques en local.\
Cette liste d'historiques est compos�e des champs :
- ID d'un utilisateur avec qui on a communiqu�
- L'historique de messages en lui m�me\

### Initialisation application
#### R�seau local
Au lancement de l'application on envoie un message en broadcast � tous les utilisateurs qui sont sur notre
r�seau pour leur notifier que l'on vient de se connecter. Chacun des utilisateurs va nous r�pondre en nous envoyant
leur information, cela nous permettra de remplir notre liste des utilisateurs.\
Il nous est ensuite demand� de rentrer un pseudo. Avec la liste que nous venons de remplir nous pouvons savoir si
le pseudo choisi est d�j� pris, si c'est le cas alors il faut en saisir un autre sinon le pseudo est accept� et on
envoie un message en broadcast pour notifier tous les utilisateurs de notre pseudo.

#### R�seau distant
Pour un r�seau distant lorsque l'on se connecte on en informe le serveur distant. Si l'utilisateur n'est pas d�j�
r�pertori� dans la liste des utilisateurs alors il cr��e un utilisateur suppl�mentaire. Le serveur nous envoie ensuite
la liste des utilisateurs actifs. Il nous est ensuite demand� de rentrer un pseudo. Avec la liste que nous venons de
remplir nous pouvons savoir si le pseudo choisi est d�j� pris, si c'est le cas alors il faut en saisir un autre sinon on
modifie le champs "pseudo" de l'utilisateur dans la BDD du serveur avec le pseudo saisi.

### Changer pseudo
M�me principe que dans Initilisation application

### Fermeture application
#### R�seau local
Lorsque l'on ferme l'application on envoie un message en broadcast � tous les utilisateurs du r�seau local
pour leur informer que l'on est inactif. De cette fa�on ils savent qu'ils ne peuvent plus communiquer avec
nous.

#### R�seau distant
Pour un r�seau distant on informe le serveur de notre d�connexion et nous mets inactif dans la BDD.

### Clavardage
Un utilisateur peut initier une session de clavardage avec un autre utilisateur.
Il �tablit la connexion puis il va chercher dans sa liste d'historiques s'il a un historique de conversation
avec l'utilisateur avec qui il veut parler.\
Il peut ensuite envoyer des messages ou bien fermer la session de clavardage. Lors de la fermeture de la session les
utilisateurs vont regarder s'ils ont un historique avec l'autre. Si c'est le cas alors ils ajoutent la conversation
qu'ils viennent d'avoir � la suite de l'historique sinon ils cr��ent un historique pour l'utilisateur avec qui ils
viennent de parler.

### Rafraichisseur
Il sert pour un r�seau distant. Il permet de rafraichir la liste des utilisateurs visibles par l'agent. Toutes
les 5 secondes il envoie � l'agent le pseudo et si les utilisateurs sont actifs ou non.

### Suppositions
Utilisateur sur un seul poste, donc historique stock� sur le poste.
Pour r�cup�rer l'historique qui est stock� sur la machine on associe les historiques avec les ids des utilisateurs
avec qui on a parl� Id = adresse mac de la machine de l'utilisateur avec qui on a parl�