1.使用axis2调用webservice的方法配置。
2.服务器端使用axis2 1.7.6的bin下面的axis2server.bat的默认Version服务。
3.Stub使用axis2  code  generator生成。
4.axis最好还是配合默认的httpclient3.1使用。使用4需要自行配置axis2.xml和repo。
5.connection reset可以在各种场景下发生。根据需要配置重试。
