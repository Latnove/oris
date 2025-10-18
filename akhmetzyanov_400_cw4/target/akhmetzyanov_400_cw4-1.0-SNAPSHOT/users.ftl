<html lang="en">
<#include "base.ftl">
<#macro title>Users</#macro>
<#macro scripts></#macro>
<#macro content>
    <table>
        <caption>Users</caption>
        <tr>
            <th>Login</th>
            <th>Name</th>
            <th>LastName</th>
        </tr>
        <#if users?has_content>
            <#list users as u>
                <tr>
                    <td>${u.login()}</td>
                    <td>${u.name()}</td>
                    <td>${u.lastName()}</td>
                    <td>
                        <img src=${u.image()!""} style="display: block; width: 80px; height: 80px; object-fit: cover; border-radius: 50%;" alt="profile image" />
                    </td>
                </tr>
            </#list>
        </#if>
        <br>
    </table>

    <form method="post" action="/users">
        <input type="submit" value="Не нажимать!">
    </form>
</#macro>
</html>