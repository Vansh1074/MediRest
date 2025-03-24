package com.example.MediCure.service;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorRecommendationService {

    private Classifier model;
    private Instances dataStructure;

    public DoctorRecommendationService() {
        try {
            // Load the pre-trained WEKA model
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/resources/doctor_recommendation.model"));
            model = (Classifier) ois.readObject();
            ois.close();

            // Create the structure for incoming symptom data
            ArrayList<Attribute> attributes = new ArrayList<>();
            attributes.add(new Attribute("symptom1", (List<String>) null)); // String attribute
            attributes.add(new Attribute("symptom2", (List<String>) null));
            attributes.add(new Attribute("symptom3", (List<String>) null));

            ArrayList<String> classes = new ArrayList<>();
            classes.add("Cardiologist");
            classes.add("Dermatologist");
            classes.add("Orthopedist");
            // Add other specialties

            attributes.add(new Attribute("class", classes));
            dataStructure = new Instances("DoctorRecommendation", attributes, 0);
            dataStructure.setClassIndex(dataStructure.numAttributes() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String recommendDoctor(String symptom1, String symptom2, String symptom3) {
        try {
            DenseInstance instance = new DenseInstance(4);
            instance.setValue(dataStructure.attribute("symptom1"), symptom1);
            instance.setValue(dataStructure.attribute("symptom2"), symptom2);
            instance.setValue(dataStructure.attribute("symptom3"), symptom3);
            instance.setDataset(dataStructure);

            double classIndex = model.classifyInstance(instance);
            return dataStructure.classAttribute().value((int) classIndex);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error making prediction.";
        }
    }
}
