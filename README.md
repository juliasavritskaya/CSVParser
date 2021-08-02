COMMANDS

||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

database creation (mysql workbench):

CREATE DATABASE washrides;

||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

maven project creation (command line):

mvn archetype:generate -B ^
    -DarchetypeGroupId=org.apache.maven.archetypes ^
    -DarchetypeArtifactId=maven-archetype-quickstart ^
    -DarchetypeVersion=1.1 ^
    -DgroupId=by ^
    -DartifactId=mnkqn ^
    -Dversion=1.0-SNAPSHOT ^
    -Dpackage=mnkqn

|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

db schema migration:

mvn: flyway migrate

|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
