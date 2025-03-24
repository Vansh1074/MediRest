package com.example.MediCure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;

@SpringBootApplication
public class MediCureApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(MediCureApplication.class, args);
		System.out.println("Medicure");

	}
}
