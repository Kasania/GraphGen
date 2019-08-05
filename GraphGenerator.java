package com.graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class GraphGenerator {

	private static final String FilePrefix = "GraphFile";
	private static final String NodePrefix = "Node : ";
	private static final String NodePostfix = "--";
	
	private ArrayList<Graph> graphs;
	

	public GraphGenerator() {
		graphs = new ArrayList<Graph>();
	}

	public ArrayList<Graph> generate(ArrayList<Integer> values) {
		graphs.clear();

		int NodeNum = values.get(0);
		int MinLine = values.get(1);
		int MaxLine = values.get(2);
		int MinCost = values.get(3);
		int MaxCost = values.get(4);

		for (int i = 0; i < NodeNum; ++i) {
			Graph graph = new Graph(i);
			graphs.add(graph);
		}

		for (Graph graph : graphs) {
			int lineNum = Util.randBetween(MinLine, MaxLine);
			int leftAttept = graphs.size() * 10;

			for (int i = graph.getAllConnection().size(); i < lineNum; ++i) {
				if (leftAttept < 0) {
					break;
				}
				int connectTo = Util.randBetween(0, NodeNum - 1);
				if (connectTo == graph.id) {
					--i;
					continue;
				} else if (graph.hasConnection(connectTo)) {
					continue;
				} else if (graphs.get(connectTo).getAllConnection().size() >= MaxLine) {
					--i;
					--leftAttept;
					continue;
				} else {
					graph.connectTo(graphs.get(connectTo), Util.randBetween(MinCost, MaxCost));
				}
			}

		}

		return this.graphs;
	}

	public ArrayList<Graph> parseGraph(File src) {
		graphs.clear();
		
		try {

			FileInputStream fis = new FileInputStream(src);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			BufferedReader br = new BufferedReader(isr);

			String firstLine = br.readLine(); 
			
			if(firstLine.contentEquals(FilePrefix)) {
				int nodeNum = Integer.parseInt(br.readLine());
				for(int i = 0; i<nodeNum; ++i) {
					graphs.add(new Graph(i));
				}
				
				while(true) {
					String line = br.readLine();
					if(line == null) {
						break;
					}
					if(line.contentEquals(NodePrefix)) {
						int id = Integer.parseInt(br.readLine());
						while(true) {
							String contents = br.readLine();
							if(contents.contentEquals(NodePostfix)) {
								break;
							}
							String[] values = contents.split(",");
							int target = Integer.parseInt(values[0]);
							int Cost = Integer.parseInt(values[1]);;
							
							graphs.get(id).connectTo(graphs.get(target), Cost);
							
						}
						
					}
				}
			}

			br.close();
			isr.close();
			fis.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
		}

		return graphs;
	}

	public void saveGraph(ArrayList<Graph>graphs, File dst) {
		try {

			FileOutputStream fos = new FileOutputStream(dst.getAbsolutePath()+".txt");
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			BufferedWriter bw = new BufferedWriter(osw);
			
			bw.append(FilePrefix + "\r\n");
			
			bw.append(graphs.size() + "\r\n");
			
			for (Graph graph : graphs) {
				int id = graph.id;
				bw.append(NodePrefix+"\r\n" );
				bw.append(id + "\r\n");
				graph.getAllConnection().forEach((connected,line)->{
					try {
						bw.append(connected + "," + line.getCost() + "\r\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				bw.append(NodePostfix + "\r\n");
			}
			

			bw.close();
			osw.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
