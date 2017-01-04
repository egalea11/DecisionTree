package algorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Etienne G on 04/01/2017.
 */
public class ID3 {

    public void readInputData(String filename) throws Exception {
        FileInputStream in = null;
        try {
            File inputFile = new File(filename);
            in = new FileInputStream(inputFile);
        } catch (Exception e) {
            System.err.println("Unable to open file: " + filename + "\n" + e);
        }

        // read file
        BufferedReader bin = new BufferedReader(new InputStreamReader(in));
        String attributeList = bin.readLine();
//        System.out.println(attributeList + "\n");

        ArrayList<String> attributes = new ArrayList<>(Arrays.asList(attributeList.split(", ")));

        for (String x : attributes){
            System.out.println(x);
        }

        int numberOfAttributes = attributes.size();
        ArrayList<String>[] table = new ArrayList[numberOfAttributes];

        while(true){
            String[] nextLine = bin.readLine().split(", ");
            for(int i=0; i < numberOfAttributes; i++){
                if(!table[i].contains(nextLine[i])){
                    table[i].add(nextLine[i]);
                    Attribute c = new Attribute(nextLine[i]);
                }
                else{

                }
            }
        }






//        StringTokenizer tokenizer = new StringTokenizer(input);
//        numAttributes = tokenizer.countTokens();
//        if (numAttributes <= 1) {
//            System.err.println("Read line: " + input);
//            System.err.println("Could not obtain the names of attributes in the line");
//            System.err.println("Expecting at least one input attribute and one output attribute");
//            return 0;
//        }
//        domains = new Vector[numAttributes];
//        for (int i = 0; i < numAttributes; i++){
//            // change
//            domains = new Vector[i];
//        }
//        attributeNames = new String[numAttributes];
//        for (int i=0; i < numAttributes; i++) {
//            attributeNames[i] = tokenizer.nextToken();
//        }
//        while(true) {
//            input = bin.readLine();
//            if (input == null) break;
//            if (input.startsWith("//")) continue;
//            if (input.equals("")) continue;
//            tokenizer = new StringTokenizer(input);
//            int numtokens = tokenizer.countTokens();
//            if (numtokens != numAttributes) {
//                System.err.println( "Read " + root.data.size() + " data");
//                System.err.println( "Last line read: " + input);
//                System.err.println( "Expecting " + numAttributes + " attributes");
//                return 0;
//            }
//            DataPoint point = new DataPoint(numAttributes);
//            for (int i=0; i < numAttributes; i++) {
//                point.attributes[i] = getSymbolValue(i, tokenizer.nextToken());
//            }
//            root.data.addElement(point);
//        }
//        bin.close();

    }
}
