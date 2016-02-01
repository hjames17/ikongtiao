<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <script src="http://cdn.sockjs.org/sockjs-0.3.min.js"></script>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <!-- 可选的Bootstrap主题文件（一般不用引入） -->
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="http://cdn.bootcss.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    <title>WebSocketTest</title>
    <script type="text/javascript">
        $(function(){
            var websocket;
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://115.29.203.145:8090/web/test");
            } else if ('MozWebSocket' in window) {
                websocket = new MozWebSocket("ws://115.29.203.145:8090/web/test");
            } else {
                websocket = new SockJS("ws://115.29.203.145:8090/web/test");
            }
            websocket.onopen = function (evnt) {
                $("#tou").html("Connect Success!")
            };
            websocket.onmessage = function (evnt) {
                $("#msg").html($("#msg").html() + "<br/>" + evnt.data);
            };
            websocket.onerror = function (evnt) {
            };
            websocket.onclose = function (evnt) {
                $("#tou").html("Game over!")
            }
            $('#send').bind('click', function() {
                send();
            });
            function send(){
                if (websocket != null) {
                    var message = document.getElementById('message').value;
                    websocket.send(message);
                } else {
                    alert('未与服务器链接.');
                }
            }
        });
    </script>

</head>
<body>
<div class="container" ng-app ="myApp">
    <h1>WebSocketTest</h1>
    <div class="page-header" id="tou">
    </div>
    <div class="well" id="msg">
        Message:
    </div>
    <div class="col-lg">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Sending..." id="message">
      <span class="input-group-btn">
        <button class="btn btn-default" type="button" id="send" >Send</button>
      </span>
        </div><!-- /input-group -->
    </div><!-- /.col-lg-6 -->
</div><!-- /.row -->
</div>
</body>
</html>