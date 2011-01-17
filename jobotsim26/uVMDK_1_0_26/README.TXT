================================================================================
* For all technical questions regarding muvium please Post on the Support Group
* http://groups.yahoo.com/group/muvium 
================================================================================
uVMDK 1.0.26 August 15, 2006 

WHATS NEW:
  
  * Networking 
  	- com.muvium.net - Introduces the Ethernet TCP/IP Stack
  	- 'Practical Embedded Networking' Application Note
    
  * JAVADOC
  	- First cut at collating the muvium libraries into a single java doc
      	   
   * CORE
  	- PerformanceProfile for up to 500% performace improvement  
  
Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com
================================================================================
uVMDK 1.0.25 June 3, 2006 

WHATS NEW:
  
  * APT
  - com.muvium.apt.SerialPort
      	+ SerialPortVirtualImpl ( Reintegrated )
  - com.muvium.apt.MultiServoController
  		+ MultiServoControllerPWMImpl ( Reintegrated )
  - com.muvium.apt.PinMonitor
  		+ Added debounce feature to PinMonitor
  - com.muvium.apt.PeriodicTimer
  		+ Improved accuracy of PeriodicTimer for 32Mhz
  - com.muvium.apt.ADCReader
  		+ Corrected the TAD calculator for the ADCReader for wider range of frequencies
  		+ Added setConfig to ADCReader to enable runtime configuration of ADC pins
  	
   * CORE
  - Enhanced performance of Static and field accessors
  - Created PerformanceProfile for up to 500% performace improvement ( available next release )
  
 
Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com
================================================================================
 

uVMDK 1.0.23 May 19, 2006 

WHATS NEW:
  
  * APT
  - com.muvium.apt.PinMonitor

  * CORE
  - Pre-emptive Multi-tasking BugFixes
  - java.lang.Object
      - toString()
  
  * IDE
  - Added MuviumOS Installer and Developer Certificate
  
================================================================================
uVMDK 1.0.22 April 25, 2006 

WHATS NEW:
  
  * APT
  - com.muvium.apt.PulseTrainGenerator

  * CORE
  - Improved several legacy components to 16-bit lengths
  	- String.getBytes()
  	- new String(byte[] )
  - Improved performance of several bytecodes
  
   
Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================
uVMDK 1.0.21 April 6, 2006 

WHATS NEW:
  
  * APT
  - com.muvium.apt.PortMonitor

  * EXAMPLES   
  - com.muvium.examples.apt.portMonitor.PortMonitorDemo
  - com.muvium.examples.io.EEPROMStoreDemo

  * UVMDK
  - com.muvium.io.EEPROMDataStore

  * FRAMEWORKS
  - jautomation.dev.java.net
   
Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================
uVMDK 1.0.20 March 3, 2006 

WHATS NEW:
  
  * TOOLS
  - IDE now brings Bootloader into Configuration List
  
  * GUI 
  - com.muvium.gui.ListBox
  - com.util.StringList
  
  * DEBUGGER
  - Fixed the debugger 
  
  * BAT FILES
  - Repaired paths of InstallMuviumOS, uvmp18 and other batch files
   
Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================
uVMDK 1.0.19 Jan 26, 2006 

WHATS NEW:
  
  * APT 	
  - com.muvium.apt.FrequencyGenerator
  - com.muvium.apt.RealTimeClock
  
  * GUI 
  - com.muvium.gui.Menu
  - com.muvium.gui.ScrollPane
  - com.muvium.gui.WikiView
  - com.muvium.gui.ComboBox
  - Support for ColorBitmaps
 
   * Visij
   - RoboPAL support classes
   
   * Compatibility
   - OOPIC compatibile components
   
   * Examples
   - com.muvium.example.apt.FrequencyGenDemo
   - com.muvium.example.apt.DemoRealTimeClock
   - com.muvium.example.drivers.AgilantOpticalMouse
   
   * Build now uses ANT
   
Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

uVMDK 1.0.18 September 23, 2005 

WHATS NEW:
  
* LINUX Support
  - Compatibility issues with linux have been resolved allowing the IDE application
    to be use with the linux operating sysem for packaging and programming muvium
    applications

