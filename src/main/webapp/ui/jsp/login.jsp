<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Login to CS Systems</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <link href="/style.css" rel="stylesheet" type="text/css">
    <meta
</head>
<body>
    <div class="wrapper">
        <div class="main">
            <div class="logo">
                <img src="/logo.png" alt="logo">
            </div>
            <div class="form-wrapper">
                <c:if test="${param.error ne null}">
                    <div style="color: red">Invalid credentials.</div>
                </c:if>
                <form action="/login" method="post" class="required-validation" novalidate>
                    <div class="form-group">
                        <input placeholder="Email" type="text" class="form-control" id="username" name="username" required>
                        <div class="invalid-feedback">
                            Please enter a valid email address.
                        </div>
                    </div>
                    <div class="form-group">
                        <input placeholder="Password" type="password" class="form-control" id="pwd" name="password" required>
                        <div class="invalid-feedback">
                            Please enter a valid password.
                        </div>
                    </div>
                    <div class="form-check" style="margin-bottom: 20px">
                        <input class="form-check-input" type="checkbox" id="rememberMe" name="remember-me">
                        <label class="form-check-label" for="rememberMe">Remember me</label>
                    </div>
                    <button type="submit" class="btn btn-default btn-login">Sign In</button>

                    <input type="hidden" name="${_csrf.parameterName}"
                        value="${_csrf.token}" />
                </form>
            </div>
        </div>
    </div>
    <script>
    (function() {
      window.addEventListener('load', function() {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('required-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function(form) {
          form.addEventListener('submit', function(event) {
            if (form.checkValidity() === false) {
              event.preventDefault();
              event.stopPropagation();
            }
            form.classList.add('was-validated');
          }, false);
        });
      }, false);
    })();
    </script>
</body>
</html>
