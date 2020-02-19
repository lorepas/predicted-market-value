package dm.classifier;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.JFileChooser;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class RandomTreeClassifier {
	
	public void classifier(String team,String nation,String role,String bornDate,int presences,int calls,int goals,int assists,int penaltyGoals,int ownGoals,int yellowCards,int doubleYellowCards,int redCards,long minutesPlayed) throws Exception {
		String defaultDirectoryPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		//DataSource source = new DataSource(defaultDirectoryPath+"\\playerStatistics.arff");
		CSVLoader csv = new CSVLoader();
		csv.setSource(new File(defaultDirectoryPath+"\\playerStatistic.csv"));
		csv.setNoHeaderRowPresent(false);
		csv.setFieldSeparator(";");
		csv.setDateAttributes("5");
		csv.setDateFormat("dd/MM/yyyy");
		csv.setNominalAttributes("1-4");
		Instances trainDataset = csv.getDataSet();
		System.out.println(trainDataset);
		//Setting the classes
		trainDataset.setClassIndex(trainDataset.numAttributes()-1);
		String[] options = new String[2];
		options[0]="-print";
		options[1]="-attribute-importance";
//		RandomForest randomForest = new RandomForest(); 
//		randomForest.setOptions(options);
//		randomForest.buildClassifier(trainDataset);
//		Evaluation eval = new Evaluation(trainDataset);
//		eval.crossValidateModel(randomForest, trainDataset, 10, new Random(1));
//		System.out.println(eval.toSummaryString("\n Results \n\n",false));
		
		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
		//Creating the dataset header
		attributes.add(new Attribute("TEAM"));
		attributes.add(new Attribute("NATION"));
		attributes.add(new Attribute("ROLE"));
		attributes.add(new Attribute("BORN DATE"));
		attributes.add(new Attribute("CALLS"));
		attributes.add(new Attribute("PRESENCES"));
		attributes.add(new Attribute("GOALS"));
		attributes.add(new Attribute("ASSISTS"));
		attributes.add(new Attribute("PENALTY GOALS"));
		attributes.add(new Attribute("OWN GOALS"));
		attributes.add(new Attribute("YELLOW CARDS"));
		attributes.add(new Attribute("DOUBLE YELLOW CARDS"));
		attributes.add(new Attribute("RED CARDS"));
		attributes.add(new Attribute("MINUTES PLAYED"));
		attributes.add(new Attribute("MARKET VALUE"));
		Instances newDataset = new Instances("NewPlayer",attributes,1);
		
		// adding instances
		double[] values = new double[attributes.size()];
		values[0] = newDataset.attribute(0).indexOfValue(team);
		values[1] = newDataset.attribute(1).addStringValue(nation);
		values[2] = newDataset.attribute(2).addStringValue(role);
		values[3] = newDataset.attribute(3).addStringValue(bornDate);
		values[4] = calls;
		values[5] = presences;
		values[6] = goals;
		values[7] = assists;
		values[8] = penaltyGoals;
		values[9] = ownGoals;
		values[10] = yellowCards;
		values[11] = doubleYellowCards;
		values[12] = redCards;
		values[13] = minutesPlayed;
		values[14] = newDataset.attribute(14).addStringValue("?");
		Instance inst = new DenseInstance(1.0, values);
		newDataset.add(inst);
		
		System.out.println(newDataset);
	}
}
