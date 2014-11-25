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
import com.siebel.data.SiebelException;
import org.apache.synapse.MessageContext;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.wso2.carbon.connector.core.AbstractConnector;
import org.wso2.carbon.connector.core.ConnectException;

/**
 * Logoffs from Siebel.
 */
public class SiebelLogoff extends AbstractConnector {

    @Override
    public void connect(MessageContext messageContext) throws ConnectException {
        org.apache.axis2.context.MessageContext axis2MsgCtx = ((Axis2MessageContext) messageContext).getAxis2MessageContext();

        SiebelDataBean siebelDataBean = (SiebelDataBean)axis2MsgCtx.getOperationContext().getProperty(SiebelConstants.SIEBEL_AUTH_INSTANCE);

        if (siebelDataBean != null) {
            try {
                siebelDataBean.logoff();
            } catch(SiebelException se) {
                log.error("Failed to disconnect: " + se.getMessage(), se);
                SiebelUtils.storeErrorResponseStatus(messageContext, se);
            }
        }
    }
}
