<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title><c:choose>
		<c:when test="${not empty Exp_Msg}">
			<c:out value="${Exp_Msg}" />
		</c:when>
		<c:otherwise>
			Oops Error Page
		</c:otherwise>
	</c:choose></title>
<link rel="stylesheet" href="res/css/material.min.css">
<link rel="stylesheet" href="res/css/material.icon.css">
<link rel="stylesheet" href="res/css/style.css">
<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes">
<link rel="icon" sizes="128x128" href="res/img/note_pin.ico">

<!-- Add to homescreen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-mobile-web-app-title" content="Material Design Lite">
<link rel="apple-touch-icon-precomposed" href="res/img/osx_pin.icns">

<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileImage" content="res/img/128_pin.png">
<meta name="msapplication-TileColor" content="#3372DF">

<link rel="shortcut icon" href="res/img/128_pin.png">
<script type="text/javascript" src="res/js/material.min.js"></script>
</head>
<body>
	<div class="center_content">
		<br>
		<c:choose>
			<c:when test="${not empty Exp_Msg}">
				<c:out value="${Exp_Msg}" />
			</c:when>
			<c:otherwise>
				Oops Error Page
		</c:otherwise>
		</c:choose>
		<br />
		<c:choose>
			<c:when test="${not empty Exp_Des}">
				<span> &#x2639;<c:out value="${Exp_Des}" />
					&nbsp;Please Goto <a class="mdl-js-button" href="/NoteApp">Home</a>&#x263A
				</span>
			</c:when>
			<c:otherwise>
			Oops Error Page!!! Please goto #<a class="mdl-js-button"
					href="/NoteApp">Home Page</a>
			</c:otherwise>
		</c:choose>	
		</div>
</body>
</html>