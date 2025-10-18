<!DOCTYPE html>
<html lang="en">
<#include "base.ftl">
<#macro title>Users</#macro>
<#macro scripts></#macro>
<#macro content>
    <h3>
        Hello ${user}! Login successful.
        <br>
        Session ID: ${sessionId}
        <br>
        CookieUser: ${cookieUser}
        <br>
        <a href="/">Вернуться на главную</a>
    </h3>
</#macro>
</html>
