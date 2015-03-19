<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Log in</title>
</head>
<body>

<h1>Log in</h1>

<form role="form" action="/login" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <div>
        <label for="firstName">First Name</label>
        <input type="text" name="firstName" id="firstName" required autofocus>
    </div>
    <div>
        <label for="lastName">Last Name</label>
        <input type="text" name="lastName" id="lastName" required>
    </div>
    <div>
        <label for="fisrtName">Username</label>
        <input type="text" name="username" id="username" required>
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" name="password" id="password" required>
    </div>
    <button type="submit">Sign in</button>
</form>