* uVM-Studio In-Circuit Debugger
  - The In-circuit Debugger enables rich debugging of muvium applications directly
    in the target hardware. Can be lauched directly from the IDE or as a seperate
    application. Requires Microsoft.NET runtime, future version will be compatible
    with Mono .NET runtime for linux compatibility

Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

 
================================================================================
uVMDK 1.0.17 August 15, 2005 

WHATS NEW:
  
* APT 	
  - com.muvium.apt.PWM

* Frameworks

  - BouncyCastle Crypto 
	- Created limited support for bouncycastle cryto library
	- AESEnginE
  - WebServices RPC
	- Added support for byte[] return type
	- Extended lightweight RPC to support byte[]
  
* Examples
  - com.muvium.examples.apt.pwm.PWMDemo
  - com.muvium.examples.crypto.AESDemo
  - com.muvium.examples.web.protocolAdaptor


Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

 
================================================================================
uVMDK 1.0.16 August 4, 2005 

WHATS NEW:

* Debug Infrastructure
  - Support for for Incircuit Debugging
  
* Core 
  - Increased maximum array sizes to 16bit
  - Support for static initialiser
  - Substantial performance improvement for 18F family
  
* APT 	
  - com.muvium.apt.I2CMaster

* Examples
  - com.muvium.examples.apt.i2c.I2CDemo

* Frameworks
  - Added GUI framework

* Bug Fixes
  - switch Bug fixed

================================================================================
uVMDK 1.0.15 May 2, 2005 

Iterative release, major release shifted to 1.16

WHATS NEW:

* General BugFixes and packager enhancements 

* Enhanced Support to the following java base classes
  - String
  - Vector

* Added new helper classes
  - com.muvium.io.ByteDataStore
  - com.muvium.util.LinkedListIntStore
  - com.muvium.io.StringInputStream
  - com.muvium.io.FLASHRAW16InputStream

* Added Persistance Class	
  - com.muvium.io.EEPROMDataStore


================================================================================

uVMDK 1.0.14 March 2, 2005 

WHATS NEW:

* Enhanced Online Compiler with 300-500% performance improvement
  - Implemented SOAP protocol for improved performance and proxy/firewall penetration
  - Improved serialisation performance
  - Added Cache 
  - No significant difference between desktop and remote compilation

* Public Bootloader
  - Public bootloader for assimilating existing hardware with muvium technology
  - Assimilator integrated with Paypal system for instant fullfillment
  - 'Roll your muvium devices within minutes' using the public bootloader


================================================================================

uVMDK 1.0.13 Feb 2, 2005 

WHATS NEW:

* Eclipse Plugin - Added GenericPCB for developing for uVM-877A, uVM-452

* Fix for IDE which was preventing programming uVM-877A


================================================================================

uVMDK 1.0.12 Jan 29, 2005 

WHATS NEW:

* Elcipse Plugin Released
  - Eclipse is the industry leading java development environment. The Eclipse Plugin
    integrates the muvium programming and configuration tools along with the muvium
    emulation technology enabling virtual real-time application develoment for target
    hardware with seamless retargeting between virtual and real targets.
  - Released with 4 examples
	* HelloWorld - Basic HelloWorld example
	* ADCSampler - Samples the ADC channel and buttons
	* HttpServletServer - Create dynamic web pages with re-useable servlets
	* LightBlinker - Use XML Web Services for remote method invocation
  - Released with PowerPoint tutorial and application notes for getting started

* SlipSocketGateway
  - Created the SlipSocketGateway implementation for connecting ethernet to slip
  - Installation notes for installing SlipSocketGateway on TINI
  - Integrated the SlipSocketGateway into the IDE, ICD and uVM Emulator 

FIXES:

* SPIMaster
  - fixed a bug causing projects using SPIMaster with 18F projects to fail during compilation
  - Added 2 methods, read(), write() to com.muvium.apt.SPIMaster for half duplex operation

================================================================================

uVMDK 1.0.11 Jan 4, 2005 

* Welcome to 2005! Watch this space!

WHATS NEW:

* IDE
  - Added Most Recently Used feature
  - Added Compiler Server selection list

FIXES:

* uvmp
  - fixed a bug causing projects using StringBuffer not to remote compile correctly

Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================

