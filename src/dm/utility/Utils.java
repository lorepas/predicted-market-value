package dm.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;

import dm.model.PerformanceLastSeason;
import dm.persistence.DAOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mongodb.MongoClient;


public class Utils {
	
	private static final String CSV_SEPARATOR = ",";
		
	public static MongoClient getMongoClient() throws MongoException {
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		builder.maxConnectionIdleTime(60000);
		List<ServerAddress> servers = new ArrayList<ServerAddress>();
		servers.add(new ServerAddress("172.16.0.132", 27018));
		servers.add(new ServerAddress("172.16.0.136", 27018));
		servers.add(new ServerAddress("172.16.0.138", 27018));
		MongoClient mongoClient = new MongoClient(servers, builder.build());
		try {
			mongoClient.getAddress();
		} catch (Exception e) {
			System.out.println("Mongo is down");
			throw new MongoException("Mongo is down: you should connect with a VPN");
		}
		return mongoClient;
	}
	

	
	public static String toPrettyFormat(String jsonString){
		ObjectMapper mapper = new ObjectMapper();
		String prettyJson = new String();
        try {
            Object jsonObject = mapper.readValue(jsonString, Object.class);
            prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prettyJson;
	 }
	
	public static void writeFileJSON(List<PerformanceLastSeason> d_performances) throws DAOException {
		PrintWriter out = null;
		try {
			String defaultDirectoryPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
			System.out.println("Default directory: " + defaultDirectoryPath);
			File file = new File(defaultDirectoryPath + "/LastSeasonPerformance.json");
			Gson gson = new Gson();
			String json = gson.toJson(d_performances);
			if (!file.exists()) {
		        if (file.createNewFile()) {
		            out = new PrintWriter(file,"UTF-8");
		            out.println(toPrettyFormat(json));
		            out.close();
		        }
		    } else {
		    	out = new PrintWriter(file,"UTF-8");
	            out.println(toPrettyFormat(json));
	            out.close();
		    }
		} catch (FileNotFoundException fe) {
			throw new DAOException(fe);
		} catch (IOException ioe) {
			throw new DAOException(ioe);
		} finally {
			if(out != null) {
				out.close();
			}
		}
	}
	
	
    public static void writeFileCSV(List<PerformanceLastSeason> performances){
        try
        {
        	String header = "SEASON,TEAM,NATION,ROLE,BORN DATE,CALLS,PRESENCES,GOALS,ASSISTS,PENALTY GOALS,OWN GOALS,YELLOW CARDS,DOUBLE YELLOW CARDS,RED CARDS,MINUTES PLAYED,MARKET VALUE";
        	String defaultDirectoryPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(defaultDirectoryPath+"/statistics.csv"), "UTF-8"));
            bw.append(header);
            bw.newLine();
            for (PerformanceLastSeason performance : performances)
            {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(performance.getSeason());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getTeam());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getNation());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getRole());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getBornDate());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getCalls());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getPresences());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getGoals());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getAssists());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getPenalityGoals());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getOwnGoals());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getYellowCards());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getDoubleYellowCards());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getRedCards());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getMinutesPlayed());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(performance.getMarketValue());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        }
        catch (FileNotFoundException e){
        	e.printStackTrace();
        }
        catch (IOException e){
        	e.printStackTrace();
        }
    }
 }
