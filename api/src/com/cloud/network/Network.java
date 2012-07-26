// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
package com.cloud.network;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cloud.acl.ControlledEntity;
import com.cloud.network.Networks.BroadcastDomainType;
import com.cloud.network.Networks.Mode;
import com.cloud.network.Networks.TrafficType;
import com.cloud.utils.fsm.FiniteState;
import com.cloud.utils.fsm.StateMachine;

/**
 * owned by an account.
 */
public interface Network extends ControlledEntity {

    public enum GuestType {
        Shared,
        Isolated
    }

    public static class Service {
        private static List<Service> supportedServices = new ArrayList<Service>();

        public static final Service Vpn = new Service("Vpn", Capability.SupportedVpnTypes);
        public static final Service Dhcp = new Service("Dhcp");
        public static final Service Dns = new Service("Dns", Capability.AllowDnsSuffixModification);
        public static final Service Gateway = new Service("Gateway");
        public static final Service Firewall = new Service("Firewall", Capability.SupportedProtocols, Capability.MultipleIps, Capability.TrafficStatistics);
        public static final Service Lb = new Service("Lb", Capability.SupportedLBAlgorithms, Capability.SupportedLBIsolation, Capability.SupportedProtocols, Capability.TrafficStatistics, Capability.LoadBalancingSupportedIps, Capability.SupportedStickinessMethods, Capability.ElasticLb);
        public static final Service UserData = new Service("UserData");
        public static final Service SourceNat = new Service("SourceNat", Capability.SupportedSourceNatTypes, Capability.RedundantRouter);
        public static final Service StaticNat = new Service("StaticNat", Capability.ElasticIp);
        public static final Service PortForwarding = new Service("PortForwarding");
        public static final Service SecurityGroup = new Service("SecurityGroup");
        public static final Service Connectivity = new Service("Connectivity");


        private String name;
        private Capability[] caps;

        public Service(String name, Capability... caps) {
            this.name = name;
            this.caps = caps;
            supportedServices.add(this);
        }

        public String getName() {
            return name;
        }

        public Capability[] getCapabilities() {
            return caps;
        }

        public boolean containsCapability(Capability cap) {
            boolean success = false;
            if (caps != null) {
                int length = caps.length;
                for (int i = 0; i< length; i++) {
                    if (caps[i].getName().equalsIgnoreCase(cap.getName())) {
                        success = true;
                        break;
                    }
                }
            }

            return success;
        }

        public static Service getService(String serviceName) {
            for (Service service : supportedServices) {
                if (service.getName().equalsIgnoreCase(serviceName)) {
                    return service;
                }
            }
            return null;
        }

        public static List<Service> listAllServices(){
            return supportedServices;
        }
    }

    /**
     * Provider -> NetworkElement must always be one-to-one mapping. Thus for each NetworkElement we need a separate Provider added in here.
     */
    public static class Provider {
        private static List<Provider> supportedProviders = new ArrayList<Provider>();

        public static final Provider VirtualRouter = new Provider("VirtualRouter", false);
        public static final Provider JuniperSRX = new Provider("JuniperSRX", true);
        public static final Provider F5BigIp = new Provider("F5BigIp", true);
        public static final Provider Netscaler = new Provider("Netscaler", true);
        public static final Provider ExternalDhcpServer = new Provider("ExternalDhcpServer", true);
        public static final Provider ExternalGateWay = new Provider("ExternalGateWay", true);
        public static final Provider ElasticLoadBalancerVm = new Provider("ElasticLoadBalancerVm", false);
        public static final Provider SecurityGroupProvider = new Provider("SecurityGroupProvider", false);
        public static final Provider None = new Provider("None", false);
        public static final Provider NiciraNvp = new Provider("NiciraNvp", true);

        private String name;
        private boolean isExternal;

        public Provider(String name, boolean isExternal) {
            this.name = name;
            this.isExternal = isExternal;
            supportedProviders.add(this);
        }

        public String getName() {
            return name;
        }

        public boolean isExternal() {
            return isExternal;
        }

        public static Provider getProvider(String providerName) {
            for (Provider provider : supportedProviders) {
                if (provider.getName().equalsIgnoreCase(providerName)) {
                    return provider;
                }
            }
            return null;
        }
    }

