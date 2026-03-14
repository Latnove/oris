<html>
<head>
    <meta charset="UTF-8">
    <title>wow</title>

    <style>

        *{
            box-sizing:border-box;
            margin:0;
            padding:0;
        }

        body{
            height:100vh;
            display:flex;
            justify-content:center;
            align-items:center;
            font-family: "Segoe UI", sans-serif;
            background:linear-gradient(135deg,#5f9cff,#7f53ff);
        }

        .form-card{
            background:rgba(255,255,255,0.15);
            backdrop-filter:blur(15px);
            border-radius:14px;
            padding:40px;
            width:340px;
            box-shadow:0 10px 30px rgba(0,0,0,0.25);
            color:white;
        }

        .form-card h2{
            text-align:center;
            margin-bottom:25px;
            font-weight:500;
        }

        .input-group{
            display:flex;
            flex-direction:column;
            margin-bottom:18px;
        }

        .input-group label{
            font-size:14px;
            margin-bottom:6px;
            opacity:0.85;
        }

        .input-group input{
            padding:12px;
            border:none;
            border-radius:8px;
            font-size:14px;
            outline:none;
            transition:0.3s;
        }

        .input-group input:focus{
            box-shadow:0 0 0 2px rgba(255,255,255,0.5);
        }

        button{
            width:100%;
            padding:12px;
            border:none;
            border-radius:8px;
            font-size:15px;
            background:white;
            color:#5f53ff;
            cursor:pointer;
            transition:0.3s;
            font-weight:600;
        }

        button:hover{
            transform:translateY(-2px);
            box-shadow:0 5px 15px rgba(0,0,0,0.3);
        }

        button:active{
            transform:scale(0.97);
        }

    </style>
</head>
<body>
<form class="form-card" method="post">

    <h2>Создать пользователя</h2>

    <div class="input-group">
        <label>Username: <span class="left-characters">15</span></label>
        <input class="username" placeholder="Введите username" name="username" required maxlength="15">
    </div>

    <div class="input-group">
        <label>Password</label>
        <input class="password" placeholder="Введите пароль" name="password" required minlength="8" />
    </div>

    <button class="button" type="submit">Создать</button>

</form>

<script>
    const MAX_CHARACTERS = 15
    const usernameEl = document.querySelector(".username")
    const passwordEl = document.querySelector(".password")
    const leftEl = document.querySelector(".left-characters")
    const buttonEl = document.querySelector(".button")
    const formEl = document.querySelector(".form-card")

    document.addEventListener("DOMContentLoaded", () => {
        leftEl.textContent = "Осталось: " + MAX_CHARACTERS
    })

    usernameEl.addEventListener("input", (e) => {
        const length = e.target.value.length
        leftEl.textContent = "Осталось: " + (MAX_CHARACTERS - length)

        if (!length) {
            buttonEl.disable()
            buttonEl.style.opacity = 0.6
        }
    })

    formEl.addEventListener("submit", async (e) => {
        e.preventDefault();

        console.log(usernameEl.value, passwordEl.value)
        try {
            const response = await fetch("/auth", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: usernameEl.value,
                    password: passwordEl.value,
                    roles: ["USER"]
                })
            })

            if (!response.ok) {
                throw new Error("Error with send")
            } else {
                window.location.href = "/users";
            }

            console.log(data)
        } catch (error) {
            console.log(error)
        }
    })


</script>
</body>
</html>