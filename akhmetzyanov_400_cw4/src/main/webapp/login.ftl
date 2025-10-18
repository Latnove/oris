<html lang="en">
<#include "base.ftl">
<#macro title>Log in</#macro>
<#macro scripts></#macro>
<#macro content>
    <div id="login_form">
        <#if Session.user?has_content>
            Вы уже вошли в аккаунт.
        <#else>
            <form method="post" action="/login">
                Login:
                <label>
                    <input type="text" name="login" placeholder="login">
                </label>
                <br>
                Password:
                <label>
                    <input type="password" name="password" placeholder="password">
                </label>
                <br>
                <input type="submit" value="Login">
                Не зарегестрирован? <a href="/sign_up">Зарегестрироваться</a>
            </form>
        </#if>
    </div>
</#macro>
</html>