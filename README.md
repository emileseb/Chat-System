# projet_coo

BDD
La base de donn�es du serveur contient une liste de tous les utilisateurs avec les champs suivant :
- ID
- Pseudo
- Actif
- Liste d'historiques

Liste historiques
Un utilisateur dans la base de donn�es contient une liste d'historiques.
Cette liste d'historiques est compos�e des champs :
- ID d'un utilisateur avec qui on a communiqu�
- Une r�f�rence vers cet historique
De cette fa�on l'historique commun � 2 utilisateurs est stock� une seule fois sur le serveur et chacun des utilisateurs a
une r�f�rence vers cet historique, cela �vite de d�doubler l'historique