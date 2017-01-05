package uom.CIS3087;

import java.util.ArrayList;

/**
 * Created by Etienne G on 05/01/2017.
 */
public class DecisionNode {

    public Double entropy;
    public String label;
    public AttributeNode parent;
    public AttributeNode child;

    public DecisionNode(String label, AttributeNode parent){
        this.label = label;
        this.parent = parent;
    }


}
