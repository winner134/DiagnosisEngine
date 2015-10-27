package diagnosis.recommendation.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.graph.Graph;

public class JungView {

	private JFrame frmJungGraphView;
	private VisualizationViewer<String, String> vv;
	private PickingGraphMousePlugin gm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JungView window = new JungView();
					window.frmJungGraphView.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JungView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmJungGraphView = new JFrame();
		frmJungGraphView.setTitle("JUNG Graph View");
		frmJungGraphView.setBounds(100, 100, 800, 650);
		frmJungGraphView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmJungGraphView.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
	}
	
	public void setJungParameters(Graph graph) {
		

		Layout<String, String> layout = new CircleLayout(graph);
		layout.setSize(new Dimension(500,500)); // sets the initial size of the space
		// The BasicVisualizationServer<V,E> is parameterized by the edge types
		vv = new VisualizationViewer<String,String>(layout);
		vv.setPreferredSize(new Dimension(800, 650)); //Sets the viewing area size
		
		frmJungGraphView.getContentPane().add(vv);
		frmJungGraphView.setVisible(true);
		
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		//vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
		
		// Create a graph mouse and add it to the visualization component
		gm = new PickingGraphMousePlugin();
	
		vv.addMouseListener(gm);
		vv.addMouseMotionListener(gm);
	}
	
	public void setVertexColourTransformer(final List<String> diseases) {
		
		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			
			public Paint transform(String i) {
				
				if(!diseases.contains(i)) 
					return Color.YELLOW;
				
				else
					return Color.RED;
				
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
		
	}
	
	public void setVertexColourTransformer(final String disease, final List<String> knownSymptoms,  final List<String> missingSymptoms) {
		
		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			
			public Paint transform(String i) {
				
				if(i.equalsIgnoreCase(disease)) 
					return Color.RED;
				
				else if(knownSymptoms.contains(i))
					return Color.YELLOW;
				
				else if(missingSymptoms.contains(i))
					return Color.BLUE;
				
				else
					return null;
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);	
	}
	
	public void setVertexColourTransformer(final String disease, final List<String> diseases,  final String[] symptoms) {
		
		Transformer<String,Paint> vertexPaint = new Transformer<String,Paint>() {
			
			public Paint transform(String i) {
				
				if(i.equalsIgnoreCase(disease)) 
					return Color.GREEN;
				
				else if(diseases.contains(i))
					return Color.RED;
				
				else {
					
					for(int j = 0; j < symptoms.length; j++) {
						
						if(symptoms[j].equalsIgnoreCase(i))
							return Color.YELLOW;
					}
				}
			
				return null;
			}
		};
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
	}
			
	
	public void setVertexShapeTransformer(final String shape) {

		Transformer<String,Shape> vertexPaint = new Transformer<String,Shape>() {
			
			public Shape transform(String i) {
				
				if(shape.equals(new String("RECTANGLE"))) {
					
					return new Rectangle(-5, -5, 15,15);
				}
				
				else
					return null;
			}
		};
		
		vv.getRenderContext().setVertexShapeTransformer(vertexPaint);
	}
	
	public void setEdgeTransformer(final String edge) {
		
		// Set up a new stroke Transformer for the edges
		float dash[] = {10.0f};
		final Stroke edgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
		BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);
		
		Transformer<String, Stroke> edgeStrokeTransformer =
		new Transformer<String, Stroke>() {
			
			public Stroke transform(String s) {
				
				if(s.equalsIgnoreCase(edge))
					return edgeStroke;
				
				else
					return null;
			}
		};
		
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
	}

}
