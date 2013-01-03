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
package org.apache.cloudstack.engine.service.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.cloudstack.engine.datacenter.entity.api.ClusterEntity;
import org.apache.cloudstack.engine.datacenter.entity.api.PodEntity;
import org.apache.cloudstack.engine.datacenter.entity.api.PodEntityImpl;
import org.apache.cloudstack.engine.datacenter.entity.api.StorageEntity;
import org.apache.cloudstack.engine.datacenter.entity.api.ZoneEntity;
import org.apache.cloudstack.engine.datacenter.entity.api.ZoneEntityImpl;
import org.springframework.stereotype.Component;

import com.cloud.host.Host;
import com.cloud.host.Status;
import com.cloud.storage.StoragePool;

@Component
public class ProvisioningServiceImpl implements ProvisioningService {

    @Override
    public void deregisterStorage(String uuid) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deregisterZone() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deregisterPod() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deregisterCluster() {
        // TODO Auto-generated method stub

    }

    @Override
    public void deregisterHost() {
        // TODO Auto-generated method stub

    }

    @Override
    public void changeState(String type, String entity, Status state) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Host> listHosts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PodEntity> listPods() {
        List<PodEntity> pods = new ArrayList<PodEntity>();
        pods.add(new PodEntityImpl("pod-uuid-1", "pod1"));
        pods.add(new PodEntityImpl("pod-uuid-2", "pod2"));
        return null;
    }

    @Override
    public List<ZoneEntity> listZones() {
        List<ZoneEntity> zones = new ArrayList<ZoneEntity>();
        zones.add(new ZoneEntityImpl("zone-uuid-1", "name1"));
        zones.add(new ZoneEntityImpl("zone-uuid-2", "name2"));
        return zones;
    }

    @Override
    public List<StoragePool> listStorage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ZoneEntity getZone(String uuid) {
        ZoneEntityImpl impl = new ZoneEntityImpl(uuid, "name");
        return impl;
    }

    @Override
    public StorageEntity registerStorage(String name, List<String> tags, Map<String, String> details) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ZoneEntity registerZone(String name, List<String> tags, Map<String, String> details) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PodEntity registerPod(String name, List<String> tags, Map<String, String> details) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ClusterEntity registerCluster(String name, List<String> tags, Map<String, String> details) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String registerHost(String name, List<String> tags, Map<String, String> details) {
        // TODO Auto-generated method stub
        return null;
    }

}