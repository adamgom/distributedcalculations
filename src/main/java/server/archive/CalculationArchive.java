package server.archive;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class CalculationArchive {
	private static CalculationArchive instance;

	private List<Calculation> calculations;
	
	private CalculationArchive() {
		calculations = new ArrayList<>();
	}

	public static CalculationArchive getInstance() {
		if (instance == null) instance = new CalculationArchive();
		return instance;
	}

	public void addOperation(double result, String operator, String computeType) {
		calculations.add(new Calculation(result, operator, computeType));
	}

	public JsonObject getHistory() {
		JsonObjectBuilder history = Json.createObjectBuilder();
		for (int i = 0 ; i < calculations.size() ; i++) {
			history.add("operation" + i, calculations.get(i).getComputeSet());
		}
		return history.build();
	}
}
