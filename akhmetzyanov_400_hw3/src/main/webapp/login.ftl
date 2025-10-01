<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login page</title>
</head>

<body>

<#if name?has_content>
    <div>Вы уже авторизованы, если хотите сменить аккаунт, нажмите:</div>
    <a href="/logout">Выйти</a>
<#else>
    <form method="post" action="login">
        Login:
        <input type="text" name="login" placeholder="type your login here">
        Password:
        <input type="password" name="password">
        <br>
        <input type="submit" value="login">
    </form>
</#if>
</body>
</html>