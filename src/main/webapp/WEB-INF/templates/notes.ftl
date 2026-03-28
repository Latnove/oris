<!DOCTYPE html>
<html>
<head>
    <title>Мои заметки</title>
</head>
<body>

<h1>Мои заметки</h1>

<a href="/notes/create">Создать новую заметку</a> |
<a href="/notes/public">Публичные заметки</a>

<hr>

<ul>
    <#list notes as note>
        <li>
            <b>${note.title}</b> <br>
            ${note.content} <br>

            <#if note.isPublic>
                <span style="color:green;">(Публичная)</span>
            </#if>

            <br>

            <a href="/notes/${note.id}/edit">Редактировать</a>

            <form action="/notes/${note.id}/delete" method="post" style="display:inline;">
                <button type="submit">Удалить</button>
            </form>

            <hr>
        </li>
    </#list>
</ul>

</body>
</html>