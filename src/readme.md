## Об исходниках ⚙️
 
Проект состоит из двух частей:
1. REST API 🚀
2. Мобильный клиент 📲

Ссылка к API в мобильном клиенте указывается в файле [Constants.java](https://github.com/keyldev/brainbrakers/blob/main/src/Brainbrakers.Mobile/app/src/main/java/com/keyldev/brakerspodcast/Constants.java)

Подключение к базе данных производится в файле [ApplicationContext.cs](https://github.com/keyldev/brainbrakers/blob/main/src/Brainbrakers.API/Data/ApplicationContext.cs)

🔮 В качестве СУБД - MySQL <br>
🔮 В качестве ORM - EF Core

🔒 JWT + Refresh токены как мера безопасности <br>
🔒 SHA256 в качестве защиты паролей

