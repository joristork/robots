Author: Vipul Gupta
Last Update: Aug 17, 2010

Overview:
========
This project contains a small footprint "AppServer" that binds to TCP port 9999 and hosts two different "WebApplications":
(1) EchoApp: this web application always outputs HTML containing a printout of the HTTP request it recieved.
(2) DateApp: this web application only supports HTTP GET requests and outputs the current date and time.

How to build and run:
=====================
- First build CHTTPlib, WoTlib-common and WoTlib-host (in that order). You will need to have the Sun SPOT SDK installed. Eventually, I may get around to removing any dependencies on the SDK. It should be easy so if this is a priority, let me know.

- Build this project and run using

   % ant host-compile
   % ant host-run -Dbasestation.not.required=true

- From a browser, you may access the following URLs
   http://localhost:9999/r (lists installed web applications and the URLs they monitor)
   http://localhost:9999/r?sh=e (show which web application has a "short URL" of e)
   http://localhost:9999/echo or http://localhost:9999/e (e is the "short URL" for the echo application)
   http://localhost:9999/echo/123?key1=val1
   http://localhost:9999/date or or http://localhost:9999/d

- You could also use cURL to interact with this AppServer, e.g.

   curl --dump-header hdrs.txt --request GET "http://localhost:9999/echo"
   curl --dump-header hdrs.txt --request POST --data "data-to-be-posted" "http://localhost:9999/echo"
   curl --dump-header hdrs.txt --request GET "http://localhost:9999/date?a=b"
   curl --dump-header hdrs.txt --request POST --data "junk" "http://localhost:9999/date"

 The HTTP headers from the response will be captured in hdrs.txt which can be viewed using any text editor or commands such as "more" or "cat".

What next:
==========
- Using the Date of echo services as a model, you can create your own service and register the URL(s) where that service may be accessed.

- If you are behind a NAT/Firewall, you can still enable access to your server via RHTTP (see http://tools.ietf.org/html/draft-lentczner-rhttp-00). RHTTP (Reverse HTTP) allows an RHTTP client to initiate a connection to an RHTTP server (which must be available on the open Internet) and have the latter relay HTTP requests to it.

We normally run an RHTTP relay (thanks to code from yaler.org) on sensor.network.com at port 1234. First check that the server is still running (if you type the URL http://sensor.network.com:1234/ in your browser, you should see a "Gateway timeout" messge). If the relay is running, uncomment the RHTTP line in Main.java and replace "xxxx" with
something that is globally unique (so it does not conflict with another 
SPOT user). One example could be the last part of your basestation's address.
Let's say your basestation SPOT is 0014.4F01.0000.0B0B then you could replace xxxx
with 0B0B.

Now, anyone that contacts http://sensor.network.com:1234/0B0B will be contacting your server, i.e. an HTTP GET to
http://sensor.network.com:1234/0B0B/echo will output the HTTP request and http://sensor.network.com:1234/0B0B/date will output the date from your server.

Questions?
==========

Email vipul dot x dot gupta at oracle dot com.


