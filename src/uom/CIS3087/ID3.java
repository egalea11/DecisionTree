package uom.CIS3087;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.sun.xml.internal.fastinfoset.util.StringArray;
import org.w3c.dom.Attr;

import javax.management.Attribute;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Etienne G on 04/01/2017.
 */
public class ID3 {

    private List<AttributeNode> attributeNodes;
    private ArrayList<String[]> trainingDataSet;


    public ID3() {
        this.attributeNodes = new ArrayList<>();
        this.trainingDataSet = new ArrayList<>();
    }

    public void readInputData(String filename) throws Exception {
        FileInputStream in = null;
        try {
            File inputFile = new File(filename);
            in = new FileInputStream(inputFile);
        } catch (Exception e) {
            System.err.println("Unable to open file: " + filename + "\n" + e);
            System.exit(1);
        }

        // read file
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        String attributeString = bin.readLine();
//        System.out.println(attributeString + "\n");

        ArrayList<String> attributeList = new ArrayList<>(Arrays.asList(attributeString.split(", ")));

        for (String attr : attributeList) {
            System.out.println(attr);
            createNewNode(attr);
        }

        int numberOfAttributes = attributeList.size();


        String line;
        // till EOF
        while ((line = bin.readLine()) != null) {
            String[] trainingData = line.split(", ");
//            trainingDataSet.add(trainingData);
            for (int i = 0; i < numberOfAttributes; i++) {
                attributeNodes.get(i).addLabel(trainingData[i]);
                attributeNodes.get(i).data.add(trainingData[i]);
//                System.out.println("label: " + trainingData[i]);
            }
        }

        AttributeNode target = attributeNodes.get(attributeNodes.size()-1);
        // calculating Entropy
        for (AttributeNode a : attributeNodes){
            calculateEntropy(a);
        }

        // calculating Double Entropy
        for (AttributeNode a : attributeNodes){
            calculateDoubleEntropy(target, a);
        }

        // calculating Information Gain
        for (AttributeNode a : attributeNodes) {
            System.out.println("Information Gain [" + a.attr + "]: " +calculateInformationGain(target, a));
        }

        System.out.println("Complete");
    }



    // add new attribute to attribute array-list
    public void createNewNode(String attrName) {
        attributeNodes.add(new AttributeNode(attrName));
    }


    // calculate entropy of attribute node
    public double calculateEntropy(AttributeNode node){
        double entropy = 0;
        double total = node.data.size();

        // loop for all labels in attribute
        for(String label : node.labels){
            double count = 0;
            // loop for every record in data
            for(String x : node.data) {
                if (x.equals(label)) {
                    count++;
                }
            }
            double p = (count/total);
            double log2 = (Math.log10(p)/Math.log10(2));
            entropy += (-p*(log2));
        }

//        System.out.println("Entropy for " + node.attr + ": " + entropy);

        return entropy;
    }


    // calculate entropy of two attributes
    public double calculateDoubleEntropy(AttributeNode target, AttributeNode node) {
        double entropy = 0;
        double total = node.data.size();

        // loop for all labels in attribute
        for(String xLabel : node.labels){
            AttributeNode temp = new AttributeNode("temp");
            for(String targetLabel : target.labels){
                double count = 0;
                for(int i=0; i<node.data.size(); i++){
                    if(node.data.get(i).equals(xLabel) && target.data.get(i).equals(targetLabel)){
                        count++;
                        temp.addLabel(targetLabel);
                        temp.data.add(targetLabel);
                    }
                }
            }
            double sunny = node.attrValues.get(node.labels.indexOf(xLabel));
            double p = sunny/total;
            entropy += p * calculateEntropy(temp);
//            System.out.println("Entropy for " + xLabel + " + entropy : " + entropy);
        }

        return entropy;
    }


    public double calculateInformationGain(AttributeNode target, AttributeNode node){
        return calculateEntropy(target) - calculateDoubleEntropy(target, node);
    }


}

