/*

    最近の更新を監視するためのスクリプト

*/
/*
alert();
var xmlHttp = new XMLHttpRequest();
xmlHttp.timeout = 2000;
xmlHttp.open("GET", "http://localhost:8080/medis/api", "true");

xmlHttp.ontimeout = function(e) {
    alert();
}*/

var xhr = new XMLHttpRequest();
xhr.open('GET', '/server', true);

xhr.timeout = 2000; // time in milliseconds

xhr.onload = function () {
  // Request finished. Do processing here.
};

xhr.ontimeout = function (e) {
  alert('timeout!');
};

xhr.send(null);