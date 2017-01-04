package uom.CIS3087;

public class Main {
    public static void main(String[] args) throws Exception{

        // run decision tree
        ID3 tree = new ID3();
        tree.readInputData("C:\\DTreeInput.txt");

    }
}
