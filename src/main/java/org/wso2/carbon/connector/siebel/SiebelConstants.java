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

/**
 * Siebel Connector constants.
 */
public class SiebelConstants {

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String URL = "url";

    public static final String SIEBEL_USERNAME = "siebel.username";
    public static final String SIEBEL_PASSWORD = "siebel.password";
    public static final String SIEBEL_URL = "siebel.url";
    public static final String SIEBEL_AUTH_INSTANCE = "SIEBEL_AUTH_INSTANCE";

    public static final String SERVICE_NAME = "serviceName";
    public static final String METHOD_NAME = "methodName";
    public static final String CONVERTER_TYPE = "converterType";
    public static final String PROPERTY_SET = "propertySet";

    public static final String XML_CONVERTER_TYPE = "xml";
    public static final String STRING_CONVERTER_TYPE = "string";

    public static final String[] CONVERTER_TYPES = { XML_CONVERTER_TYPE, STRING_CONVERTER_TYPE };
}
