Backend часть приложения Bank

-------------------------------

Установка:
Необходимо склонировать репозиторий, и открыть файл pom.xml как проект.
После чего произойдет подгрузка библиотек.
Также для корректной работы потребуется база данных - в моем случае Postgresql. Важно изменить ссылку для доступа к базе данных и пароль в application.properties.

Для создания таблиц и наполнения их готовыми данными, нужно запустить 2 sql-файла в папке src/main/resources

-------------------------------

Принцип работы:
Проект симулирует банковское приложение - в нем есть пользователи, аккаунты, переводы денег. 
Для начала работы необходимо создать пользователя, после чего залогиниться. Это можно сделать через get запрос на адрес /login.
После чего использовать полученный JWT токен в качестве авторизации.
Теперь можно использовать весь функционал - получать информации о счете, проверять историю переводов, оформлять сами переводы.
Один нюанс - токен выдается на 30 минут, после чего необходимо войти в систему заново (или поправить настройки токена :3).

-------------------------------

Технологии:
Spring boot, MVC, Hibernate, Lombok, Spring security, JWT.
Vaadin как frontend часть.
PostgreSql в качесте DB.

