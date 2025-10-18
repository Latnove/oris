<html lang="ru">
<#include "base.ftl">
<#macro title>Sign Up</#macro>
<#macro scripts>
    <script>
        var timeout
        console.log("bulat")
        $(document).on("input", "#login-input", function () {
            console.log("dgfdfrgfghgfh")
            const input = $("#login-input").val()
            timeout = setTimeout(() => {
                $.get("/ajax/sign_up?login=" + input, function (response) {
                    $("#resp-text").text(response)
                })
            }, 200)
        })
    </script>
</#macro>
<#macro content>
    <div id="sign_up_form">
        <form method="post" action="/sign_up" enctype="multipart/form-data">
            Sign Up:
            <label>
                <input type="text" id="login-input" name="login" placeholder="login">
            </label>
            <br>
            <div id="resp-text"></div>
            <br>
            Password:
            <label>
                <input type="password" name="password" placeholder="password">
            </label>
            <br>
            Name:
            <label>
                <input type="text" name="name" placeholder="name">
            </label>
            <br>
            LastName:
            <label>
                <input type="text" name="lastname" placeholder="last name">
            </label>
            <br>
            LastName:
            <label>
                <input type="file" name="file" />
            </label>

            <input type="submit" value="Sign up">
            Уже зарегестрирован? <a href="/login">Войти</a>
        </form>
    </div>
</#macro>
</html>