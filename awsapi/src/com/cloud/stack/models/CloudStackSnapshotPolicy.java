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

package com.cloud.stack.models;

import com.google.gson.annotations.SerializedName;

public class CloudStackSnapshotPolicy {
	
	@SerializedName(ApiConstants.ID)
	private String id;
	@SerializedName(ApiConstants.INTERVAL_TYPE)
	private String intervalType;
	@SerializedName(ApiConstants.MAX_SNAPS)
	private Long maxSnaps;
	@SerializedName(ApiConstants.SCHEDULE)
	private String schedule;
	@SerializedName(ApiConstants.TIMEZONE)
	private String timeZone;
	@SerializedName(ApiConstants.VOLUME_ID)
	private String volumeId;

	/**
	 * 
	 */
	public CloudStackSnapshotPolicy() {
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the intervalType
	 */
	public String getIntervalType() {
		return intervalType;
	}

	/**
	 * @return the maxSnaps
	 */
	public Long getMaxSnaps() {
		return maxSnaps;
	}

	/**
	 * @return the schedule
	 */
	public String getSchedule() {
		return schedule;
	}

	/**
	 * @return the timeZone
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @return the volumeId
	 */
	public String getVolumeId() {
		return volumeId;
	}

}
