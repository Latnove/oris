<!DOCTYPE html>
<html>
<head>
    <title>Заметка</title>
</head>
<body>

<h1>
    <#if note.id??>
        Редактировать заметку
    <#else>
        Создать заметку
    </#if>
</h1>

<form action="<#if note.id??>/notes/${note.id}/edit<#else>/notes/create</#if>" method="post">

    <label>Заголовок:</label><br>
    <input type="text" name="title" value="${note.title!}"/><br><br>

    <label>Текст:</label><br>
    <textarea name="content">${note.content!}</textarea><br><br>

    <label>
        <input type="checkbox" name="public"
               <#if note.isPublic?? && note.isPublic>checked</#if> />
        Публичная
    </label><br><br>

    <button type="submit">Сохранить</button>
</form>

<br>
<a href="/notes">Назад</a>

</body>
</html>