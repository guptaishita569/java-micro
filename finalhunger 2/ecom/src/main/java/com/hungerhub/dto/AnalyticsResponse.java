package com.hungerhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnalyticsResponse {

    private Long placed;

    private Long shipped;

    private Long delivered;


    public AnalyticsResponse() {
		// TODO Auto-generated constructor stub
	}

	public Long getPlaced() {
		return placed;
	}

	public void setPlaced(Long placed) {
		this.placed = placed;
	}

	public Long getShipped() {
		return shipped;
	}

	public void setShipped(Long shipped) {
		this.shipped = shipped;
	}

	public Long getDelivered() {
		return delivered;
	}

	public void setDelivered(Long delivered) {
		this.delivered = delivered;
	}

	public Long getCurrentMonthOrders() {
		return currentMonthOrders;
	}

	public void setCurrentMonthOrders(Long currentMonthOrders) {
		this.currentMonthOrders = currentMonthOrders;
	}

	public Long getPreviousMonthOrders() {
		return previousMonthOrders;
	}

	public void setPreviousMonthOrders(Long previousMonthOrders) {
		this.previousMonthOrders = previousMonthOrders;
	}

	public Long getCurrentMonthEarnings() {
		return currentMonthEarnings;
	}

	public void setCurrentMonthEarnings(Long currentMonthEarnings) {
		this.currentMonthEarnings = currentMonthEarnings;
	}

	public Long getPreviousMonthEarnings() {
		return previousMonthEarnings;
	}

	public void setPreviousMonthEarnings(Long previousMonthEarnings) {
		this.previousMonthEarnings = previousMonthEarnings;
	}

	private Long currentMonthOrders;

    private Long previousMonthOrders;

    private Long currentMonthEarnings;

    private Long previousMonthEarnings;
}
