<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>История чата</title>
    <style>
        body { font-family: Arial, sans-serif; max-width: 900px; margin: 32px auto; background: #f6f7fb; color: #20242a; }
        a { color: #1f5fbf; }
        .message { background: white; border: 1px solid #dde2ea; border-radius: 8px; padding: 14px; margin-bottom: 10px; }
        .meta { color: #697381; font-size: 13px; margin-bottom: 6px; }
        .content { white-space: pre-wrap; overflow-wrap: anywhere; }
    </style>
</head>
<body>
<h1>История общего чата</h1>
<p><a href="/chat">Открыть чат</a></p>

<#list messages as message>
    <article class="message">
        <div class="meta">
            <strong>${message.authorUsername?html}</strong>
            <span>${message.sentAt}</span>
        </div>
        <div class="content">${message.content?html}</div>
    </article>
<#else>
    <p>Сообщений пока нет.</p>
</#list>
</body>
</html>
