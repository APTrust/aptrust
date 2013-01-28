<?xml version="1.0" encoding="ISO-8859-1"?>
<%@include file="../include/libraries.jsp"%>

<tiles:importAttribute name="institution" scope="request"/>
<header role="banner">
  <hgroup>
    <h1>AP Trust</h1>
    <h2>University of Virginia</h2>
  </hgroup>
  <div id="courtesy">
    <p>
      Welcome, Thomas Jefferson! <a href="#">Sign Out</a>
    </p>
    <form
      action=""
      method="get"
      accept-charset="utf-8">
      <input
        type="search"
        name="query"
        id="search"
        placeholder="enter search terms"
        value=""/>
    </form>
  </div>
</header>
<nav
  id="primary-nav"
  role="navigation">
  
  <c:url var="institutionBaseUrl" value="/html/${institution.id}"/>
  <ul>
    <li><a href="${institutionBaseUrl}">Dashboard</a></li>
    <li><a href="${institutionBaseUrl}/discovery">Discovery</a></li>
  </ul>
</nav>
<div role="main">
  <tiles:insertAttribute name="content" />
</div>

