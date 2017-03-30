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

 	//on cr�� un graphe qui va regrouper tout les �l�ments
	 static Graph<Station, Liaison> g = new DirectedSparseMultigraph<Station, Liaison>();
	 
	 //on place les stations qui correspondent aux sommets dans l'interface en fonction des coordonn�es attribu�es aux stations
	 
	 //� l'aide de la fonction Transformer, nous voulons placer les sommets
	 //en fonction des coordonn�es X et Y de de chaque station � partir d'objets Point 2D
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
	 
     //puis nous ins�rons un module de visualisation du graphe
	 static VisualizationViewer<Station,Liaison> vv = 
			 new VisualizationViewer<Station,Liaison>(layout);

	 //la valeur de poids d'un arc ici, correspond au temps de trajet dans une liaison,
	 //en effet dans ca graphe un arc correspond � une liaison entre deux stations de m�tro
	 
	 static Transformer<Liaison, Integer> wtTransformer = new Transformer<Liaison,Integer>() {
		 public Integer transform(Liaison link) {
			 return link.getTemps();
		 }
	 };

	 //pour effectuer un calcul d'itin�raire, on utilise un algorithme de Dijkstra.
	 //on l'utilisera sur le graphe des stations de m�tro
	 static DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
   	
	// ces valeurs seront plus tard utilis�es pour l'interface graphique
		private static JPanel container = new JPanel();
		private static JPanel container1 = new JPanel();
		  private static JComboBox<String> combo = new JComboBox<String>();
		  private static JComboBox<String> combo2 = new JComboBox<String>();
		  private static JLabel choix = new JLabel("Bienvenue, choisissez votre itin�raire :");
		  private static JLabel debut = new JLabel("D�part :");
		  private static JLabel fin = new JLabel("Arriv�e :");
		  
		 private static JLabel texte = new JLabel();
		 private static JButton bouton = new JButton("Rechercher");
		 private static JLabel stations = new JLabel("Stations travers�es :");
		 private static JLabel resultats = new JLabel("R�sultats :");

		 private static JButton ajoutStationFerme = new JButton("Ajouter");
		 private static JButton effacerListe = new JButton("Effacer");
		 private static JComboBox<Integer> combof = new JComboBox<Integer>();
	

	
	
 public static void main(String[] args) {
		 
		 //on cherche le fichier � chercher
		 String fichiernoms = "D:/JAVA/fichiers stations/nom station.txt";
		 String fichiersommets = "D:/JAVA/fichiers stations/coordonnee sommet.txt";
		 String fichierarcs = "D:/JAVA/fichiers stations/arc valeur temps.txt";

		 
		 //on cr�� la liste des stations pour les sommets du graphe
			ArrayList<Station> l = new ArrayList<Station>();
			
			//on cr�� la liste des liaisons pour les arcs du graphe
			ArrayList<Liaison> a = new ArrayList<Liaison>();
			


			//-----------------------------------------------------------------------------
			 //on cherche les �l�ments dans le fichier texte et on les range dans une liste Arraylist
			
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

//							//on extrait les donn�es d'une ligne en s�parant les identifiants et le nom des stations
							String[] st = ligne.split(" ", 2);
							
//							//on remplit la liste de sommets avec les objets stations
							//on cr�� les variables pour les tests
							
							String nomStation = st[1].trim().toLowerCase();
							int idStation = Integer.parseInt(st[0]);



							
							l.add(new Station(idStation,nomStation,0,0,Color.red,false));
							
						}
					
					br1.close();

					//inportation du fichier 2
					//-----------------------------------------------------------------------------		
					
					while ((ligne=br2.readLine())!=null){
						String[] st = ligne.split(" ");
						
						
						//on int�gre les �l�ments de la liste dans des variables pour le test suivant
						int idStation = Integer.parseInt(st[0]);
						int coordX = Integer.parseInt(st[1]);
						int coordY = Integer.parseInt(st[2]);
						
						
						//on parcours la liste et on v�rifie dans la liste identifiants de la station
						//lorsqu'un identifiant est reconnu, on attribut les coordonn�es � la station auquelle elle appartient
						

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
						
						
						//on int�gre les �l�ments de la liste dans des variables pour le test suivant
						int sI = Integer.parseInt(st[0]);
						int sF = Integer.parseInt(st[1]);
						double tempsDouble = Double.parseDouble(st[2]);
						int temps = (int) tempsDouble;
						
						//on commence par v�rifier le sommet initiale de l'arc
						//pour cela on cherche l'identifiant dans la liste des stations
						for(Station station : l){
							if(station.getId() == sI){
								
								//quand la condition pr�c�dente est remplie, on fait la m�me op�ration
								//avec le sommet final
								
									for(Station station2 : l){
											if(station2.getId() == sF){
												
												/*quand les deux stations sont trouv�es, on cherche ensuite des arcs
												 *qui sont des correspondances on sait les reconaitre lorsque le temps
												 *est �gal � la valeur double 120.0 
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
			 
			 
			 
			 /*on ajoute ensuite les arcs � partir de la liste des liaisons
			 * pour cela cr�� une boucle qui va parcourir cette liste en lisant l'identifiant du sommet entrant
			 * 
			 * en m�me temps on parcourt la liste des station et on v�rifie d'identifiant qu'on associe
			 * au sommet initiale de la liaison
			 * 
			 * on va parcourir les identifiants de cette station qui correspond � un sommet du graphe
			 */

			 
			 for(Liaison liaison : a){
				 for(Station station : l){

					 /* en m�me temps on parcourt la liste des station et on v�rifie d'identifiant qu'on associe
					 *au sommet initiale de la liaison
					 */
						 if(station.getId() == liaison.getA()){
							 
							//quand l'identifiant est trouv�, on fait la m�me op�ration pour le sommet final
							 for(Station stat : l){

									 if(stat.getId() == liaison.getB()){
										 
							//une fois le sommet initial et final, on va pouvoir ajouter un arc dans le graphe
							//on recommence jusqu'� ce que toute la liste des liaisons soit parcourue
										 int d = liaison.Distance(station.getX(),station.getY(),stat.getX(),stat.getY());
										 liaison.setDistance(d);
										 
										 g.addEdge((Liaison)liaison,station,stat,EdgeType.DIRECTED);
										 break;
									 }

								 }
								 
							 }
							 
						 }
				 
			 }
			 
			    // nous allons maintenant g�n�rer une interface pour visualiser le graphe
			 	// ce graphe correspond � un plan g�om�trique des liaisons et des stations de m�tro
			 

			 		//nouss indiquons une taille pour la fenetre
				      vv.setPreferredSize(new Dimension(1000,800)); 
				      
				      
				      

				        //nous voulons afficher le nom des stations ansi que leur identifiant dans l'interface graphique
				        vv.getRenderContext().setVertexLabelTransformer(new Transformer<Station, String>() {
				            public String transform(Station e) {
				                return (e.getNom() + " : " + e.getId());
				            }
				        });
		 

				        
					     //-------------------------------------------------------------------------------
				        
				        
				        //lorsqu'un itin�raire est trouv�, on veut le colorier
				        //on change alors la couleur des sommets et des arcs qui correspondent
				        //� cet itin�raire
				        
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
				      

				        //on veut afficher le graphe dans une fen�tre
				        //on donne les param�tre d'affichage
				        JFrame frame = new JFrame("Itin�raire M�tro de Paris");
					      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					      frame.setSize(800, 800);
					      frame.isResizable();
					      frame.setLocationRelativeTo(null);
					      frame.getContentPane().add(vv); 
					      frame.pack();

					      
					        
					        //debut de la cr�ation de new fenetre
					      
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

							    fer.add(new JLabel("Stations ferm�es"));
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
					      
					      
					      //nous devons aussi faire une liste des stations qui sont ferm�es,
					      //on indique l'identifiant de la station
					      //les stations ferm�es influent sur l'itin�raire � tracer
					      //le m�tro ne s'arrete pas au niveau des stations ferm�es
					      //on ne peut pas effectuer de correspondance � partir de ces derni�res
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
					      
					      //on a un bouton pour effacer la liste des stations ferm�es
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
					      
					      //le bouton suivant sera utilis� pour calculer l'itin�raire apr�s avoir choisi 
					      // et de fin indiqu�es dans la fenetre
					      bouton.addActionListener(new ActionListener() {
						   		
					        	public void actionPerformed(ActionEvent e) {
					        		
					        		
			      //on utilise un objet Djikstra pour calculer l'itin�raire le plus court entre deux stations 
			      DijkstraShortestPath<Station,Liaison> alg = new DijkstraShortestPath<Station, Liaison>(g,wtTransformer);
  

			      // les donn�es sont lues dans les combobox des stations de d�part et d'arriv�e
	       	   // System.out.println("D�part : ");
	      	    String m = combo.getSelectedItem().toString();
	      	    String n = combo2.getSelectedItem().toString();

			      //on cr�� deux listes qui correspondent respectivenet � la liste des stations
			      //et � la liste des liaisons de l'itin�raire
			      List<Station> L = new ArrayList<Station>();
			      List<Liaison> A = new ArrayList<Liaison>();
			      
			      
		    	  
			      
			      for(Station station : l){
			    	  station.setC(Color.red);
			      }
			      
			      for(Liaison liaison : a){
			    	  liaison.setC(Color.black);
			      }
			      
			      
			      //pour obtenir un itin�raire, on doit taper la station de d�but et la station de fin

			    	  
			    	  //on vide les zones de texte
			    	  listeStations.setText("");
			    	  result.setText("");


			      //on modifie les donn�es de la station lorsqu'elle est ferm�e
			      //si les liaisons autour sont des correspondances, on ne doit pas les emprunter
			      //on leur donne une priorit� minimale
			    	  
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

			      
			      //lorsque l'itin�raire est calcul�, la station de d�part est d�finit en fonction du nom rentr�
			      //et non de l'identifiant, un nom de station a un identifiant fixe
			      //si la premi�re liaison est une correspondance, on l'enl�ve de la liste

			      if(A.get(0).isCorresp() == true){
			    	  A.remove(0);  
			      }
			      
			      //on enl�ve aussi la derni�re liaison si c'est une correspondance
			      if(A.get(A.size()-1).isCorresp() == true){
			    	  A.remove(A.size()-1);  
			      }

			      for(Liaison liaison : A){
			    	  liaison.setC(Color.red);
			      }


			      Station v = null;

			      //� l'aide de l'objet de transformation plus haut, on colorie les stations
			      //qui font partie de l'itin�raire
			      
			    //on colorie les liaisons appartenant � la liaison en rouge, les stations de l'itin�raire
			    //en bleu et les stations ferm�es en gris
			      
			      //on colorie d'abort la premi�re station de l'itin�raire...
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

		    	  
		    	  
		    	  //on cr�� une liste String qui affichera la liste des stations dans l'interface graphique
		    	  ArrayList<String> listString = new ArrayList<String>();
		    	  
		    	  //on fait attention � ne pas avoir de nom en double dans la liste
			      for(Station station : L){
			    	  if(!listString.contains(station.getNom())){
			    		  listString.add(station.getNom());
			    	  }
			      }
			      
			      for(String station : listString){
			    	  listeStations.append(station + "\n");
			      }
			      
				      
			      //� l'aide de la transformation du poids des arc plus haut, on calcule le temps
			      //pass� dans les liaisons
			      Number dist = alg.getDistance(L.get(0), L.get(L.size()-1));
			      
			      // le temps d'arr�t  � une station= 20 sec
			      //on estime que pou chaque stations travers�es par le m�tro, il y a un temps d'arr�t de 20 secondes
			      //on ne compte pas de temps � la station de d�part et d'arriv�e
			      //et justement on ne le compte pas non plus aux les stations ferm�es
			      double arret = 0;
			      for(Station station : L){
			    	  if(station.isFerme() == false)
			    	  arret += 20;
			      }
			      if(arret <= 0)
			      arret = 0;


			      
			      //on fait la conversion du temps en minutes
			      //on prend en compte le temps de trajet dans une liaison + le temps d'arret � une stations
			      double tempsM = (double) dist + arret;
			      tempsM *= 0.0166667;
			      
			      
			      //les distances sont calcul�es � partir des coordonn�es des stations
			      //elles sont approximatives
			      
			      result.append("## Station de d�part :\n"
					      + L.get(0).getNom()
					      +"\n## Station d'arriv�e :\n"
					      + L.get(L.size()-1).getNom()
					      + "\n## temps de trajet estim� :\n"
					      + Math.round(tempsM) + " minutes"
					      + "\n## distance � parcourir estim�e :\n"
					      );
			      
			      int d = 0;
			      for(Liaison liaison : A){
			    	 d += liaison.getDistance();
			      }	      
			      
			      String distance = null;
			      
			      //on calcule la distance estim�e gr�ce aux coordonn�es entre les stations travers�es
			      //on veut afficher la distance en m�tre ou en km selon la longueur
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
			    	  result.append(" � :\n");
			      }
			      	      
			      for(Station station : Lcorr){

			    	  result.append(" - " + station.getNom() + "\n");
			      }

			      result.append("\n");


			      	}
			      
					        	
					        	
					        	
			      });
					      

	 			}
	}