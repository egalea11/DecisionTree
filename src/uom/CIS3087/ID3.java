package uom.CIS3087;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Etienne G on 04/01/2017.
 */

public class ID3 {

    public int trainingDataLines = 14;

    private AttributeNode root;
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

        ArrayList<String> attributeList = new ArrayList<>(Arrays.asList(attributeString.split(", ")));

        for (String attr : attributeList) {
//            System.out.println(attr);
            createNewNode(attr);
        }

        int numberOfAttributes = attributeList.size();

        String line;
        // till EOF
        while ((line = bin.readLine()) != null) {
            String[] trainingData = line.split(", ");
            trainingDataSet.add(trainingData);
            for (int i = 0; i < numberOfAttributes; i++) {
                attributeNodes.get(i).addLabel(trainingData[i]);
                attributeNodes.get(i).data.add(trainingData[i]);
            }
        }
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

        System.out.println("Entropy for " + node.attr + ": " + entropy);

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
            int labelTotal = 0;
            for (String label : node.data)
                if(label.equals(xLabel))
                    labelTotal++;

            double p = labelTotal/total;
            entropy += p * calculateEntropy(temp);
            System.out.println("Entropy for " + xLabel + " + entropy : " + entropy);
        }

        return entropy;
    }


    public double calculateInformationGain(AttributeNode target, AttributeNode node){
        return calculateEntropy(target) - calculateDoubleEntropy(target, node);
    }

    public AttributeNode findAttributeWithBestInformationGain(List<AttributeNode> nodes){
        double bestIG = 0;
        // PlayTennis
        AttributeNode target = nodes.get(nodes.size()-1);
        // Finding attribute with best information gain
        for (int i=0; i < nodes.size()-1; i++) {
            double gain = calculateInformationGain(target, nodes.get(i));
            nodes.get(i).informationGain = gain;
            if(gain > bestIG){
                bestIG = gain;
            }
            System.out.println("Information Gain [" + nodes.get(i).attr + "]: " + gain);
        }

        AttributeNode bestNode = null;
        for (int i=0; i < nodes.size()-1; i++) {
            if (bestIG == nodes.get(i).informationGain) {
                bestNode = nodes.get(i);
            }
        }

        return bestNode;
    }

    // first run
    public void buildDecisionTree(){

        List<AttributeNode> nodes = attributeNodes;

        // Finds the Attribute with the best information gain from the training data provided
        AttributeNode bestNode = findAttributeWithBestInformationGain(nodes);
        System.out.println("BestNode: " + bestNode.attr);

        root = createDecisionNodes(nodes, bestNode);

        // call recursive method
        buildTree(nodes, root.children.get(1));
    }

    public void buildTree(List<AttributeNode> nodes, DecisionNode parent){
        // Finds the Attribute with the best information gain from the training data provided
        AttributeNode bestNode = findAttributeWithBestInformationGain(nodes);
        System.out.println("BestNode: " + bestNode.attr);

        parent.child = bestNode;


    }

    // creates DecisionNodes from labels
    private AttributeNode createDecisionNodes(List<AttributeNode>nodes, AttributeNode aNode) {
        for(String label : aNode.labels){
            DecisionNode dNode = new DecisionNode(label, aNode);
            dNode = createSubDecisionTree(nodes, dNode);    // prune table
            aNode.children.add(dNode);
        }
        return aNode;
    }

    public DecisionNode createSubDecisionTree(List<AttributeNode> nodes, DecisionNode dNode){
        List<AttributeNode> tempNodes = nodes;

        int attributeIndex = nodes.indexOf(dNode.parent);

        // pruning table
        // for all attributes... (14)
        for (int i=trainingDataLines-1; i>0; i--){
            // if decisionNode label does not match with attribute's label, remove all record
            if(!dNode.label.equals(tempNodes.get(attributeIndex).data.get(i))){
                // remove record which does not match label
                for(int k=0; k<attributeNodes.size(); k++){
                    tempNodes.get(k).data.remove(i);
                }
            }
        }

        // remove attribute
        tempNodes.remove(attributeIndex);

        // calculate entropy & put node with best Information gain as child
        AttributeNode bestNode = findAttributeWithBestInformationGain(tempNodes);

        dNode.child = bestNode;
        dNode.child.parent = dNode; // might not be necessary



        return dNode;
    }


}