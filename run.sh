#@IgnoreInspection BashAddShebang
sleep 8                     # грязный хак, чтобы приложение раньше базы не завелось
PGPASSWORD=postgres  psql -h 0.0.0.0 -p 5422 -U postgres  -c "create database waves";
java -jar waves-1.jar
