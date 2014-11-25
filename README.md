# Siebel Connector for WSO2 ESB 4.8

Siebel Connector provides connectivity between the Siebel CRM and other applications and services. Users can call any business method on Siebel. The Siebel Connector helps users to synchronize or migrate data between Siebel CRM and other platforms such as IBM Domino, Microsoft Exchange, SaleForce.

The following sections describe how to perform basic operations with the connector. For general information on using connectors and their operations in your ESB configurations, see [Using a Connector](https://docs.wso2.com/display/ESB481/Using+a+Connector).

[Connecting to Siebel](#connecting -to-siebel)		
[Call method with encoded string payload](#call-method-with-encoded-string-payload)  
[Call method with XML payload](#call-method-with-xml-payload) 

### Connecting to Siebel
To use the Siebel connector, add the <siebel.init> element in your configuration before any other Siebel connector operations. This configuration authenticates with Siebel by configuring the user credentials and login URL.

    <siebel.init>
        <username>xxx</username>
        <password>xxx</password>
        <url>siebel://xxx:2321/SBA_80/ESEObjMgr_enu</url>
    </siebel.init>

### Call method with encoded string payload

To create a spreadsheet, use siebel.invokeMethod and specify the following properties:

      <siebel.invokeMethod>
            <serviceName>Workflow Utilities</serviceName>
            <methodName>Echo</methodName>
            <converterType>string</converterType>
            <propertySet>@0*0*2*2*11*PropertySet3*4*Body11*Attribute 123*Calendar and Activities8*number#11*N2*0*10*Date range3*0*8*EndDates19*2011-07-14 12:11:1110*StartDates19*2011-07-14 11:11:110*0*10*Attachment2*4*VEVTVA==</propertySet>
      </siebel.invokeMethod>
      
### Call method with XML payload
