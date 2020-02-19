package dm.persistence;

import java.util.List;
import dm.model.PerformanceLastSeason;

public interface IDAOPlayer {
	
	List<PerformanceLastSeason> retrieveOverallStatisticsLastSeason() throws DAOException;
	//double retrieveAvgMarketValueFromPlayer(String name) throws DAOException;
	
}
