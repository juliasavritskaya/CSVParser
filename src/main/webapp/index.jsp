<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello!" %>
</h1>
<br/>
<a href="bicycle?rideable_type=electric_bike">rideable_type filter</a>
<br/>
<a href="tripdata?startDate=2021/06/25%00:00:00&endDate=2021/06/27%00:00:00">start_end dates filter</a>
</body>
</html>