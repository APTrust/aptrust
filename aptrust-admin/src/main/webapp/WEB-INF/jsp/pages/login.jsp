<?xml version="1.0" encoding="ISO-8859-1"?>
<%-- Author: Daniel Bernstein --%><%@include file="../include/libraries.jsp"%>
<tiles:insertDefinition
  name="page-base"
  flush="true">
  <tiles:putAttribute name="title">Sign into AP Trust Admin</tiles:putAttribute>

  <tiles:putAttribute name="body">
    <header role="banner">
      <hgroup>
        <h1>AP Trust</h1>
      </hgroup>
      <div id="courtesy"></div>
    </header>

    <div role="main">
      <nav id="primary-nav" role="navigation">
        
      </nav>
    
      <h2>Sign In</h2>
      <c:if test="${not empty pageContext.request.parameterMap['error']}">
      <div class="error">
        The username and/or password is invalid. Please try again.
      </div>
      </c:if>

      <form
        id="loginForm"
        action="${pageContext.request.contextPath}/j_spring_security_check"
        method="POST">
        <fieldset>
          <ol>
            <li><label for="username">Username</label> <input
              name="j_username"
              type="text"
              autofocus="true"
              id="username" /></li>

            <li><label>Password</label> <input
              name="j_password"
              type="password"
              id="password" /></li>
          </ol>
        </fieldset>

        <fieldset>
          <button
            class="primary"
            type="submit"
            id="login-button">Login</button>
        </fieldset>
      </form>
    </div>
  </tiles:putAttribute>
</tiles:insertDefinition>

