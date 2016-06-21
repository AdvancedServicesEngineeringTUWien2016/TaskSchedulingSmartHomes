package com.ase.iec.Shared.dto;


public class EnergyPriceDTO {

	
	private double selling;
	private double buying;
	
	
    public EnergyPriceDTO(double selling, double buying) {
		super();
		this.selling = selling;
		this.buying = buying;
	}

	public EnergyPriceDTO() {
    }

	public double getBuying() {
		return buying;
	}

	public void setBuying(double buying) {
		this.buying = buying;
	}

	public double getSelling() {
		return selling;
	}

	public void setSelling(double selling) {
		this.selling = selling;
	}
   
    
}