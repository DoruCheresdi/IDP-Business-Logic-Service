docker run --name='my_sql_container' `
-e MYSQL_ROOT_PASSWORD=root `
-e MYSQL_DATABASE=idp_mysql -p 3306:3306 -d mysql:latest