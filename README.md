# projet_coo

BDD
La base de données du serveur contient une liste de tous les utilisateurs avec les champs suivant :
- ID
- Pseudo
- Actif
- Liste d'historiques

Liste historiques
Un utilisateur dans la base de données contient une liste d'historiques.
Cette liste d'historiques est composée des champs :
- ID d'un utilisateur avec qui on a communiqué
- Une référence vers cet historique
De cette façon l'historique commun à 2 utilisateurs est stocké une seule fois sur le serveur et chacun des utilisateurs a
une référence vers cet historique, cela évite de dédoubler l'historique