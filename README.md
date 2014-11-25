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

- **username**: Siebel account username.
- **password**: Siebel accout password.
- **url**: connection string.

### Call method with encoded string payload

To invoke a method passing encoded string payload, use siebel.invokeMethod with the following properties:

      <siebel.invokeMethod>
            <serviceName>Workflow Utilities</serviceName>
            <methodName>Echo</methodName>
            <converterType>string</converterType>
            <propertySet>@0*0*2*2*11*PropertySet3*4*Body11*Attribute 123*Calendar and Activities8*number#11*N2*0*10*Date range3*0*8*EndDates19*2011-07-14 12:11:1110*StartDates19*2011-07-14 11:11:110*0*10*Attachment2*4*VEVTVA==</propertySet>
      </siebel.invokeMethod>

- **serviceName**: The name of Siebel service.
- **converterType**: A convertert type [string].
- **methodName**: The name of service method.

### Call method with XML payload

To invoke a method passing XML palyload, siebel.invokeMethod with the following properties:

    </siebel.init>
        <siebel.invokeMethod>
        <serviceName>Workflow Utilities</serviceName>
        <methodName>Echo</methodName>
        <converterType>xml</converterType>
        <propertySet>&lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;?Siebel-Property-SetEscapeNames="true"?&gt;&lt;PropertySet Attribute_spc1="Calendar and Activities" number_pnd1="N"&gt;&lt;Date_spcrange EndDates="2014-12-12 12:11:11" StartDates="2011-07-14 11:11:11"/&gt;&lt;Attachment sblValueVariant="CCFVT_MEMBLOCK"&gt;VEVTVA==&lt;/Attachment&gt;&lt;/PropertySet&gt;</propertySet>
      </siebel.invokeMethod>

- **serviceName**: The name of Siebel service.
- **converterType**: A convertert type [xml].
- **methodName**: The name of service method.
