<#ftl encoding="UTF-8">

<!DOCTYPE html>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login page</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script>
        var timeout

        $(document).on('change', '#login-input', function () {
	    const value = $('#login-input').val()

	    clearTimeout(timeout)

	    timeout = setTimeout(() => {
		    $.get('/ajax/sign_up?login=' + value, function (response) {
			    $('#login-input-text').text(response)
		    })
	    }, 200)
    })
    </script>
</head>

<body>

<form method="post" action="sign_up" accept-charset="UTF-8">
    Login:
    <input id="login-input" type="text" name="login" placeholder="your login">
    <div id="login-input-text"></div>
    Name:
    <input type="text" name="name" placeholder="your name">
    Lastname:
    <input type="text" name="lastname" placeholder="your lastname">
    Password:
    <input type="password" name="password" placeholder="your password">
    <br>
    <input type="submit" value="sign up bro">
</form>

</body>

</html>