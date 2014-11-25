/*
 *  Copyright (c) 2014, Arbuz LLC (http://www.arbuzworks.com) All Rights Reserved.
 *
 *  Arbuz LLC licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.connector.siebel;

import com.siebel.data.SiebelDataBean;
import com.siebel.data.SiebelPropertySet;
import com.siebel.data.SiebelService;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.synapse.MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

/**
 * Invokes a method on Siebel Service.
 */
public class SiebelInvokeMehod extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        String serviceName = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.SERVICE_NAME);
        String methodName = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.METHOD_NAME);
        String converterType = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.CONVERTER_TYPE);
        String propertySet = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.PROPERTY_SET);

        if (converterType == null || "".equals(converterType.trim())) {
            ConnectException connectException = new ConnectException("Missing parameter converterType " + "[" + SiebelConstants.STRING_CONVERTER_TYPE + ", " + SiebelConstants.XML_CONVERTER_TYPE + "]");
            SiebelUtils.storeErrorResponseStatus(messageContext, connectException);
            return;
        }

        if (!isValidConverterType(converterType)) {
            ConnectException connectException = new ConnectException("Invalid converterType. Valid types " + "[" + SiebelConstants.STRING_CONVERTER_TYPE + ", " + SiebelConstants.XML_CONVERTER_TYPE + "]");
            SiebelUtils.storeErrorResponseStatus(messageContext, connectException);
            return;
        }

        if (serviceName == null || "".equals(serviceName.trim())) {
            ConnectException connectException = new ConnectException("Missing parameter serviceName");
            SiebelUtils.storeErrorResponseStatus(messageContext, connectException);
            return;
        }

        if (methodName == null || "".equals(methodName.trim())) {
            ConnectException connectException = new ConnectException("Missing parameter methodName");
            SiebelUtils.storeErrorResponseStatus(messageContext, connectException);
            return;
        }

        if (propertySet == null || "".equals(propertySet.trim())) {
            ConnectException connectException = new ConnectException("Missing parameter propertySet");
            SiebelUtils.storeErrorResponseStatus(messageContext, connectException);
            return;
        }

        try {
            SiebelDataBean siebelDataBean = new SiebelClientLoader(messageContext).loadSiebelDataBean();

            SiebelService siebelService = siebelDataBean.getService(serviceName);

            if (log.isDebugEnabled()) {
                log.debug("Referenced Siebel Service: " + siebelService.getName());
            }

            SiebelPropertySet output = new SiebelPropertySet();
            SiebelPropertySet input =  new SiebelPropertySet();
            String result;

            if (converterType.equals(SiebelConstants.STRING_CONVERTER_TYPE)) {
                input.setValue(propertySet);
                siebelService.invokeMethod(methodName, input, output);
                result = output.encodeAsString();
            } else {
                SiebelService xmlConverterService = siebelDataBean.getService("XML Converter");
                input = new SiebelPropertySet();
                input.setByteValue(propertySet.getBytes("UTF-8"));
                SiebelPropertySet outputPropertySet = new SiebelPropertySet();
                xmlConverterService.invokeMethod("XMLToPropSet", input, outputPropertySet);
                siebelService.invokeMethod(methodName, output, outputPropertySet);
                xmlConverterService.invokeMethod("PropSetToXML", outputPropertySet, output);
                result = new String(output.getByteValue(), "UTF-8");
            }

            if (log.isDebugEnabled()) {
                log.debug("Building " + converterType + " response: " + result);
            }

            if (messageContext.getEnvelope().getBody().getFirstElement() != null) {
                messageContext.getEnvelope().getBody().getFirstElement().detach();
            }

            OMFactory factory = OMAbstractFactory.getOMFactory();
            OMNamespace ns = factory.createOMNamespace("http://org.wso2.esbconnectors.siebel", "ns");
            OMElement invokeMehtodResult = factory.createOMElement("invokeMethodResult", ns);
            OMElement resultElement = factory.createOMElement("result", ns);
            invokeMehtodResult.addChild(resultElement);
            resultElement.setText(result);
            messageContext.getEnvelope().getBody().addChild(invokeMehtodResult);
        } catch(Exception e) {
            log.error("Error invoking " + methodName + " on service " + serviceName + ":" + e.getMessage(), e);
            SiebelUtils.storeErrorResponseStatus(messageContext, e);
        }

    }

    private boolean isValidConverterType(String type) {
        for (String validType : SiebelConstants.CONVERTER_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
