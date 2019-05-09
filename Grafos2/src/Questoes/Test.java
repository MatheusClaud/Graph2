package Questoes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.util.NeighborCache;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.RandomWalkIterator;

import jgrapht.MyJGraphTUtil;
import jgrapht.RelationshipEdge;

public class Test {

	public static void main(String[] args) {
		
		SimpleGraph<String, DefaultEdge> tree = new SimpleGraph<>(RelationshipEdge.class);
		tree = (SimpleGraph<String, DefaultEdge>) MyJGraphTUtil.importDefaultGraphGML(tree, "./src/graphs/cubo-spanningtree.gml");
		
		RandomWalkIterator <String, DefaultEdge> random = new RandomWalkIterator <> (tree);
		String root = random.next();
		
		DefaultDirectedGraph<String, DefaultEdge> rootedTree = new DefaultDirectedGraph<>(DefaultEdge.class);
		NeighborCache <String,DefaultEdge> nc = new NeighborCache <String,DefaultEdge> (tree);
		
		Iterator v1 = tree.vertexSet().iterator();
		Iterator a2 = tree.edgeSet().iterator();

		System.out.println(tree.vertexSet());
		System.out.println(tree.edgeSet());

		
		while (v1.hasNext()) {
			rootedTree.addVertex((String) v1.next());
		}
		
		System.out.println(rootedTree.vertexSet());
		
		ArrayList <String> al = new ArrayList<String>();
		al.add(root);
		HashMap<String, String> m = new HashMap<String, String>();

		
		while (!al.isEmpty()) {
			
			String atual = al.get(0);
			Set a = nc.neighborsOf(atual);
			
			if (!a.isEmpty()) {
				Iterator i = a.iterator();
				
				while(i.hasNext()) {
					String proximo = (String) i.next();
					
					if(!m.containsKey(proximo)) {
						rootedTree.addEdge(atual, proximo);
						al.add(proximo);
					}
				}
			}
			
			al.remove(0);
			m.put(atual, atual);
			
		}
		
		Scanner s = new Scanner(System.in);
		
		int j = 0;
		for (j = 0; j < 4; j++) {
			System.out.println("\nQual é a raiz?");
			String palpite = s.nextLine();
			
			if(!palpite.equals(root)) {
				List<String> l = Graphs.predecessorListOf(rootedTree,palpite);
				List<String> l2 = Graphs.successorListOf(rootedTree,palpite);
				String filhos = "{";
				while (!l2.isEmpty()) {
					filhos += l2.get(0);
					l2.remove(0);
					if (!l2.isEmpty()) {
						filhos += ", ";
					}
				}
				filhos += "}.";
				System.out.println(palpite+ " não é raiz. O pai de "+palpite+" é " + l.get(0)+" e os filhos de " + palpite+ " são" + filhos);;
			}
			else {
				System.out.println("Você acertou!");
				break;
			}
		}
		
		if (j == 4) {
			System.out.println("Número de tentativas exedido!");
		}


	}
}