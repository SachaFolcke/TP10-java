#/bin/bash

echo "Démarrage du serveur"

java -jar TP10_server/TP10_server.jar

if [ $? -eq 0 ]
then
  echo "CTRL+C pour arrêter le serveur"
else
  echo "Erreur lors du lancement du serveur" >&2
fi

exec bash;
