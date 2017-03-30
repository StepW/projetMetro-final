package trajetMetro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.BufferedReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;


import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class Main4 {

 	//on créé un graphe qui va regrouper tout les éléments
	 static Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
	 
	 //on place les stations qui correspondent aux sommets dans l'interface en fonction des coordonnées attribuées aux stations
	 
	 //à l'aide de la fonction Transformer, nous voulons placer les sommets
	 //en fonction des coordonnées X et Y de de chaque station à partir d'objets Point 2D
	 static Transformer<Station, Point2D> locationTransformer = new Transformer<Station, Point2D>() {
			
	        @Override
	        public Point2D transform(Station vertex) {
	            return new Point2D.Double((double) vertex.getX()*2-100 ,
	            		(double) Math.abs(vertex.getY() - 600)*2-100 );
	        }
	    };
	 
	    
     //nous voulons faire une mise en forme du graphe
	 static StaticLayout<Station, Liaison> layout =
		new StaticLayout<Station, Liaison>(g, locationTransformer);
	 
     //puis nous insérons un module de visualisation du graphe
	 static VisualizationViewer<Station,Liaison> vv = 
			 new VisualizationViewer<Station,Liaison>(layout);

	 //la valeur de poids d'un arc ici, correspond au temps de trajet dans une liaison,
	 //en effet dans ca graphe un arc correspond à une liaison entre deux stations de métro
	 
	 static Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
		 public Integer transform(Liaison link) {
			 return link.getTemps();
		 }
	 };

	 //pour effectuer un calcul d'itinéraire, on utilise un algorithme de Dijkstra.
	 //on l'utilisera sur le graphe des stations de métro
	 static DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
   	
	// ces valeurs seront plus tard utilisées pour l'interface graphique
		private static JPanel container = new JPanel();
		private static JPanel container1 = new JPanel();
		  private static JComboBox<String> combo = new JComboBox<String>();
		  private static JComboBox<String> combo2 = new JComboBox<String>();
		  private static JLabel choix = new JLabel("Bienvenue, choisissez votre itinéraire :");
		  private static JLabel debut = new JLabel("Départ :");
		  private static JLabel fin = new JLabel("Arrivée :");
		  
		 private static JLabel texte = new JLabel();
		 private static JButton bouton = new JButton("Rechercher");
		 private static JLabel stations = new JLabel("Stations traversées :");
		 private static JLabel resultats = new JLabel("Résultats :");

		 private static JButton ajoutStationFerme = new JButton("Ajouter");
		 private static JButton effacerListe = new JButton("Effacer");
		 private static JComboBox<Integer> combof = new JComboBox<Integer>();
	

	
	
 public static void main(String[] args) {
		 
		 //on donne le répertoire de chaque fichier
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 
		 //on créé la liste des stations pour les sommets du graphe
			ArrayList<Station> l = new ArrayList<Station>();
			
			//on créé la liste des liaisons pour les arcs du graphe
			ArrayList<Liaison> a = new ArrayList<Liaison>();
			


			//-----------------------------------------------------------------------------
			 //on cherche les éléments dans le fichier texte et on les range dans une liste Arraylist
			
			 try{
				 //on appelle les fonctions pour appeler le fichier
					InputStream ips1=new FileInputStream(fichiernoms);
					InputStream ips2=new FileInputStream(fichiersommets); 
					InputStream ips3=new FileInputStream(fichierarcs); 
					InputStreamReader ipsr1=new InputStreamReader(ips1);
					InputStreamReader ipsr2=new InputStreamReader(ips2);
					InputStreamReader ipsr3=new InputStreamReader(ips3);
					BufferedReader br1=new BufferedReader(ipsr1);
					BufferedReader br2=new BufferedReader(ipsr2);
					BufferedReader br3=new BufferedReader(ipsr3);
					String ligne;

					//inportation du fichier 1
					//-----------------------------------------------------------------------------

					while ((ligne=br1.readLine())!=null){

//							//on extrait les données d'une ligne en séparant les identifiants et le nom des stations
							String[] st = ligne.split(" ", 2);
							
//							//on remplit la liste de sommets avec les objets stations
							//on créé les variables pour les tests
							
							String nomStation = st[1].trim().toLowerCase();
							int idStation = Integer.parseInt(st[0]);



							
							l.add(new Station(idStation,nomStation,0,0,Color.red,false));
							
						}
					
					br1.close();

					//inportation du fichier 2
					//-----------------------------------------------------------------------------		
					
					while ((ligne=br2.readLine())!=null){
						String[] st = ligne.split(" ");
						
						
						//on intègre les éléments de la liste dans des variables pour le test suivant
						int idStation = Integer.parseInt(st[0]);
						int coordX = Integer.parseInt(st[1]);
						int coordY = Integer.parseInt(st[2]);
						
						
						//on parcours la liste et on vérifie dans la liste identifiants de la station
						//lorsqu'un identifiant est reconnu, on attribut les coordonnées à la station auquelle elle appartient
						

							for(Station station : l){	
								
								
									if(idStation == station.getId()){
										station.setX(coordX);
										station.setY(coordY);
										}
								
							}

					
						}
					
					br2.close();
					
					//inportation du fichier 3
					//-----------------------------------------------------------------------------
					
					while ((ligne=br3.readLine())!=null){
						String[] st = ligne.split(" ");
						
						
						//on intègre les éléments de la liste dans des variables pour le test suivant
						int sI = Integer.parseInt(st[0]);
						int sF = Integer.parseInt(st[1]);
						double tempsDouble = Double.parseDouble(st[2]);
						int temps = (int) tempsDouble;
						
						//on commence par vérifier le sommet initiale de l'arc
						//pour cela on cherche l'identifiant dans la liste des stations
						for(Station station : l){
							if(station.getId() == sI){
								
								//quand la condition précédente est remplie, on fait la même opération
								//avec le sommet final
								
									for(Station station2 : l){
											if(station2.getId() == sF){
												
												/*quand les deux stations sont trouvées, on cherche ensuite des arcs
												 *qui sont des correspondances on sait les reconaitre lorsque le temps
												 *est égal à la valeur double 120.0 
												 */
												if(tempsDouble == 120.0){
													a.add(new Liaison(sI,sF,temps,Color.black,true));
												}
												else {
													a.add(new Liaison(sI,sF,temps,Color.black,false));
													
												}
												
												
											}
											
										}

								}
							
						}
						
					}
					
					br3.close();
					
				}		
				catch (Exception e){
					System.out.println(e.toString());
				}

			 //--------------------------------------------------------------------------------------------
			 
			 //dans ce graphe on ajoute les sommets qui correspondent aux stations.
			 for(Station station : l){
				 g.addVertex((Station)station);
			 }
			 
			 
			 
			 /*on ajoute ensuite les arcs à partir de la liste des liaisons
			 * pour cela créé une boucle qui va parcourir cette liste en lisant l'identifiant du sommet entrant
			 * 
			 * en même temps on parcourt la liste des station et on vérifie d'identifiant qu'on associe
			 * au sommet initiale de la liaison
			 * 
			 * on va parcourir les identifiants de cette station qui correspond à un sommet du graphe
			 */

			 
			 for(Liaison liaison : a){
				 for(Station station : l){

					 /* en même temps on parcourt la liste des station et on vérifie d'identifiant qu'on associe
					 *au sommet initiale de la liaison
					 */
						 if(station.getId() == liaison.getA()){
							 
							//quand l'identifiant est trouvé, on fait la même opération pour le sommet final
							 for(Station stat : l){

									 if(stat.getId() == liaison.getB()){
										 
							//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
							//on recommence jusqu'à ce que toute la liste des liaisons soit parcourue
										 int d = liaison.Distance(station.getX(),station.getY(),stat.getX(),stat.getY());
										 liaison.setDistance(d);
										 
										 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
										 break;
									 }

								 }
								 
							 }
							 
						 }
				 
			 }
			 
			    // nous allons maintenant générer une interface pour visualiser le graphe
			 	// ce graphe correspond à un plan géométrique des liaisons et des stations de métro
			 

			 		//nouss indiquons une taille pour la fenetre
				      vv.setPreferredSize(new Dimension(1000,800)); 
				      
				      
				      

				        //nous voulons afficher le nom des stations ansi que leur identifiant dans l'interface graphique
				        vv.getRenderContext().setVertexLabelTransformer(new Transformer<Station, String>() {
				            public String transform(Station e) {
				                return (e.getNom() + " : " + e.getId());
				            }
				        });
		 

				        
					     //-------------------------------------------------------------------------------
				        
				        
				        //lorsqu'un itinéraire est trouvé, on veut le colorier
				        //on change alors la couleur des sommets et des arcs qui correspondent
				        //à cet itinéraire
				        
				        Transformer<Liaison,Paint> strokePaint = new Transformer<Liaison,Paint>() {
				        	 public Paint transform(Liaison i) {
				        	 return i.getC();
				        	 }
				        	 };
				        	 
					      vv.getRenderContext().setEdgeDrawPaintTransformer(strokePaint);
					      vv.getRenderContext().setArrowFillPaintTransformer(strokePaint);
					      vv.getRenderContext().setArrowDrawPaintTransformer(strokePaint);
					      
					      Transformer<Station,Paint> vertexPaint = new Transformer<Station,Paint>() {
					    	  public Paint transform(Station e) {
					    	  return e.getC();
					    	  }
					    	  }; 
					    	  
					    	  vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);			        
				        
					     //------------------------------------------------------------

				      //on utilise une fonction pour pouvoir zoomer sur le graphe	  
				        DefaultModalGraphMouse<Station, Liaison> gm = new DefaultModalGraphMouse<Station, Liaison>();
				        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
				        vv.setGraphMouse(gm);
				      

				        //on veut afficher le graphe dans une fenêtre
				        //on donne les paramètre d'affichage
				        JFrame frame = new JFrame("Itinéraire Métro de Paris");
					      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					      frame.setSize(800, 800);
					      frame.isResizable();
					      frame.setLocationRelativeTo(null);
					      frame.getContentPane().add(vv); 
					      frame.pack();

					      
					        
					        //debut de la création de new fenetre
					      
					      GridLayout gridResult = new GridLayout(2,2,5,5);
					      GridLayout gridStationFerme = new GridLayout(4,0,5,5);
					      
					      JTextArea listeStations = new JTextArea(10,5);
					      JTextArea result = new JTextArea(10,5);
					      JTextArea statFerme = new JTextArea(10,15);
					      
						      container1.setBackground(Color.white);
							  container1.setLayout(new BorderLayout());
							  container.setBackground(Color.white);
							  container.setLayout(new BorderLayout());
							  combo.setPreferredSize(new Dimension(200, 20));
							  combo2.setPreferredSize(new Dimension(200, 20));
							  texte.setPreferredSize(new Dimension(150,20));
							  stations.setPreferredSize(new Dimension(80,20));
							  resultats.setPreferredSize(new Dimension(80,20));
							  choix.setPreferredSize(new Dimension(300,20));
							    
							    combof.setPreferredSize(new Dimension(60, 20));
							    
							    
							    
							    JPanel top = new JPanel();						 
							    JPanel roc = new JPanel();
							    JPanel bas = new JPanel();
							    
							    JPanel res = new JPanel();
							    JPanel fer = new JPanel();
							    
							    top.add(choix);
							    top.add(debut);
							    top.add(combo);
							    top.add(fin);
							    top.add(combo2);
							    top.add(bouton);
							    
							    res.setLayout(gridResult);
							    fer.setLayout(gridStationFerme);		    
							    
							    roc.add(vv);
							    
							    JScrollPane scrollpane1 = new JScrollPane(listeStations);
							    JScrollPane scrollpane2 = new JScrollPane(result);

							    fer.add(new JLabel("Stations fermées"));
							    fer.add(combof);
							    fer.add(ajoutStationFerme);
							    fer.add(effacerListe);				    
							    
							    res.add(stations);
							    res.add(scrollpane1);
							    res.add(resultats);
							    res.add(scrollpane2);
							    bas.add(res);
							    bas.add(fer);
							    bas.add(statFerme);
							    	    
							    scrollpane1.setPreferredSize(new Dimension(250, 110));
							    scrollpane2.setPreferredSize(new Dimension(250, 110));
							    statFerme.setPreferredSize(new Dimension(100, 100));
							    
							    container.add(top, BorderLayout.NORTH);
							    container.add(roc, BorderLayout.CENTER);
							    container.add(bas, BorderLayout.SOUTH);
							    texte.setHorizontalAlignment(JLabel.CENTER);

							    listeStations.setLineWrap(true);
							    listeStations.setEditable(false);
							    listeStations.setWrapStyleWord(true);
							   
							    frame.setContentPane(container);
							    frame.setVisible(true);
							    
							    ArrayList<String> listString = new ArrayList<String>();
							    
							    
							    for(Station station : l){
							    	if(!listString.contains(station.getNom())){
							    		listString.add(station.getNom());
							    	}
							    }
							    
							    for(String station : listString){
							    	combo.addItem(station);
							    	combo2.addItem(station);
								 }

							    
							      for(Station station : l){
							    	  combof.addItem(station.getId());
							      }
							   
							    //fin creation de new fenetre
					      
					      
					      List<Station> FERME = new ArrayList<Station>();
					      
					      
					      //nous devons aussi faire une liste des stations qui sont fermées,
					      //on indique l'identifiant de la station
					      //les stations fermées influent sur l'itinéraire à tracer
					      //le métro ne s'arrete pas au niveau des stations fermées
					      //on ne peut pas effectuer de correspondance à partir de ces dernières
					      ajoutStationFerme.addActionListener(new ActionListener() {
						   		
					        	public void actionPerformed(ActionEvent e) {
					        		
					        		String f = combof.getSelectedItem().toString();
					        		
					        		for(Station station : l){
					        			
					        			
							    		  if(Integer.parseInt(f) == station.getId()){
							    			  FERME.add(station);
							    			  station.setFerme(true);
							    			  station.setC(Color.gray);
							    			  statFerme.append(station.getId() + " - " + station.getNom() + "\n");
							    		  }
							    	  }
					        		
					        		vv.repaint();
					        		
	
					        		
					        	}
					      });
					      
					      //on a un bouton pour effacer la liste des stations fermées
					      effacerListe.addActionListener(new ActionListener() {
						   		
					        	public void actionPerformed(ActionEvent e) {
					        		
					        		for(Station station : l){
					        			for(Station stationF : FERME){
					        				
					        				
					        				if(station == stationF){
						        			station.setFerme(false);
						        			station.setC(Color.red);
					        				}
						        		}
					        			
					        			
					        			
					        			
					        		}
					        		
					        		
					        		FERME.clear();
					        		statFerme.setText("");
					        		
					        		vv.repaint();
					        		
					        		
					        	}
					      });
					      
					      //le bouton suivant sera utilisé pour calculer l'itinéraire après avoir choisi 
					      // et de fin indiquées dans la fenetre
					      bouton.addActionListener(new ActionListener() {
						   		
					        	public void actionPerformed(ActionEvent e) {
					        		
					        		
			      //on utilise un objet Djikstra pour calculer l'itinéraire le plus court entre deux stations 
			      DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
  

			      // les données sont lues dans les combobox des stations de départ et d'arrivée
	       	   // System.out.println("Départ : ");
	      	    String m = combo.getSelectedItem().toString();
	      	    String n = combo2.getSelectedItem().toString();

			      //on créé deux listes qui correspondent respectivenet à la liste des stations
			      //et à la liste des liaisons de l'itinéraire
			      List<Station> L = new ArrayList<Station>();
			      List<Liaison> A = new ArrayList<Liaison>();
			      
			      
		    	  
			      
			      for(Station station : l){
			    	  station.setC(Color.red);
			      }
			      
			      for(Liaison liaison : a){
			    	  liaison.setC(Color.black);
			      }
			      
			      
			      //pour obtenir un itinéraire, on doit taper la station de début et la station de fin

			    	  
			    	  //on vide les zones de texte
			    	  listeStations.setText("");
			    	  result.setText("");


			      //on modifie les données de la station lorsqu'elle est fermée
			      //si les liaisons autour sont des correspondances, on ne doit pas les emprunter
			      //on leur donne une priorité minimale
			    	  
			    	  for(Station station : l){
			    		  if(station.isFerme() == true){
				    		  for(Liaison liaison : a){
				    			 
				    			  if(station.getId() == liaison.getB()
				    					  && liaison.isCorresp() == true){
				    				  liaison.setTemps(100000);
				    			  }
				    			  
				    			  if(station.getId() == liaison.getA()	    					  
				    					  && liaison.isCorresp() == true){

				    				  liaison.setTemps(100000);
				    			  }
				    		  }
			    		  }
			    		  
			      }	    	  
			    	  

			      int k = 0;
			      int c = 0;
			      
			      for(Station station : l){
				      if(m.equals(station.getNom())){
				    	  k = l.indexOf(station);

				    	  }
				      if(n.equals(station.getNom())){
				    	  c = l.indexOf(station);

				    	  }
			      }
			      
			      A = alg.getPath(l.get(k), l.get(c));
			      
			      
			      for(Liaison liaison : a){
			      if(liaison.isCorresp() == true){
		    		  liaison.setTemps(120);
		    	  }
			      }

			      
			      //lorsque l'itinéraire est calculé, la station de départ est définit en fonction du nom rentré
			      //et non de l'identifiant, un nom de station a un identifiant fixe
			      //si la première liaison est une correspondance, on l'enlève de la liste

			      if(A.get(0).isCorresp() == true){
			    	  A.remove(0);  
			      }
			      
			      //on enlève aussi la dernière liaison si c'est une correspondance
			      if(A.get(A.size()-1).isCorresp() == true){
			    	  A.remove(A.size()-1);  
			      }

			      for(Liaison liaison : A){
			    	  liaison.setC(Color.red);
			      }


			      Station v = null;

			      //à l'aide de l'objet de transformation plus haut, on colorie les stations
			      //qui font partie de l'itinéraire
			      
			    //on colorie les liaisons appartenant à la liaison en rouge, les stations de l'itinéraire
			    //en bleu et les stations fermées en gris
			      
			      //on colorie d'abort la première station de l'itinéraire...
			      for(Station station : l){
			    	  if(station.getId() == A.get(0).getA()){
			    		  L.add(station);
			    		  L.get(0).setC(Color.blue);

			    	  }
			      }

			      //...puis toutes les autres
			      for(Liaison ligne : A){
				      for(Station station : l){
				    		  if(ligne.getB() == station.getId()){
				    			  v = station;
				    			  break;

				    		  }

				    	  }
				      
				    	  if(!L.contains(v) && v != null){
				    		  v.setC(Color.blue);
				    		  L.add(v);
				    		  
				    	  }
				    	  
				    	  for(Station station : FERME){
				    		  station.setC(Color.gray);
				    	  }
				    	  
				  } 
			      
			    //----------------------------------------------------------------------------------

		    	  
		    	  
		    	  //on créé une liste String qui affichera la liste des stations dans l'interface graphique
		    	  ArrayList<String> listString = new ArrayList<String>();
		    	  
		    	  //on fait attention à ne pas avoir de nom en double dans la liste
			      for(Station station : L){
			    	  if(!listString.contains(station.getNom())){
			    		  listString.add(station.getNom());
			    	  }
			      }
			      
			      for(String station : listString){
			    	  listeStations.append(station + "\n");
			      }
			      
				      
			      //à l'aide de la transformation du poids des arc plus haut, on calcule le temps
			      //passé dans les liaisons
			      Number dist = alg.getDistance(L.get(0), L.get(L.size()-1));
			      
			      // le temps d'arrêt  à une station= 20 sec
			      //on estime que pou chaque stations traversées par le métro, il y a un temps d'arrêt de 20 secondes
			      //on ne compte pas de temps à la station de départ et d'arrivée
			      //et justement on ne le compte pas non plus aux les stations fermées
			      double arret = 0;
			      for(Station station : L){
			    	  if(station.isFerme() == false)
			    	  arret += 20;
			      }
			      if(arret <= 0)
			      arret = 0;


			      
			      //on fait la conversion du temps en minutes
			      //on prend en compte le temps de trajet dans une liaison + le temps d'arret à une stations
			      double tempsM = (double) dist + arret;
			      tempsM *= 0.0166667;
			      
			      
			      //les distances sont calculées à partir des coordonnées des stations
			      //elles sont approximatives
			      
			      result.append("## Station de départ :\n"
					      + L.get(0).getNom()
					      +"\n## Station d'arrivée :\n"
					      + L.get(L.size()-1).getNom()
					      + "\n## temps de trajet estimé :\n"
					      + Math.round(tempsM) + " minutes"
					      + "\n## distance à parcourir estimée :\n"
					      );
			      
			      int d = 0;
			      for(Liaison liaison : A){
			    	 d += liaison.getDistance();
			      }	      
			      
			      String distance = null;
			      
			      //on calcule la distance estimée grâce aux coordonnées entre les stations traversées
			      //on veut afficher la distance en mètre ou en km selon la longueur
			     if(d > 1000){
			     d /= 1000;
			     distance = d + " km";
			     }
			     else
			     distance = d + " m";
			     
			     result.append(distance + "\n");
			     
			     //on modifie la couleur des sommets et des arcs
			      vv.repaint();      
			      
			      //on verifie le nombre de correspondance avec un compteur
			      //et on affiche la liste
			      result.append("\n");
			      result.append("## nombre de correspondances :\n");
			      int corr = 0;
			      List<Station> Lcorr = new ArrayList<Station>();
			      for(Liaison liaison : A){
			    	  if(liaison.isCorresp() == true){
			    		  corr += 1;
			    		  for(Station station : L){
			    			  if(liaison.getA() == station.getId()){
			    				  Lcorr.add(station);
			    			  }
			    		  }
			    	  }
			      }
			      
			      result.append(corr + "");
			      if(corr != 0){
			    	  result.append(" à :\n");
			      }
			      	      
			      for(Station station : Lcorr){

			    	  result.append(" - " + station.getNom() + "\n");
			      }

			      result.append("\n");


			      	}
			      
					        	
					        	
					        	
			      });
					      

	 			}
	}
