<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Мои сообщения</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 900px; margin: 32px auto; background: #f6f7fb; color: #20242a; }
        a { color: #1f5fbf; }
        .message { background: white; border: 1px solid #dde2ea; border-radius: 8px; padding: 14px; margin-bottom: 10px; }
        .meta { color: #697381; font-size: 13px; margin-bottom: 6px; }
        .content { white-space: pre-wrap; overflow-wrap: anywhere; margin-bottom: 10px; }
        button { border: 0; border-radius: 6px; padding: 8px 12px; background: #d9342b; color: white; cursor: pointer; }
    </style>
</head>
<body>
<h1>Мои сообщения</h1>
<p><a href="/chat">Вернуться в чат</a></p>

<#list messages as message>
    <article class="message">
        <div class="meta">${message.sentAt}</div>
        <div class="content">${message.content?html}</div>
        <form action="/chat/${message.id}/delete" method="post">
            <button type="submit">Удалить</button>
        </form>
    </article>
<#else>
    <p>Вы пока не отправляли сообщения.</p>
</#list>
</body>
</html>
