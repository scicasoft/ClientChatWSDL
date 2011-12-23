La classe qui va servir de WebService se trouve dans ./ressources/ChatRoom.java
Il faut le déployer en tant que service

Ensuite il faut modifier la variable URL du fichier main.rb ligne 10 pour mettre le link de votre ressource wsdl

Avant de le lancer il faut executer la commande : sudo bundle install
a partir de la racine du projet

Et enfin vous lancez l'application en vous deplacer sur la racine du projet et taper la commance :
ruby main.rb
