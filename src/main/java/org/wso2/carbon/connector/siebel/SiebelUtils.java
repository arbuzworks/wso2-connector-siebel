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

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.wso2.carbon.connector.core.util.ConnectorUtils;

/**
 * Siebel Utilities.
 */
public class SiebelUtils {
    protected static Log log = LogFactory.getLog(SiebelUtils.class);

    public static String lookupFunctionParam(MessageContext ctxt, String paramName) {
        return (String) ConnectorUtils.lookupTemplateParamater(ctxt, paramName);
    }

    public static void storeConfig(MessageContext ctxt, String userName, String password, String url) {
        ctxt.setProperty(SiebelConstants.SIEBEL_USERNAME, userName);
        ctxt.setProperty(SiebelConstants.SIEBEL_PASSWORD, password);
        ctxt.setProperty(SiebelConstants.SIEBEL_URL, url);
    }

    public static void storeErrorResponseStatus(MessageContext ctxt, Exception e) {
        log.error(e.getMessage());

        ctxt.setProperty(SynapseConstants.ERROR_EXCEPTION, e);
        ctxt.setProperty(SynapseConstants.ERROR_MESSAGE, e.getMessage());

        if (ctxt.getEnvelope().getBody().getFirstElement() != null) {
            ctxt.getEnvelope().getBody().getFirstElement().detach();
        }

        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace ns = factory.createOMNamespace("http://org.wso2.esbconnectors.siebel", "ns");
        OMElement searchResult = factory.createOMElement("ErrorResponse", ns);
        OMElement errorMessage = factory.createOMElement("ErrorMessage", ns);
        searchResult.addChild(errorMessage);
        errorMessage.setText(e.getMessage());
        ctxt.getEnvelope().getBody().addChild(searchResult);
    }
}
