package algorithm;

import java.util.ArrayList;

/**
 * Created by Etienne G on 04/01/2017.
 */

public class AttributeNode {

    public Double entropy;
    public String attr;
    public ArrayList<String> labels;
    public int[] attrValues;
    public AttributeNode parent;
    public AttributeNode[] children;

    public AttributeNode(String attr){
        this.attr = attr;
    }

    public void addLabel(String label){
        int index;
        if(!labels.contains(label)) {
            labels.add(label);
        }
        index = labels.indexOf(label);
        attrValues[index]++;
    }

}