uVMDK 1.0.10 November 10 , 2004 

'SerialPort' Release

WHATS NEW:

* Refactored the APT infrastructure to better handle multi-tasking and refactored 
  existing APTs' to utilise the new infrastructure

* Implemented the Access Method optimisation for faster method invokation of small
  methods reducing code size and improving performance for getters/setters etc.

* Implemented a new extensible SerialPort infrastructure similar to the javax.comm
  serialPort infrastructure but extensible with different types of serial peripherals
  including in this release
  
  - SerialPortUARTImpl multiChannel physical UART SerialPort for handling up to the
    maximum number of UART channels as available on the device asycnhronous
    serial communications up to 115bps
  
  - SerialPortVirtualImpl multichannel virtual UART SerialPort for handling up to 4
    simulataneous asynchronous serial communications channels up to 19.2 baud.

  - Other implementations to extend this SerialPort infrastructure will 
    include, SPI, I2C, USB, Ethernet, Can etc.

* Implement a slow speed, MasterSlave, ( Half Duplex ), SoftUART up to 2400bps which
  can operate on an arbitary pin
   
	- SoftUARTInputStream
	- SoftUARTOutputStream

* Added a new com.muvium.apt.Time interface and implementation to support high level
  access to currentTimeMicros and timebased synchronisation

* Added a new com.muvium.visij package to host the new Visij functionality and a new
  com.muvium.visij.japl package to host the Visij Java Abstract Proxy Libary which is
  a library of interoperable high level components which act as abstact proxies to devices
  attached remotely to the Visij container. Added illustrative reference classes
	
	- com.muvium.visij.VisijElement
	- com.muvium.visij.IntDataPipe
	- com.muvium.examples.visij.japl.roboBrix.RoboBrix
	- com.muvium.examples.visij.japl.roboBrix.Led10
	- com.muvium.examples.visij.roboBrix.RoboBrixRun

* Added new support classes
	- com.muvium.util.ByteFIFO
	- com.muvium.io.PinIO

* Added examples
	- com.muvium.examples.apt.serialPort.VirtualSerialPortDemo
	- com.muvium.examples.apt.time.TimeDemo
	- com.muvium.examples.io.SoftUARTOutputStreamDemo
	- com.muvium.examples.io.SoftUARTInputStreamDemo
	- com.muvium.examples.visij.roboBrix.RoboBrixRun

FIXES:

* uVMIDE
  - fixed a math bug that caused occasssional errors with long ( 32bit ) math

NOTES:

* The Congig.XML WebService Tag <IP-ADDRESS> was changed to the url ie www.muvium.com
  rather than a specific IP address. This is to assist in the transition to multiple
  distributed hosted webservice sites


SHOP:

* Switched to paypal for general ordering to simplify the ordering process
  - uVM-DEV-8720	


Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================

uVMDK 1.0.9 August 15 , 2004 'Interrim Release'


Business Developments:

Successfully took workshops at the Microchip MASTERS conference showing off
pre-release versions of the 1.0.9 release and Eclipse plugin tools

WHATS NEW:

* Added new ICD ( Incircuit Debugger) for programming and configuring applications
  - uVMICD

* Added new Plugin for Eclipse for integrated development
  - uvmEclipse

* Refactored and integrated Virtual Breadboard (openVBB) for more flexible 
  co-operationg with muvium

* Refactored the APT infrastructure to better handle multi-tasking and refactored 
  existing APTs' to utilise the new infrastructure

* Refacted the uVMJIT as a .NET Webservice and refactored the client to access
  the remote webService replacing the existing proprietry server protocols

* Added new commands to uvmp 
  - soft   -- SoftBoots the muvium device which allows the device to be set into
	      bootmode without the need for a manual reset

FIXES:

* uVMIDE
  - fixed a bug that causes some settings to be lost
  - fixed a bug that depended on the c:\uvmdk_1_0_8\ location

* PeriodicTimer
  - fixed stop and remoteTimerListener methods

SHOP:

* New development boards available
  - uVM-DEV-8720	
  - uVM-DEV-JPB	DIP

Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================

uVMDK 1.0.8 July 8, 2004 


Business Developments:

We are particpating in this years Microchip MASTER conference presenting a
workshop MUV-816 Visual Development Using the muvium JVM on the PicMicro.


