<!DOCTYPE html>
<html >
<head>
    <title>Sign In</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" media="screen" href="${rc.getContextPath()}/resources/css/bootstrap.min.css"/>
    <link  rel="stylesheet" media="screen" href="${rc.getContextPath()}/resources/css/core.css"/>
</head>
<body>
<form class="form-narrow form-horizontal"  method="post" action="${rc.getContextPath()}/authenticate">
    <!-- inlcude csrf token -->
    <input type="hidden"
           name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
    <fieldset>
        <legend>Please Sign In</legend>
        <div class="form-group">
            <label for="inputEmail" class="col-lg-2 control-label">Email</label>
            <div class="col-lg-10">
                <input type="text" class="form-control" id="inputEmail" placeholder="Email" name="username" />
            </div>
        </div>
        <div class="form-group">
            <label for="inputPassword" class="col-lg-2 control-label">Password</label>
            <div class="col-lg-10">
                <input type="password" class="form-control" id="inputPassword" placeholder="Password" name="password" />
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="_spring_security_remember_me" /> Remember me
                    </label>
                </div>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <button type="submit" class="btn btn-default">Sign in</button>
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-offset-2 col-lg-10">
                <p>New here? <a href="signup" href="/signup">Sign Up</a></p>
            </div>
        </div>
    </fieldset>
</form>
</body>
</html>