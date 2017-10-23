<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<meta content="text/html; charset=utf-8" http-equiv=Content-Type>
<title><c:choose><c:when test="${not empty Exp_Msg}">${Exp_Msg}</c:when><c:otherwise>Oops Error Page</c:otherwise></c:choose></title>
<meta name=mobile-web-app-capable content=yes>
<link rel=icon sizes=128x128 href=/note/res/img/note_pin.ico>
<meta name=apple-mobile-web-app-capable content=yes>
<meta name=apple-mobile-web-app-status-bar-style content=black>
<meta name=apple-mobile-web-app-title content="Page Not Found">
<link rel=apple-touch-icon-precomposed href=/note/res/img/osx_pin.icns>
<meta name=msapplication-TileImage content=/note/res/img/128_pin.png>
<meta name=msapplication-TileColor content=#3372DF>
<link rel="shortcut icon" href=/note/res/img/128_pin.png>
<style>body{font-family:Courgette,cursive}body{background:#f3f3e1}.wrap{margin:0 auto;width:1000px}.logo{margin-top:50px}.logo h1{font-size:75px;color:#8f8f91;text-align:center;margin-bottom:1px;text-shadow:1px 1px 6px #fff}.logo p{color:#e492a2;font-size:20px;margin-top:1px;text-align:center}.logo p span{color:#90ee90}.sub a{color:#fff;background:#8f8e8c;text-decoration:none;padding:9px 20px;font-size:13px;font-family:arial,serif;font-weight:700;-webkit-border-radius:3em;-moz-border-radius:.1em;-border-radius:.1em}.footer{color:#8f8e8c;position:absolute;right:10px;bottom:10px}.footer a{color:#e492a2}</style>
</head>
<body>
<div class=wrap>
<c:choose><c:when test="${not empty msg}"><h4><c:out value="${msg}"></c:out></h4></c:when><c:otherwise><div class=logo><h3>Is Empty</h3></div></c:otherwise></c:choose>
</div>
<div class=footer>© 2017 KManiZoro . All Rights Reserved</div>
</body>
</html>