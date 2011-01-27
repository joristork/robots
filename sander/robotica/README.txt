This directory contains some example SPOT applications to show off 
what the Sun SPOT can do and to help people learn how to program 
their SPOTs.

AirStore:

AirStore is a data repository that Sun SPOTs (and Sun SPOT Host 
Applications) can share via their radios. The goal is to enable 
simple, one line "gets" and "puts" with which distributed 
applications can store and access data, while enabling its use 
as a "blackboard" style coordination mechansim, similar to a 
Tuple Space. 

AirText:

This application uses the linear array of tricolor LEDs
on the eDemoboard and "persistence of vision" to display
text in thin air when the SPOT is moved back and forth.

BounceDemo:

A demo to pass a "ball" displayed in the LEDs between 2 or more SPOTs.

BuiltInSensorsDemo:

Demo of the Sun SPOT "eDemoBoard" built-in devices (light detector,
temperature, accelrometer, LEDs, switches)

CryptoDemos:

A collection of demos showing how to use cryptographic functionality on Sun SPOTs.

DatabaseDemo:

This demo application stores periodic sensor readings measured
on one or more Sun SPOTs into a database and runs a few simple 
SQL queries on the stored data.

EmulatorDemo:

A series of sample MIDlets to demonstrate the new SPOT Emulator.

GeigerDemo:

Reports radio traffic in an audible manner like a Geiger counter.

HostQuerySpotsDemo:

This demo is a host application that demonstrates how to locate 
nearby SPOTs and then send them commands over-the-air (OTA). It makes
use of the spotclient library that is also used by both Solarium and
ant commands.

HTTPDemo:

This demo application uses the Sun SPOT's built-in HTTP networking 
capability to interact with the Twitter service.

InfraRedDemo:

This is a simple application to demonstrate the use of the InfraRed 
capabilities on the new rev8 Sun SPOT. Turns your SPOT into a remote
volume control for your tv.

IPv6:

Several demos using the new IP version 6 protocl to transmit information
over the radio.

iRobotCreateDemo:

A simple demo to bounce an emulated Create robot off the walls in the
Robot View in Solarium.

RadioStrength:

A simple application for 2 SPOTs. Each SPOT broadcasts 5 packets per
second and listens for radio broadcasts from the other SPOT. The radio
signal strength of the packets received is displayed in the SPOT's LEDs.

SendDataDemo:

This demo application sends periodic sensor readings measured on one
or more Sun SPOTs to an application on your laptop or PC that displays
the values.

SensorDotNetworkDemo:

This demo is an extension of the SendDataDemo. It sends periodic 
sensor readings from one or more Sun SPOTs to a web-based service
called sensor.network where that data can be easily searched, 
shared, visualized and analyzed.

Using this demo requires an account on sensor.network. Please 
visit http://sensor.network.com/ to sign up and/or learn more about 
the service.

SolariumExtensionDemo:

This demo shows two ways to extend the Solarium application. The first,
VirtualObjectPlugin, modifies Solarium to display a SPOT that is running 
the SendDataDemo with a unique icon and with a modified popup menu that 
includes displaying the data being collected within Solarium. The second,
NewViewPlugin, shows how to add a new view to Solarium and also how to 
extend the Emulator to work with the new view.

SPOTWebDemo:

This demo is a host application that starts up a simple web 
server and lets remote users interact with a collection of SPOTs
in the vicinty of the attached basestation using a standard
web browser. Authorized remote users can monitor the state of sensors, 
applications and other system statistics. They can also install, start,
pause, resume, stop and remove applications.

TelemetryDemo:

This is a demo application that provides the basic framework for
collecting data on a remote SPOT and passing it over the radio to 
a host application that can record and display it.

YggdrasilDemo:

This demo uses the Yggdrasil library ( http://yggdrasil.dev.java.net ) to 
provide an easy way to get sensor data from a Sun SPOT to a host. Yggdrasil
provides support for basic Wireless Sensor Network applications, where the nodes
are able to sleep and mesh. Yggdrasil also supports a simple mechanism of sending
the data through the basestation to http://sensor.network.com

There is also a CodeSamples subdirectory containing simple examples of
how to use the radio and some of the basic sensors.


WebOfThings:

The Web of Things is a vision where everyday devices (like home 
appliances, health and energy monitoring devices, sensors etc) are fully 
integrated into the Web by reusing the its architectural styles (REST), 
and well-known technologies (URL, HTTP).

This directory contains a collection of demos implementing parts of this
vision on the Sun SPOTs.

