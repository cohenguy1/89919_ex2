import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import InputOutput.DataClass;
import InputOutput.Output;

public class MainEx2 {

	public static void main(String[] args) {
		
		String devl_inputFile = args[0];
	    String test_inputFile = args[1];
	    String inputWord = args[2];
	    String outputFile = args[3];
	    
	    Output outputClass = new Output(outputFile);
		outputClass.writeNames();
		outputClass.writeOutput(devl_inputFile);
		outputClass.writeOutput(test_inputFile);
		outputClass.writeOutput(inputWord);
		outputClass.writeOutput(outputFile);
		outputClass.writeOutput(outputClass.vocabulary_size);
		outputClass.writeOutput(1.0/outputClass.vocabulary_size);
		
	    
	    try {
	    	DataClass devData = new DataClass();
			devData.readInputFile(devl_inputFile);
					
			outputClass.writeOutput(devData.wordsTotalAmount(devData.mapTotalDocsWordCount()));
			Map<String, Integer> trainMap = new TreeMap<String, Integer>();
			Map<String, Integer> validationMap  = new TreeMap<String, Integer>();
			devData.splitXPrecentOfDocsWords(0.9,trainMap,validationMap);
			outputClass.writeOutput(devData.wordsTotalAmount(validationMap));
			
			long numberOfEventsInTrainingSet = devData.wordsTotalAmount(trainMap);
			outputClass.writeOutput(numberOfEventsInTrainingSet);
			
			//from now - not like ex2_dummpy_ouptut.txt
			outputClass.writeOutput(trainMap.keySet().size()); //TODO: check why dummy says 18976 and we 18977
			
			int inputWordOccurencesOnTraining = trainMap.get(inputWord) == null ? 0 : trainMap.get(inputWord);
			outputClass.writeOutput(inputWordOccurencesOnTraining); //Ido checked and saw their realy is 11 in the training and 3 in the validation

			outputClass.writeOutput((double)inputWordOccurencesOnTraining/numberOfEventsInTrainingSet);
			
			String unseenWord = "unseen-word";
			int unseenWordOccurencesInTraining = trainMap.get(unseenWord) == null ? 0 : trainMap.get(unseenWord);
			outputClass.writeOutput((double)unseenWordOccurencesInTraining/numberOfEventsInTrainingSet);

			double lambda = 0.1;
			trainMap = new TreeMap<String, Integer>();
			validationMap  = new TreeMap<String, Integer>();
			devData.splitXPrecentOfDocsWords(lambda , trainMap, validationMap);
			
			inputWordOccurencesOnTraining = trainMap.get(inputWord) == null ? 0 : trainMap.get(inputWord);
			numberOfEventsInTrainingSet = devData.wordsTotalAmount(trainMap);
			
			double pLidstoneInputWord = (inputWordOccurencesOnTraining + lambda)/(numberOfEventsInTrainingSet + outputClass.vocabulary_size);
			outputClass.writeOutput(pLidstoneInputWord);
			
			unseenWordOccurencesInTraining = trainMap.get(unseenWord) == null ? 0 : trainMap.get(unseenWord);
			double pLidstoneUnseenWord = (unseenWordOccurencesInTraining + lambda)/(numberOfEventsInTrainingSet + outputClass.vocabulary_size);
			outputClass.writeOutput(pLidstoneUnseenWord);
			
//			DataClass testData = new DataClass();
//			testData.readInputFile(test_inputFile);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

}
