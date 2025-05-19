package com.capgemini.job_application.dtos;

public class ChartDTO {
    private String label;
    private Long count;

    public ChartDTO(String label, Long count) {
        this.label = label;
        this.count = count;
    }

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}
    
}