WHATS NEW:

* Added new GUI for programming and configuring applications
  - uvmIDE

* Added Language support for
  - Static Methods and Classes
  - Inner Classes

* Added new APT periperals
  - com.muvium.apt.MultiServoControllerDirectImpl
		
* Updated uVMDKDoc


New Products available from shop.muvium.com

* new devices available
  - uVM-8720	TQFP
  - uVM-452	DIP


Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com

================================================================================

uVMDK 1.0.7 Oct 5, 2003


WHATS NEW:

* Support for Microchip PIC18F Architecture.
  - First 18F Device uVM-452 available now

* Support for Prioritised Pre-emptive Multi-Tasking
  - Event Handlers can be prioritised and pre-emptively interrupted 
    to support hard-realtime multi-tasking requirements

* Support for WebServices	
  - Remote Method Invocation over webServices
  - Support for Debugging over WebServices
  
* Support for Debugging Architecture
  - 16F devices support XML Debug Document Query over WebServices

* Support for Java Native Interface (JNI) 
  - JNIWizard makes it easy to integrate your own native PIC code 
    into muvium projects in device independent java wrappers

* Added support for java base packages
  - java.io.InputStream
  - java.lang.Integer

* Added new APT periperals
  - com.muvium.apt.MultiServoController

* Added new examples
  - com.muvium.examples.apt.servo.RemoteServo
  - com.muvium.examples.apt.servo.ServoRobot
  - com.muvium.examples.http.webServices.WebServicesDemo1
  - com.muvium.examples.preEmptive.PreEmptive1
  - com.muvium.examples.lang.IntegerClass
  - com.muvium.examples.io.InputStreams

* Added new application Notes
  - appNote1-RMI Remote Method Invocation
  - appNote2-JNI Java Native Interface Wizard
  
* Added new commands to uvmp 
  - debug  -- Queries the DebugPort for a Debug document
  - rmi    -- Invoke webservice methods 
  - http   -- Query the WebServer directly
  - stdout -- Send strings to the InputStream

* Added a new java Serial WebServices framework for remote access to uVM devices
  - com.muvium.net.protocol.*;
  - com.muvium.net.socketImpls.*;
  - com.muvium.web.services.protocols.*;
		
* Updated uVMDKDoc

New Products available from shop.muvium.com

* new devices available
  - uVM-877A	DIP40, PLCC, TQFP
  - uVM-876A	DIP28
  - uVM-452	DIP40, PLCC, TQFP

Regards,
James Caska
www.muvium.com
mailto:caska@muvium.com
mailto:caska@virtualbreadboard.com
================================================================================

uVMDK 1.0.6 May 6, 2003

WHATS NEW:
* Added 32 bit long datatype support
* Added support for base packages
  - java.util.Vector
  - java.util.Stack
  - java.util.Enumeration
* Added System.currentTimeMillis()
* Added examples
  - com.muvium.examples.lang.LongInt
  - com.muvium.examples.lang.SystemUtils
  - com.muvium.examples.util.TestStack
  - com.muvium.examples.util.TestVector
* Added ExecListener to fix build Issue
* Update uVMDKDoc
  - details on 32 bit long support
  - !documentation on supported java base packages


Regards,
James Caska
www.muvium.com
mailto:caska@virtualbreadboard.com
================================================================================

uVMDK 1.0.5 April 14, 2003

WHATS NEW:
* Renamed uVMRunnable to UVMRunnable as per java naming convention
* Renamed com.muvium.japl package to com.muvium.vware namespace
* Fixed minor build error with 1.0.4
* Removed password from active version of uVMDK
* Improved program allocation in uvmJIT
* Added a HTTP Header to output from com.muvium.examples.http.PortBServlet.demoServletWebServer

Regards,
James Caska
www.muvium.com
mailto:caska@virtualbreadboard.com
================================================================================

uVMDK 1.0.4 March 24, 2003


WHATS NEW:
* New Support Group! http://groups.yahoo.com/group/muvium 
* Added new sections to uVMDK.doc
  - Limitations, DataTypes, APT, JAPL, Roadmap, Index
