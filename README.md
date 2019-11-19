# projet_coo

### BDD
La base de donn�es du serveur contient une liste de tous les utilisateurs avec les champs suivant :
- ID
- Pseudo
- Actif
- Liste d'historiques

### Liste historiques
Un utilisateur dans la base de donn�es contient une liste d'historiques.\
Cette liste d'historiques est compos�e des champs :
- ID d'un utilisateur avec qui on a communiqu�
- Une r�f�rence vers cet historique\
De cette fa�on l'historique commun � 2 utilisateurs est stock� une seule fois sur le serveur et chacun des utilisateurs a
une r�f�rence vers cet historique, cela �vite de d�doubler l'historique

### Clavardage
Un utilisateur peut initier une session de clavardage avec un autre et lui envoyer des messages.\
Lorsqu'il fermera la session, l'historique des messages sera envoy� au serveur.\
Si un historique de messages existe d�j� entre les 2 utilisateurs il ajoutera juste l'historique re�u � celui
existant. Si en revanche aucun historique n'existe alors il va en cr�er un, ajouter l'historique re�u et
passer la r�f�rence vers cet historique aux 2 utilisateurs dans la BDD.

### Rafraichisseur
Il permet de rafraichir la liste des utilisateurs visibles par l'agent. Toutes les 5 secondes il envoie � l'agent
le pseudo et si les utilisateurs sont actifs ou non