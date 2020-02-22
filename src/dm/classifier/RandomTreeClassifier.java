package dm.classifier;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JFileChooser;

import com.google.gson.reflect.TypeToken;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;
import weka.core.Utils;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
import weka.filters.unsupervised.attribute.NumericToNominal;

public class RandomTreeClassifier {
	
	public void classifier(String team,String nation,String role,String bornDate,int presences,int calls,int goals,int assists,int penaltyGoals,int ownGoals,int yellowCards,int doubleYellowCards,int redCards,long minutesPlayed) throws Exception {
		String defaultDirectoryPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		//DataSource source = new DataSource(defaultDirectoryPath+"\\playerStatistics.arff");
		CSVLoader csv = new CSVLoader();
		csv.setSource(new File(defaultDirectoryPath+"\\playerStatistic.csv"));
		csv.setNoHeaderRowPresent(false);
		csv.setNominalAttributes("1-4");
		csv.setDateAttributes("5");
		csv.setDateFormat("dd/MM/yyyy");
		csv.setFieldSeparator(";");
		Instances trainDataset = csv.getDataSet();
		trainDataset.deleteAttributeAt(0);
		System.out.println(trainDataset);
		List teamNominal = new ArrayList();
		List nationNominal = new ArrayList();
		List roleNominal = new ArrayList();

		for(int i=0;i<trainDataset.numDistinctValues(1);i++) {
			teamNominal.add(trainDataset.attribute(1).value(i));
		}
		
		for(int i=0;i<trainDataset.numDistinctValues(2);i++) {
			nationNominal.add(trainDataset.attribute(2).value(i));
		}
		
		for(int i=0;i<trainDataset.numDistinctValues(3);i++) {
			roleNominal.add(trainDataset.attribute(3).value(i));
		}
		
		//Setting the classes
		trainDataset.setClassIndex(trainDataset.numAttributes()-1);
		
		//Classifier
//		String[] options = new String[2];
//		options[0]="-print";
//		options[1]="-attribute-importance";
//		 
//		randomForest.setOptions(options);
		RandomForest randomForest = new RandomForest();
		randomForest.buildClassifier(trainDataset);
		Evaluation eval = new Evaluation(trainDataset);
		eval.crossValidateModel(randomForest, trainDataset, 10, new Random(1));
		System.out.println(eval.toSummaryString("\nResults:\n\n",false));

		//Creating new dataset for new statistics
//		ArrayList<Attribute> attributes = new ArrayList<Attribute>();
//		//Creating the dataset header
//		attributes.add(new Attribute("TEAM",teamNominal));
//		attributes.add(new Attribute("NATION",nationNominal));
//		attributes.add(new Attribute("ROLE",roleNominal));
//		attributes.add(new Attribute("BORN DATE","dd/MM/yyyy"));
//		attributes.add(new Attribute("CALLS"));
//		attributes.add(new Attribute("PRESENCES"));
//		attributes.add(new Attribute("GOALS"));
//		attributes.add(new Attribute("ASSISTS"));
//		attributes.add(new Attribute("PENALTY GOALS"));
//		attributes.add(new Attribute("OWN GOALS"));
//		attributes.add(new Attribute("YELLOW CARDS"));
//		attributes.add(new Attribute("DOUBLE YELLOW CARDS"));
//		attributes.add(new Attribute("RED CARDS"));
//		attributes.add(new Attribute("MINUTES PLAYED"));
//		attributes.add(new Attribute("MARKET VALUE"));
//	
//		Instances newDataset = new Instances("NewPlayer",attributes,1);
//		newDataset.setClassIndex(newDataset.numAttributes()-1);
//
//		// adding instances
		double[] values = new double[trainDataset.numAttributes()];
		values[0] = trainDataset.attribute(0).indexOfValue(team);
		values[1] = trainDataset.attribute(1).indexOfValue(nation);
		values[2] = trainDataset.attribute(2).indexOfValue(role);
		values[3] = trainDataset.attribute(3).parseDate(bornDate);
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
		values[14] = Utils.missingValue();
		Instance inst = new DenseInstance(1.0,values);
//		inst.setDataset(newDataset);
//		newDataset.add(inst);
//		newDataset.get(0).setClassMissing();
//		System.out.println(newDataset);
//		System.out.println("\nEstimate Market Value:\n");
//		System.out.println(randomForest.classifyInstance(newDataset.firstInstance()));
		trainDataset.add(inst);
		System.out.println(trainDataset);
		
		System.out.println("\nEstimate Market Value:\n");
		System.out.println(randomForest.classifyInstance(trainDataset.lastInstance()));
	}
}
