#/bin/bash

echo "Démarrage de l'application"

java -jar TP10_client/TP10_client.jar

if [ $? -ne 0 ]
then
  echo "Erreur lors du lancement de l'application" >&2
fi
