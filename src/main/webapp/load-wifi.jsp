<%@page import="zerobase.wifi.service.WifiApiComponent"%>
<%@page import="zerobase.wifi.model.WifiInfoModel"%>
<%@page import="zerobase.wifi.dto.WifiInfoDto"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html>
<head>
    <title>Load WiFi</title>
</head>
<body>
    <div style="text-align: center;">
        <% 
            WifiApiComponent wifiApiComponent = new WifiApiComponent();
            int total = wifiApiComponent.getTotal();
        %>
        <h1><%= total %>개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>
        <a href="/">홈으로 가기</a>
    </div>
</html>