* Added hardware schematic and DCE serial port description
* New APT Peripherals
  - com.muvium.apt.Pin
  - com.muvium.apt.Port
  - com.muvium.apt.SPIMaster
* New Japl Namespace with reference EEPROM japl driver
  - com.muvium.japl 
  - com.muvium.japl.eeprom
  - com.muvium.japl.eeprom.microchip.EE93LC66_SPI 
* New Example
  - com.muvium.examples.apt.SPIMaster.SPIMaster1


Regards,
James Caska
www.muvium.com
mailto:caska@virtualbreadboard.com
================================================================================


uVMDK 1.0.3 Feb 12, 2003
* uvmc parses the classpath correctly - removes ';' dependency 
* Dedicated Online Server! - Apologies for the disruption of service during
  deployment

Regards,
James Caska
www.muvium.com
mailto:caska@virtualbreadboard.com
================================================================================

uVMDK 1.0.2 Feb 12, 2003

NOTES!
* 1.0(364) bootloader users should ensure they update their bootloader to
  the new 1.1 version before using this release. Contact me for details.
NEW:
* Finalised bootloader firmware 1.1(256) - Previous bootloader did not fit
  into the 256 byte protected zone and it could be accidently corrupted.
* Removed persistance from Bootloader for simplified programming
* Updated uvmp with changes for the new bootloader
* Enhanced uvmp with retry capability for more robust programming
* Added new PulseMonitor Abstract Peripheral @see com.muvium.apt.PulseMonitor
* Added new example @see com.muvium.examples.apt.PulseMonitor.PulseMonitor1
* Bug Fixes 
* First Publically available Samples available for purchase.

Regards,
James Caska
www.muvium.com
mailto:caska@virtualbreadboard.com
================================================================================

uVMDK 1.0.1 Feb 2, 2003

NOTES:
* uVM-877A[364] must be running a 4Mhz Crystal and connected with 9600baud RS232
NEW:
* Improved the memory allocation algorithm of uVMJIT 
* Added \examples\com\muvium\examples\lang\Inheritance.uvm to the prebuilt files
* Placed uVMDK.zip on website for download at http://www.muvium.com/downloads/uVMDK.zip
* Added \docs\Creating a SLIP connection in Windows 2000
* Added \docs\Creating a SLIP connection in Windows XP
* Added how to use uvmp, uvmp with -cp classpath parameter
  
Regards,
James Caska
www.muvium.com

================================================================================

uVMDK 1.0 Jan 30, 2003
James Caska
www.muvium.com

Welcome,

This is the first step for muvium into the real world. 1.0 is a stake in the ground 
to display muvium's capabilities. The packages available in this first release are
quite limited but they are powerful and demonstate the key aspects of muvium. Many
more packages have already been written and are ready to move into the com.muvium
namespace. Many others are yet to be adopted and the prioritisation of these packages
will come largely from the users on demand.

It is important to note that muvium has limitations that are not yet well documented.
This will be particularly evident if you try to use java packages that have not been 
specially implemented. muvium is a highly resource constrained environment and unless
the package has been specially overridden it won't work. I recommend when exploring 
muvium you use an existing example as a starting point and make small changes as you
go. 

So much has had to come together to make uVMDK available, but now that it has, iterative
improvements including better documentation, examples, packages and bug fixing will
be available much faster.

I look forward to working with all of you who are as excited about java's future in
Embedded Real-Time Control and Internet Connectivity as I am. 

Regards,
James Caska

================================================================================

Recommended Setup
1. Create a new Directory c:\uVMDK
2. Unzip into c:\uVMDK to create the following structure

c:\uVMDK\api\ 		;muvium api, javadocs & source code
c:\uVMDK\docs\ 		;related docs including uVMDK.pdf
c:\uVMDK\examples\	;examples, javadocs, source and pre-built code
c:\uVMDK\default.xml	;default config file for loging onto remote compiler
c:\uVMDK\README.txt	;this document
c:\uVMDK\uvm.jar	;jar file containing muvium tools and examples

Read c:\uVMDK\docs\uVMDK.pdf and follow the QuickStart tutorial to get started programming

Good luck, and don't hesitate to contact me by email with support questions
while I get the support infrastructure established.

mailto:caska@virtualbreadboard.com


