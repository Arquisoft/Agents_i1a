[![Codacy Badge](https://api.codacy.com/project/badge/Grade/887b00d1840f4b1eb40974f275c41e55)](https://www.codacy.com/app/jelabra/Agents_i1a?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Agents_i1a&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/Arquisoft/Agents_i1a.svg?branch=master)](https://travis-ci.org/Arquisoft/Agents_i1a)
[![codecov](https://codecov.io/gh/Arquisoft/Agents_i1a/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Agents_i1a)


# agents_i1b

Run tests in local: 

In order to run the tests you need to download and install mongoDB. You must create an empty folder called "data\db" in the root of the partition where you installed mongo (Example: "C:\data\db"). If you already have it installed, please run in mongoConsole db.users.remove({}) before executing the tests. (Executing the tests provide mock data)
1. Comment the line of the application.properties file.
2. Uncomment "@Document(collection= "users")" in AgentInfo and comment the next line.
3. To delete the database: launch from command line "mongod" and in other command line launch "mongo", after that write "db.users.remove({})".
4. Launch the tests.

Run tests in remote:

1. Comment "@Document(collection= "users")" in AgentInfo and uncomment the next line.
2. Uncomment the line of the application.properties file.

# Authors
- Herminio García González (@herminiogg)
- Jose Emilio Labra Gayo (@labra)
- Paula Tuñón Alba
- Juan Francisco Piñera Ovejero
- Guillermo Rodríguez González 

# Course 2017/18 manteiners
- Lucia Méndez López (@UO250970, @Soondra)
- Pablo Suárez García (@PabloSuaGar)
- Marcial Francisco Parrilla Socas (@marcialfps)
- Alejandro González Campomanes (@alexgonzcampomanes)
