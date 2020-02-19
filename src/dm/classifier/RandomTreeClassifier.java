package dm.classifier;

import java.io.File;
import java.io.FileInputStream;

import javax.swing.JFileChooser;

import weka.core.Instances;
import weka.core.converters.CSVLoader;
import weka.core.converters.ConverterUtils.DataSource;

public class RandomTreeClassifier {
	
	public void classifier() throws Exception {
		String defaultDirectoryPath = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		//DataSource source = new DataSource("\\Users\\loren\\OneDrive\\Documenti\\playerStatistic.csv");
		CSVLoader csv = new CSVLoader();
		csv.setSource(new File(defaultDirectoryPath+"\\playerStatistic.csv"));
		csv.setNoHeaderRowPresent(false);
		csv.setFieldSeparator(";");
//		csv.setDateAttributes("5");
//		csv.setDateFormat("dd/MM/yyyy");
//		csv.setNominalAttributes("1-4");
		Instances trainDataset = csv.getDataSet();
		System.out.println(trainDataset.toString());
		//Setting the classes
		trainDataset.setClassIndex(trainDataset.numAttributes()-1);
		//Removing the season
		trainDataset.remove(0);
		int numClasses = trainDataset.numClasses();
		System.out.println(numClasses);
		for(int i=0; i<numClasses; i++) {
			String classValue = trainDataset.classAttribute().value(i);
			System.out.println("Class "+i+" is "+classValue);
		}
	}
}
