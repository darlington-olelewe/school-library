docker run --name mysql-alpine \
-e MYSQL_ROOT_PASSWORD=my-secret-password \
-e MYSQL_DATABASE=library_db \
-e MYSQL_USER=app_user \
-e MYSQL_PASSWORD=app_password \
-p 3306:3306 \
-d mysql:8.0-oracle
