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

/**
 * Loads SiebelDataBean.
 */
public class SiebelClientLoader {

    private MessageContext messageContext;

    public SiebelClientLoader(MessageContext ctxt) {
        this.messageContext = ctxt;
    }

    public SiebelDataBean loadSiebelDataBean() throws SiebelException {
        org.apache.axis2.context.MessageContext axis2MsgCtx =
                ((Axis2MessageContext) messageContext).getAxis2MessageContext();

        SiebelDataBean siebelDataBean = null;

        if (axis2MsgCtx.getOperationContext().getProperty(SiebelConstants.SIEBEL_AUTH_INSTANCE) != null) {
            siebelDataBean = (SiebelDataBean) axis2MsgCtx.getOperationContext()
                            .getProperty(SiebelConstants.SIEBEL_AUTH_INSTANCE);
            return siebelDataBean;
        }

        if (messageContext.getProperty(SiebelConstants.SIEBEL_USERNAME) != null &&
                messageContext.getProperty(SiebelConstants.SIEBEL_PASSWORD) != null &&
                messageContext.getProperty(SiebelConstants.SIEBEL_URL) != null) {
            siebelDataBean = new SiebelDataBean();

            siebelDataBean.login(messageContext.getProperty(SiebelConstants.SIEBEL_URL).toString(),
                    messageContext.getProperty(SiebelConstants.SIEBEL_USERNAME).toString(),
                    messageContext.getProperty(SiebelConstants.SIEBEL_PASSWORD).toString());
        }

        axis2MsgCtx.getOperationContext().setProperty(SiebelConstants.SIEBEL_AUTH_INSTANCE, siebelDataBean);

        return siebelDataBean;
    }

}
