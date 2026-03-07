<html>
<head>

</head>
<body>
<h1>Hello World, ${name!"Человек"}!</h1>

<form method="post" action="/users">
    <div class="left-characters"></div>
    <label>
        Добавить пользователя:
        <input class="input" value="" placeholder="Username" name="username" required maxlength="15"/>
    </label>
    <button type="submit" class="button" style="background-color: azure; border: 1px solid darkgray; padding: 10px 20px">Отправить</button>
</form>

<script>
    const MAX_CHARACTERS = 15
    const inputEl = document.querySelector(".input")
    const leftEl = document.querySelector(".left-characters")
    const buttonEl = document.querySelector(".button")

    document.addEventListener("DOMContentLoaded", () => {
        leftEl.textContent = "Осталось: " + MAX_CHARACTERS
    })

    inputEl.addEventListener("input", (e) => {
        const length = e.target.value.length
        leftEl.textContent = "Осталось: " + (MAX_CHARACTERS - length)

        if (!length) {
            buttonEl.disable()
            buttonEl.style.opacity = 0.6
        }
    })


</script>
</body>
</html>