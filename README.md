# projet_coo

### BDD
La base de données du serveur contient une liste de tous les utilisateurs avec les champs suivant :
- ID
- Pseudo
- Actif
- Liste d'historiques

### Liste historiques
Un utilisateur dans la base de données contient une liste d'historiques.\
Cette liste d'historiques est composée des champs :
- ID d'un utilisateur avec qui on a communiqué
- Une référence vers cet historique\
De cette façon l'historique commun à 2 utilisateurs est stocké une seule fois sur le serveur et chacun des utilisateurs a
une référence vers cet historique, cela évite de dédoubler l'historique

### Clavardage
Un utilisateur peut initier une session de clavardage avec un autre et lui envoyer des messages.\
Lorsqu'il fermera la session, l'historique des messages sera envoyé au serveur.\
Si un historique de messages existe déjà entre les 2 utilisateurs il ajoutera juste l'historique reçu à celui
existant. Si en revanche aucun historique n'existe alors il va en créer un, ajouter l'historique reçu et
passer la référence vers cet historique aux 2 utilisateurs dans la BDD.

### Rafraichisseur
Il permet de rafraichir la liste des utilisateurs visibles par l'agent. Toutes les 5 secondes il envoie à l'agent
le pseudo et si les utilisateurs sont actifs ou non