    public static class Capability {

        private static List<Capability> supportedCapabilities = new ArrayList<Capability>();

        public static final Capability SupportedProtocols = new Capability("SupportedProtocols");
        public static final Capability SupportedLBAlgorithms = new Capability("SupportedLbAlgorithms");
        public static final Capability SupportedLBIsolation = new Capability("SupportedLBIsolation");
        public static final Capability SupportedStickinessMethods = new Capability("SupportedStickinessMethods");
        public static final Capability MultipleIps = new Capability("MultipleIps");
        public static final Capability SupportedSourceNatTypes = new Capability("SupportedSourceNatTypes");
        public static final Capability SupportedVpnTypes = new Capability("SupportedVpnTypes");
        public static final Capability TrafficStatistics = new Capability("TrafficStatistics");
        public static final Capability LoadBalancingSupportedIps = new Capability("LoadBalancingSupportedIps");
        public static final Capability AllowDnsSuffixModification = new Capability("AllowDnsSuffixModification");
        public static final Capability RedundantRouter = new Capability("RedundantRouter");
        public static final Capability ElasticIp = new Capability("ElasticIp");
        public static final Capability ElasticLb = new Capability("ElasticLb");
        public static final Capability AutoScaleCounters = new Capability("AutoScaleCounters");

        private String name;

        public Capability(String name) {
            this.name = name;
            supportedCapabilities.add(this);
        }

        public String getName() {
            return name;
        }

        public static Capability getCapability(String capabilityName) {
            for (Capability capability : supportedCapabilities) {
                if (capability.getName().equalsIgnoreCase(capabilityName)) {
                    return capability;
                }
            }
            return null;
        }
    }

    enum Event {
        ImplementNetwork,
        DestroyNetwork,
        OperationSucceeded,
        OperationFailed;
    }

    enum State implements FiniteState<State, Event> {
        Allocated("Indicates the network configuration is in allocated but not setup"),
        Setup("Indicates the network configuration is setup"),
        Implementing("Indicates the network configuration is being implemented"),
        Implemented("Indicates the network configuration is in use"),
        Shutdown("Indicates the network configuration is being destroyed"),
        Destroy("Indicates that the network is destroyed");


        @Override
        public StateMachine<State, Event> getStateMachine() {
            return s_fsm;
        }

        @Override
        public State getNextState(Event event) {
            return s_fsm.getNextState(this, event);
        }

        @Override
        public List<State> getFromStates(Event event) {
            return s_fsm.getFromStates(this, event);
        }

        @Override
        public Set<Event> getPossibleEvents() {
            return s_fsm.getPossibleEvents(this);
        }

        String _description;

        @Override
        public String getDescription() {
            return _description;
        }

        private State(String description) {
            _description = description;
        }

        private static StateMachine<State, Event> s_fsm = new StateMachine<State, Event>();
        static {
            s_fsm.addTransition(State.Allocated, Event.ImplementNetwork, State.Implementing);
            s_fsm.addTransition(State.Implementing, Event.OperationSucceeded, State.Implemented);
            s_fsm.addTransition(State.Implementing, Event.OperationFailed, State.Shutdown);
            s_fsm.addTransition(State.Implemented, Event.DestroyNetwork, State.Shutdown);
            s_fsm.addTransition(State.Shutdown, Event.OperationSucceeded, State.Allocated);
            s_fsm.addTransition(State.Shutdown, Event.OperationFailed, State.Implemented);
        }
    }

    /**
     * @return id of the network profile.  Null means the network profile is not from the database.
     */
    long getId();

    String getName();

    Mode getMode();

    BroadcastDomainType getBroadcastDomainType();

    TrafficType getTrafficType();

    String getGateway();

    String getCidr();

    long getDataCenterId();

    long getNetworkOfferingId();

    State getState();

    long getRelated();

    URI getBroadcastUri();

    String getDisplayText();

    String getReservationId();

    String getNetworkDomain();

    GuestType getGuestType();

    Long getPhysicalNetworkId();

    void setPhysicalNetworkId(Long physicalNetworkId);

    ACLType getAclType();

    boolean isRestartRequired();

    boolean getSpecifyIpRanges();
}
