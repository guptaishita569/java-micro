package com.ecom.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TrackingDto {

    private UUID trackingId;

	public UUID getTrackingId() {
		return trackingId;
	}

	public void setTrackingId(UUID trackingId) {
		this.trackingId = trackingId;
	}

}
