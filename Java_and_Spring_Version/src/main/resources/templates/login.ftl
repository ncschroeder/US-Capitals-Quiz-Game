<!DOCTYPE html>
<html lang="en-US">
<head>
    <meta charset="utf-8" />
    <title>Admin Login</title>
    <link rel="icon" href="us-favicon.png" type="image/x-icon" />
    <link rel="stylesheet" href="common.css" />
    <link rel="stylesheet" href="login.css" />
</head>
<body>
    <a href="/" id="top-left-home-link">Home</a>
    <h1>Admin Login</h1>
    
    <div id="error-or-logout-message-div" class="small-text">
        <#if RequestParameters.error??>
            Invalid username and password.
        <#elseif RequestParameters.logout??>
            You've been logged out.
        </#if>
    </div>
    
    <form action="/login" method="post">
        <div id="credentials-div">
            <label for="username-input">Username</label>
            <input type="text" id="username-input" name="username" />
            
            <label for="password-input">Password</label>
            <input type="password" id="password-input" name="password" />
        </div>
        <input type="submit" value="Log in" />
    </form>
</body>
</html>