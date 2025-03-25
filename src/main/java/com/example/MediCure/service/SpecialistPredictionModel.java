package com.example.MediCure.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.InputStream;
import java.util.ArrayList;

@Service
public class SpecialistPredictionModel {

    private Classifier model;  // Decision tree classifier (J48)
    private Instances trainingDataset;  // Store the dataset structure for consistent attributes in prediction

    public void trainModel() {
        try {
            // Load CSV from resources folder
            ClassPathResource resource = new ClassPathResource("data/doctor_specialist_binary_symptoms.csv");
            InputStream csvStream = resource.getInputStream();
            CSVLoader loader = new CSVLoader();
            loader.setSource(csvStream);

            // Load dataset and set class attribute (last column)
            trainingDataset = loader.getDataSet();
            trainingDataset.setClassIndex(trainingDataset.numAttributes() - 1);

            // Train the J48 Decision Tree Model
            model = new J48(); // J48 is WEKA's implementation of the C4.5 Decision Tree
            model.buildClassifier(trainingDataset);

            System.out.println("Model trained successfully with the CSV dataset!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error training the model: " + e.getMessage());
        }
    }

    public String predictSpecialist(double[] symptomVector) {
        try {
            // Create a new empty instance with the same structure as the training dataset
            DenseInstance newInstance = new DenseInstance(1.0, symptomVector);
            newInstance.setDataset(trainingDataset);  // Use the original dataset structure

            // Make the prediction
            double predictedClassIndex = model.classifyInstance(newInstance);
            String predictedSpecialty = trainingDataset.classAttribute().value((int) predictedClassIndex);

            return predictedSpecialty;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error making prediction: " + e.getMessage();
        }
    }

}
