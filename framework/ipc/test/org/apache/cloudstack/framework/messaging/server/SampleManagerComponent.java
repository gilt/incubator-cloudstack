/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.cloudstack.framework.messaging.server;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.cloudstack.framework.messaging.EventBus;
import org.apache.cloudstack.framework.messaging.EventDispatcher;
import org.apache.cloudstack.framework.messaging.EventHandler;
import org.apache.cloudstack.framework.messaging.RpcProvider;
import org.apache.cloudstack.framework.messaging.RpcServerCall;
import org.apache.cloudstack.framework.messaging.RpcServiceDispatcher;
import org.apache.cloudstack.framework.messaging.RpcServiceHandler;
import org.springframework.stereotype.Component;

@Component
public class SampleManagerComponent {
	
	@Inject
	private EventBus _eventBus;
 	
	@Inject
	private RpcProvider _rpcProvider;
	
	public SampleManagerComponent() {
	}
	
	@PostConstruct
	public void init() {
		_rpcProvider.registerRpcServiceEndpoint(
			RpcServiceDispatcher.getDispatcher(this));
			
		// subscribe to all network events (for example)
		_eventBus.subscribe("network", 
			EventDispatcher.getDispatcher(this));
	}
	
	@RpcServiceHandler(command="NetworkPrepare")
	void onStartCommand(RpcServerCall call) {
		call.completeCall("NetworkPrepare completed");
	}
	
	@EventHandler(topic="network.prepare")
	void onPrepareNetwork(String sender, String topic, Object args) {
	}
}