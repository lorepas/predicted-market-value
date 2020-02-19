package dm.classifier;

import java.io.File;
import java.io.FileInputStream;
import java.util.Random;

import javax.swing.JFileChooser;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class RandomTreeClassifier {
	
	public void classifier() throws Exception {
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
		RandomForest randomForest = new RandomForest(); 
		randomForest.setOptions(options);
		randomForest.buildClassifier(trainDataset);
		Evaluation eval = new Evaluation(trainDataset);
		eval.crossValidateModel(randomForest, trainDataset, 10, new Random(1));
		System.out.println(eval.toSummaryString("\n Results \n\n",false));
	}
}
