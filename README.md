# Siebel Connector for WSO2 ESB 4.8

Siebel Connector provides connectivity between the Siebel CRM and other applications and services. Users can call any business method on Siebel. The Siebel Connector helps users to synchronize or migrate data between Siebel CRM and other platforms such as IBM Domino, Microsoft Exchange, SaleForce.

The following sections describe how to perform basic operations with the connector. For general information on using connectors and their operations in your ESB configurations, see [Using a Connector](https://docs.wso2.com/display/ESB481/Using+a+Connector).

[Connecting to Siebel](#connecting -to-siebel)		
[Call method with encoded string payload](#call-method-with-encoded-string-payload)  
[Call method with XML payload](#call-method-with-xml-payload) 
[Logoff from Siebel](#logoff-from-siebel)
[Example Scenario](#example-scenario)
[ESB configuration](#esb-onfiguration)
[Simulating the sample scenario](#simulating-the-sample-scenario)

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

To invoke a method passing encoded string payload, use siebel.invokeMethod with the following properties.

      <siebel.invokeMethod>
            <serviceName>Workflow Utilities</serviceName>
            <methodName>Echo</methodName>
            <converterType>string</converterType>
            <propertySet>@0*0*2*2*11*PropertySet3*4*Body11*Attribute 123*Calendar and Activities8*number#11*N2*0*10*Date range3*0*8*EndDates19*2011-07-14 12:11:1110*StartDates19*2011-07-14 11:11:110*0*10*Attachment2*4*VEVTVA==</propertySet>
      </siebel.invokeMethod>

- **serviceName**: The name of Siebel service.
- **converterType**: A converter type [string].
- **methodName**: The name of service method.

### Call method with XML payload

To invoke a method passing XML palyload, siebel.invokeMethod with the following properties.

    <siebel.invokeMethod>
        <serviceName>Workflow Utilities</serviceName>
        <methodName>Echo</methodName>
        <converterType>xml</converterType>
        <propertySet>&lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;?Siebel-Property-SetEscapeNames="true"?&gt;&lt;PropertySet Attribute_spc1="Calendar and Activities" number_pnd1="N"&gt;&lt;Date_spcrange EndDates="2014-12-12 12:11:11" StartDates="2011-07-14 11:11:11"/&gt;&lt;Attachment sblValueVariant="CCFVT_MEMBLOCK"&gt;VEVTVA==&lt;/Attachment&gt;&lt;/PropertySet&gt;</propertySet>
      </siebel.invokeMethod>

- **serviceName**: The name of Siebel service.
- **converterType**: A converter type [xml].
- **methodName**: The name of service method.

### Logoff from Siebel

To logoff from Siebel, add the <siebel.init> element in your configuration.

    <siebel.logoff/>
    
### Example scenario

This example demonstrates WSO2 ESB's Siebel connector transferring a message triggred by a stock quote client and then sends the response that comes from Siebel back to the client.

### ESB configuration
- Add the connector to ESB instance, see [Managing Connectors in Your ESB Instance ](https://docs.wso2.com/display/ESB481/Managing+Connectors+in+Your+ESB+Instance).
- Start the ESB server and log into its management console UI [https: //localhost:9443/carbon](https://localhost:9443/carbon).
- In the management console, navigate to the **Main** menu and click **Source View** in the **Service Bus** section.
- Copy and paste the following configuration, which helps you explore the example scenario, to the source view.

Replace the Siebel credentials used in the configuration below with valid credentials.

    <?xml version="1.0" encoding="UTF-8"?>
    <definitions xmlns="http://ws.apache.org/ns/synapse">
        <import name="siebel" package="org.wso2.carbon.connectors" status="enabled"/>
        <sequence name="fault">
          <log level="full">
             <property name="MESSAGE" value="Executing default &#34;fault&#34; sequence"/>
             <property name="ERROR_CODE" expression="get-property('ERROR_CODE')"/>
             <property name="ERROR_MESSAGE" expression="get-property('ERROR_MESSAGE')"/>
          </log>
          <drop/>
        </sequence>
        <sequence name="MessageChannel">
          <siebel.init>
             <username>xxx</username>
             <password>xxx</password>
             <url>siebel://xxx:2321/SBA_80/ESEObjMgr_enu</url>
          </siebel.init>
          <siebel.invokeMethod>
             <serviceName>Workflow Utilities</serviceName>
             <methodName>Echo</methodName>
             <converterType>xml</converterType>
             <propertySet>&lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;?Siebel-Property-Set EscapeNames="true"?&gt;&lt;PropertySet Attribute_spc1="Calendar and Activities" number_pnd1="N"&gt;
                   &lt;Date_spcrange EndDates="2014-12-12 12:11:11" StartDates="2011-07-14 11:11:11"/&gt;
                   &lt;Attachment sblValueVariant="CCFVT_MEMBLOCK"&gt;VEVTVA==&lt;/Attachment&gt;
                &lt;/PropertySet&gt;</propertySet>
             </siebel.invokeMethod>
            <siebel.logoff/>
            <respond/>
        </sequence>
        <sequence name="main">
            <in>
                <sequence key="MessageChannel"/>
            </in>
            <out>
                <send/>
            </out>
        </sequence>
    </definitions>
    
### Simulating the sample scenario

Send a request as follows using the Stock Quote Client to WSO2 ESB.

    ant stockquote -Dtrpurl=http://localhost:8280

This command executes the connector, which simulates sending a request to Siebel and sends the response from Siebel back to the client.
