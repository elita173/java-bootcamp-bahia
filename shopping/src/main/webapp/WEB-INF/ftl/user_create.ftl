<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form" type="eu.kielczewski.example.domain.UserCreateForm" -->
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Create a new user</title>
</head>
<body>
<nav role="navigation">
    <ul>
        <li><a href="/">Home</a></li>
    </ul>
</nav>

<h1>Create a new user</h1>

<form role="form" name="form" action="" method="post">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

	<div>
        <label for="firstName">First name</label>
        <input type="text" name="firstName" id="firstName" value="${form.firstName}" required autofocus/>
    </div>
    <div>
        <label for="firstName">Last name</label>
        <input type="text" name="firstName" id="firstName" value="${form.firstName}" required autofocus/>
    </div>
    <div>
        <label for="username">Username</label>
        <input type="text" name="username" id="username" value="${form.username}" required autofocus/>
    </div>
    <div>
        <label for="password">Password</label>
        <input type="password" name="password" id="password" required/>
    </div>
    <button type="submit">Register</button>
</form>

<@spring.bind "form" />

</body>
</html>