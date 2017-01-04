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
    private List<String> trainingDataSet;


    public ID3() {
        this.attributeNodes = new ArrayList<AttributeNode>();
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
//        ArrayList<String>[] table = new ArrayList[numberOfAttributes];


        String line;
        ArrayList<String> trainingDataSet = new ArrayList<>();
        int lineCounter = 0;
        // till EOF
        while ((line = bin.readLine()) != null) {
            String[] trainingData = line.split(", ");
            lineCounter++;
            for (int i = 0; i < numberOfAttributes; i++) {
                attributeNodes.get(i).addLabel(trainingData[i]);
                System.out.println("label: " + trainingData[i]);
            }
            trainingDataSet.add(line);
        }
        this.trainingDataSet = trainingDataSet;


        AttributeNode target = attributeNodes.get(4);

        for (AttributeNode a : attributeNodes){
//            calculateEntropy(a);
            calculateEntropy(target, a);
        }

        System.out.println("Complete");
    }





    // add new attribute to attribute array-list
    private void createNewNode(String attrName) {
        attributeNodes.add(new AttributeNode(attrName));
    }


    // calculate entropy of attribute node
    private double calculateEntropy(AttributeNode node){
        double entropy = 0;
        double totalVal = 0;
        int attrCount = 0;

        for (int k : node.attrValues){
            totalVal += k;
            attrCount++;
        }

        double p = 0;
        for (int i=0; i < attrCount; i++){
            p = (node.attrValues.get(i)/totalVal);
            double log2 = (Math.log10(p)/Math.log10(2));
            entropy += -(p*(log2));
        }

        System.out.println("Entropy for " + node.attr + ": " + entropy);

        return entropy;
    }

    // calculate entropy of two attributes
    private double calculateEntropy(AttributeNode target, AttributeNode node) {
        double entropy = 0;
        double totalVal = 0;
        int attrCount = 0;

        List<IntegerArray> targetEntropy = new ArrayList<IntegerArray>();

        for(int i=0; i<trainingDataSet.size(); i++){
            ArrayList<String> trainingData = new ArrayList<>(Arrays.asList(trainingDataSet.get(i).split(", ")));

        }


        for(AttributeNode x : attributeNodes){
//            if(x)
        }



        System.out.println("Double Entropy for " + node.attr + ": " + entropy);

        return entropy;
    }


//
//    public double calculateEntropy(List<String> target, List<String> attribute)
//    {
//        double entropy=0;
//        //this line creates a set which only contains the unique values of the ArrayList
//        //this is then used to calculate the probability
//        Set<String> uniqueAttributeValues = new HashSet<String>(attribute);
//
//        for (String uniqueAttributeValue : uniqueAttributeValues)
//        {
//            List<String> targetDataForEntropy = new ArrayList<String>();
//            double count=0;
//            for (int i=0;i<attribute.size();i++)
//            {
//                if (attribute.get(i).equals(uniqueAttributeValue))
//                {
//                    count++;
//                    targetDataForEntropy.add(target.get(i));
//                }
//            }
//            double prob = count/attribute.size();
//            entropy += prob * calculateEntropy(targetDataForEntropy);
//        }
//
//        return entropy;
//    }

}

