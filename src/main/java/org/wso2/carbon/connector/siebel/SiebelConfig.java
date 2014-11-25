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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

/**
 * Saves Siebel configuration.
 */
public class SiebelConfig extends AbstractConnector {

    private static Log log = LogFactory.getLog(SiebelConfig.class);

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        try {
            org.apache.axis2.context.MessageContext axis2MsgCtx = ((Axis2MessageContext) messageContext).getAxis2MessageContext();
            axis2MsgCtx.getOperationContext().removeProperty(SiebelConstants.SIEBEL_AUTH_INSTANCE);

            String username = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.USERNAME);
            String password = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.PASSWORD);
            String url = SiebelUtils.lookupFunctionParam(messageContext, SiebelConstants.URL);

            if (username == null || "".equals(username.trim()) ||
                    password == null || "".equals(password.trim()) ||
                    url == null || "".equals(url.trim())) {
                ConnectException connectException = new ConnectException("Input username or password");
                SiebelUtils.storeErrorResponseStatus(messageContext, connectException);
                return;
            }

            SiebelUtils.storeConfig(messageContext, username, password, url);
        } catch (Exception e) {
            log.error("Failed to login user: " + e.getMessage(), e);
            SiebelUtils.storeErrorResponseStatus(messageContext, e);
        }
    }
}
