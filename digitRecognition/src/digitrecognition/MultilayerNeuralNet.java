package digitrecognition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MultilayerNeuralNet {
    private int input;
    private int layer;
    private int output;
    private double[][] weights1layer;
    private double[][] weight2layer;
    private double[] inputs;
    private double[] layersout;
    private double[] outReults;
    private double LEARNING_RATE=0.3;

    public MultilayerNeuralNet() {
	this.input   = 101;
	this.layer  = 25;
	this.output  = 10;
	this.weights1layer = new double[this.input][this.layer];
	this.weight2layer = new double[this.layer][this.output];
	this.inputs 		 = new double[this.input];
	this.layersout 		 = new double[this.layer];
	this.outReults 		 = new double[this.output];
	layersout[this.layer - 1]   = 1.0;
	inputs[this.input  - 1]   = 1.0;
	getWeights();
        training();
    }
    
    public int getResult(int[] intBits) {
        forwardPropagation(intBits);
        if (output == 1) return (outReults[0] < 0.5)? 0 : 1;
        int index = 0;
        double max = outReults[0];
	for (int k = 1; k < output; k++){
            if (outReults[k] > max) {
		max = outReults[k]; index = k;
            }
        }   
	return index;
    }

    private void getWeights() {
        double min = -1.0, max = 1.0;
        for (int i = 0; i < input; i++){
            for (int j = 0; j < layer; j++){
                weights1layer[i][j] = min + (max - min) * Math.random();
            }
        }  
        for (int j = 0; j < layer; j++){
            for (int k = 0; k < output; k++){
                weight2layer[j][k] = min + (max - min) * Math.random(); 
            }
        }
		 
    }
    
    private void forwardPropagation(int[] inputs) {
        for (int i = 0; i < input - 1; i++){
            this.inputs[i] = inputs[i];
        }
        for (int j = 0; j < layer - 1; j++) {
            layersout[j] = 0.0;
            for (int i = 0; i < input; i++)
                layersout[j] += weights1layer[i][j] * this.inputs[i];
                    layersout[j] = activationFunction(layersout[j],0);
	}
	for (int k = 0; k < output; k++) {
            outReults[k] = 0.0;
            for (int j = 0; j < layer; j++){
                outReults[k] += layersout[j] * weight2layer[j][k];
            }
            outReults[k] = activationFunction(outReults[k],0);
	}
}
    
    private void backPropagation(double[] errors) {
	double[] aux = new double[output];
	for (int k = 0; k < output; k++){
            aux[k] = activationFunction(outReults[k],1) * errors[k];
        } 
	double[] auxj = new double[layer];
	for (int j = 0; j < layer; j++){
            for (int k = 0; k < output; k++){
                auxj[j] += activationFunction(layersout[j],1) * aux[k] * weight2layer[j][k];
            }
        }
	for (int i = 0; i < input; i++){
            for (int j = 0; j < layer; j++){
                weights1layer[i][j] += LEARNING_RATE * auxj[j] * inputs[i];
            }
        }
	for (int j = 0; j < layer; j++){
            for (int k = 0; k < output; k++){
                weight2layer[j][k] +=  LEARNING_RATE * aux[k] * layersout[j];
            }
        }            
    }
    
    private double activationFunction(double x,int op) {
        if(op==0){
            return 1./(1 + Math.exp(-x));
        }else{
            return x - (1-x);
        }
    }
        
    public void train(int[][] inputs, int[] val, int max) {
        int[][] auxoutput = {
            {1,0,0,0,0,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,0,1,0,0,0},
            {0,0,0,0,0,0,0,1,0,0},
            {0,0,0,0,0,0,0,0,1,0},
            {0,0,0,0,0,0,0,0,0,1},
        };
        
	while(max>0){
            for (int j = 0; j < inputs.length; j++) {
		forwardPropagation(inputs[j]);
                double[] errors = new double[output];
                for (int k = 0; k < output; k++){
                    //System.out.println("val " + val[j]);
                    errors[k] = auxoutput[val[j]][k] - outReults[k];
                }
                backPropagation(errors);
            }
            max--;
        }
    }
    
    private void training(){
        int total = 500;
        int[][] trainPatterns= new int[total][100];
        int[] outcome = new int[total];
        String line;
        char[] inputStr = new char[100];
        int i = 0;
        try {
            File here = new File("train_digits.txt");
            //System.out.println(here.getAbsolutePath());
            Scanner trainFile = new Scanner(here);
            while (trainFile.hasNextLine()) {
                line = trainFile.nextLine();  
                //System.out.println("len: "+line.length());
                if(line.length()!=0){
                   line.getChars(0, 100, inputStr, 0);
                   for(int j=0;j<100;j++){
                     trainPatterns[i][j]= inputStr[j]; 
                   }
                   outcome[i]=Integer.parseInt(Character.toString(line.charAt(100)));
                   //System.out.println("outcome " + outcome[i]);
                   i++;
                }
            }
            trainFile.close();
            train(trainPatterns,outcome,2000);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MultilayerNeuralNet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
