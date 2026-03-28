<!DOCTYPE html>
<html>
<head>
    <title>Публичные заметки</title>
</head>
<body>

<h1>Публичные заметки</h1>

<a href="/notes">Мои заметки</a>

<hr>

<ul>
    <#list notes as note>
        <li>
            <b>${note.title}</b> <br>
            ${note.content} <br>

            <small>Автор: ${note.author.username}</small>

            <hr>
        </li>
    </#list>
</ul>

</body>
</html>