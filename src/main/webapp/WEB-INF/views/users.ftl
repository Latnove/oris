<!DOCTYPE html>
<html>
<head>
    <title>Users</title>
</head>
<body>

<h1>Users</h1>

<ul>
<#list users as user>
    <li>${user.id} - ${user.username}</li>
</#list>
</ul>

</body>
</html>