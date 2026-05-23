<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Общий чат</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <script src="/webjars/sockjs-client/sockjs.min.js"></script>
    <script src="/webjars/stomp-websocket/stomp.min.js"></script>
    <style>
        body { background: #f6f7fb; }
        .chat-shell { max-width: 920px; margin: 32px auto; }
        .message-list { height: 520px; overflow-y: auto; background: white; border: 1px solid #dde2ea; border-radius: 8px; padding: 16px; }
        .message { border-bottom: 1px solid #edf0f5; padding: 10px 0; }
        .message:last-child { border-bottom: 0; }
        .message-header { display: flex; align-items: flex-start; justify-content: space-between; gap: 12px; }
        .message-meta { color: #6c757d; font-size: 13px; margin-bottom: 4px; }
        .message-content { white-space: pre-wrap; overflow-wrap: anywhere; }
        .admin-delete { flex: 0 0 auto; }
    </style>
</head>
<body>
<main class="chat-shell">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h1 class="h3 mb-0">Общий чат</h1>
        <div>
            <a class="btn btn-outline-secondary btn-sm" href="/chat/my">Мои сообщения</a>
            <a class="btn btn-outline-secondary btn-sm" href="/chat/public">Публичная история</a>
        </div>
    </div>

    <section id="messages" class="message-list mb-3">
        <#list messages as message>
            <article class="message" data-message-id="${message.id}">
                <div class="message-header">
                    <div class="message-meta">
                        <strong>${message.authorUsername?html}</strong>
                        <span>${message.sentAt}</span>
                    </div>
                    <#if isAdmin>
                        <button class="btn btn-outline-danger btn-sm admin-delete" type="button" data-message-id="${message.id}">
                            Удалить
                        </button>
                    </#if>
                </div>
                <div class="message-content">${message.content?html}</div>
            </article>
        </#list>
    </section>

    <form id="message-form" class="d-flex">
        <input id="message-input" class="form-control mr-2" type="text" maxlength="2000" autocomplete="off" placeholder="Написать сообщение" required>
        <button id="send-button" class="btn btn-primary" type="submit">Отправить</button>
    </form>
</main>

<script>
    const messagesEl = document.querySelector("#messages");
    const formEl = document.querySelector("#message-form");
    const inputEl = document.querySelector("#message-input");
    const sendButtonEl = document.querySelector("#send-button");
    const isAdmin = ${isAdmin?c};
    let stompClient = null;

    function scrollToBottom() {
        messagesEl.scrollTop = messagesEl.scrollHeight;
    }

    function appendMessage(message) {
        const article = document.createElement("article");
        article.className = "message";
        article.dataset.messageId = message.id;

        const meta = document.createElement("div");
        meta.className = "message-meta";

        const author = document.createElement("strong");
        author.textContent = message.authorUsername;

        const sentAt = document.createElement("span");
        sentAt.textContent = " " + (message.sentAt || "");

        const content = document.createElement("div");
        content.className = "message-content";
        content.textContent = message.content;

        const header = document.createElement("div");
        header.className = "message-header";
        meta.append(author, sentAt);
        header.append(meta);

        if (isAdmin) {
            const deleteButton = document.createElement("button");
            deleteButton.className = "btn btn-outline-danger btn-sm admin-delete";
            deleteButton.type = "button";
            deleteButton.dataset.messageId = message.id;
            deleteButton.textContent = "Удалить";
            header.append(deleteButton);
        }

        article.append(header, content);
        messagesEl.append(article);
        scrollToBottom();
    }

    function removeMessage(messageId) {
        const messageEl = messagesEl.querySelector('[data-message-id="' + messageId + '"]');
        if (messageEl) {
            messageEl.remove();
        }
    }

    async function deleteMessage(messageId) {
        const response = await fetch("/admin/messages/" + messageId, {method: "DELETE"});
        if (!response.ok) {
            throw new Error("Не удалось удалить сообщение");
        }
    }

    function connect() {
        const socket = new SockJS("/ws");
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function () {
            sendButtonEl.disabled = false;
            stompClient.subscribe("/topic/messages", function (payload) {
                appendMessage(JSON.parse(payload.body));
            });
            stompClient.subscribe("/topic/message-deletions", function (payload) {
                removeMessage(JSON.parse(payload.body).id);
            });
        }, function () {
            sendButtonEl.disabled = true;
        });
    }

    formEl.addEventListener("submit", function (event) {
        event.preventDefault();
        const content = inputEl.value.trim();
        if (!content || !stompClient) {
            return;
        }
        stompClient.send("/app/send", {"content-type": "application/json"}, JSON.stringify({content: content}));
        inputEl.value = "";
        inputEl.focus();
    });

    messagesEl.addEventListener("click", function (event) {
        const deleteButton = event.target.closest(".admin-delete");
        if (!deleteButton) {
            return;
        }

        deleteButton.disabled = true;
        deleteMessage(deleteButton.dataset.messageId).catch(function () {
            deleteButton.disabled = false;
        });
    });

    sendButtonEl.disabled = true;
    scrollToBottom();
    connect();
</script>
</body>
</html>
