package density.tools;

import density.*;
import gnu.getopt.*;
import java.io.*;
import java.util.*;

public class RandomSample3 {

    public static void main(String[] args) {
	if (args.length < 2) {
	    System.out.println("Usage: RandomSample3 numSamples layer1 layer2 ...");
	    System.exit(1);
	}
	try { 
	    new RandomSample3().go(args);
	}
	catch (IOException e) { 
	    System.out.println("Error in RandomSample: " + e.toString());
	    System.exit(1);
	}
    }

    void go(String[] args) throws IOException {
	int ngrids = args.length-1;
	String[] fileNames = new String[ngrids];
	int numSamples = Integer.parseInt(args[0]);
	Grid[] g = new Grid[ngrids];
	for (int i=0; i<ngrids; i++) {
	    fileNames[i] = args[i+1];
	    g[i] = GridIO.readGrid(fileNames[i]);
	}
	GridDimension dim = g[0].getDimension();
	density.Utils.generator = new Random(0);
	System.out.print("species,x,y");
	for (int i=0; i<ngrids; i++)
	    System.out.print(","+new File(fileNames[i]).getName());
	System.out.println();
	for (int i=0; i<numSamples; i++) {
	    int r, c;
	    while (true) {
		boolean success=true;
		r = density.Utils.generator.nextInt(dim.getnrows());
		c = density.Utils.generator.nextInt(dim.getncols());
		for (int j=0; j<ngrids; j++)
		    if (!g[j].hasData(r,c)) success=false;
		if (success) break;
	    } 
	    System.out.print("sample," + dim.toX(c) + "," + dim.toY(r));
	    for (int j=0; j<ngrids; j++)
		System.out.print("," + g[j].eval(r,c));
	    System.out.println();
	}
    }
}
