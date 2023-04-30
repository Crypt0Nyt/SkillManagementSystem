import java.util.HashMap;
import java.util.Map;

public class Rating {
	private Map<String, SkillLevel> ratings;

	public Rating() {
		this.ratings = new HashMap<>();
	}

	public void addRating(String employeeID, SkillLevel SkillLevel) {
		ratings.put(employeeID, SkillLevel);
	}

	public SkillLevel getRating(String employeeID) {
		return ratings.get(employeeID);
	}

	public Map<String, SkillLevel> getRatings() {
		return ratings;
	}

}
