package uom.CIS3087;

import java.util.ArrayList;

public class AttributeNode {

    public Double informationGain;
    public String attr;
    public ArrayList<String> data;
    public ArrayList<String> labels;
    public DecisionNode parent;
    public ArrayList<DecisionNode> children;

    public AttributeNode(String attr){
        this.attr = attr;
        this.data = new ArrayList<>();
        this.labels = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    // if label does not already exist, add it
    public void addLabel(String label){
        if(!labels.contains(label)) {
            labels.add(label);
        }
    }


}
