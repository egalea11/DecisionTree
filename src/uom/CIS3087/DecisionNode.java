package uom.CIS3087;

public class DecisionNode {

    public String label;
    public AttributeNode parent;
    public AttributeNode child;

    public DecisionNode(String label, AttributeNode parent){
        this.label = label;
        this.parent = parent;
    }


}
