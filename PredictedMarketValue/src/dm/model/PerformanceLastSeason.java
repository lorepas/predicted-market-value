package dm.model;

import java.text.ParseException;

import com.google.gson.Gson;

public class PerformanceLastSeason {
	
	private String season;
	private String team;
	private String nation;
	private String role;
	private String bornDate;
	private String marketValue;
//	private int goalConceded;
//	private int cleanSheets;
	private int assists;
	private int penalityGoals;
	private int calls;
	private int presences;
	private int goals;
	private int ownGoals;
	private int yellowCards;
	private int doubleYellowCards;
	private int redCards;
	private double minutesPlayed;
	
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}

	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getMarketValue() {
		return marketValue;
	}
	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}
	/*
	public Integer getGoalConceded() {
		return goalConceded;
	}
	public void setGoalConceded(Integer goalConceded) {
		this.goalConceded = goalConceded;
	}
	public Integer getCleanSheets() {
		return cleanSheets;
	}
	public void setCleanSheets(Integer cleanSheets) {
		this.cleanSheets = cleanSheets;
	}
	*/
	public int getAssists() {
		return assists;
	}
	public void setAssists(int assists) {
		this.assists = assists;
	}
	public int getPenalityGoals() {
		return penalityGoals;
	}
	public void setPenalityGoals(int penalityGoals) {
		this.penalityGoals = penalityGoals;
	}

	public int getCalls() {
		return calls;
	}
	public void setCalls(int calls) {
		this.calls = calls;
	}
	public int getPresences() {
		return presences;
	}
	public void setPresences(int presences) {
		this.presences = presences;
	}
	public int getGoals() {
		return goals;
	}
	public void setGoals(int goals) {
		this.goals = goals;
	}
	public int getOwnGoals() {
		return ownGoals;
	}
	public void setOwnGoals(int ownGoals) {
		this.ownGoals = ownGoals;
	}
	public int getYellowCards() {
		return yellowCards;
	}
	public void setYellowCards(int yellowCards) {
		this.yellowCards = yellowCards;
	}
	public int getDoubleYellowCards() {
		return doubleYellowCards;
	}
	public void setDoubleYellowCards(int doubleYellowCards) {
		this.doubleYellowCards = doubleYellowCards;
	}
	public int getRedCards() {
		return redCards;
	}
	public void setRedCards(int redCards) {
		this.redCards = redCards;
	}
	public double getMinutesPlayed() {
		return minutesPlayed;
	}
	public void setMinutesPlayed(double minutesPlayed) {
		this.minutesPlayed = minutesPlayed;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getBornDate() {
		return bornDate;
	}
	public void setBornDate(String bornDate) throws ParseException {
		this.bornDate = bornDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(team);
		return builder.toString();
	}
	
	public String toJSON() {
		Gson g = new Gson();
		return g.toJson(this);
	}

	public static PerformanceLastSeason teamFromJson(String jsonString) {
		Gson g = new Gson();
		return g.fromJson(jsonString, PerformanceLastSeason.class);
	}
}
