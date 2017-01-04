package uom.CIS3087;

import java.util.ArrayList;

/**
 * Created by Etienne G on 04/01/2017.
 */

public class AttributeNode {

    public Double entropy;
    public String attr;
    public ArrayList<String> labels;
    public ArrayList<Integer> attrValues;
    public AttributeNode parent;
    public AttributeNode[] children;

    public AttributeNode(String attr){
        this.attr = attr;
        labels = new ArrayList<>();
        attrValues = new ArrayList<>();
    }

    public void addLabel(String label){
        int index = labels.indexOf(label);
        // if label does not exist, create new label
        if(!labels.contains(label)) {
            System.out.println(label + " - does not exist");
            labels.add(label);
            index = labels.indexOf(label);
            attrValues.add(index, 0);
        }
        // increment label value
        attrValues.set(index, attrValues.get(index)+1);
    }


}
