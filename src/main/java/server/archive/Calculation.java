package server.archive;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import transportProtocol.Fields;

public class Calculation {
	private double result;
	private String operator;
	private String computeType;
	
	public Calculation(double result, String operator, String computeType) {
		this.result = result;
		this.operator = operator;
		this.computeType = computeType;
	}
	
	public JsonObjectBuilder getComputeSet() {
		JsonObjectBuilder set = Json.createObjectBuilder();
		set.add("result", result);
		set.add("operator", operator);
		set.add(Fields.headerComputePrev, computeType);
		return set;
	}
}
