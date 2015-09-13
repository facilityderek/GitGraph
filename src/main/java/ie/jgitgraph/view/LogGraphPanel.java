/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.jgitgraph.view;

import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.control.*;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.subLayout.TreeCollapser;
import java.awt.*;
import java.awt.event.*;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.*;
import ie.jgitgraph.model.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.ConstantTransformer;

/**
 *
 * @author dfitzsimons
 */
public class LogGraphPanel extends javax.swing.JPanel{

    private static final long serialVersionUID = 1L;

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger( LogGraphPanel.class.getName() );

    /**
     * Creates new form LogGraphPanel
     */
    public LogGraphPanel() {
        //debugGrphics();
        initComponents();
        
    }

    
    final void debugGrphics(){
        RepaintManager repaintManager = RepaintManager.currentManager( this );
        repaintManager.setDoubleBufferingEnabled( false );        
    }
    /**
     * the graph
     */
    Forest<String, Integer> graph;

    Factory<DirectedGraph<String, Integer>> graphFactory = () -> new DirectedSparseMultigraph<>();

    Factory<Tree<String, Integer>> treeFactory = () -> new DelegateTree<>( graphFactory );

    Factory<Integer> edgeFactory = new Factory<Integer>() {
        int i = 0;

        @Override
        public Integer create() {
            return i++;
        }
    };

    Factory<String> vertexFactory = new Factory<String>() {
        int i = 0;

        @Override
        public String create() {
            return "V" + i++;
        }
    };

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<String, Integer> vv;

    VisualizationServer.Paintable rings;

    String root;

    TreeLayout<String, Integer> layout;

    @SuppressWarnings( "unchecked" )
    FRLayout<String, Integer> layout1;

    TreeCollapser collapser;

    RadialTreeLayout<String, Integer> radialLayout;

    /**
     *
     */
    private void createTree() {
        graph.addVertex( "V0" );
        graph.addEdge( edgeFactory.create(), "V0", "V1" );
        graph.addEdge( edgeFactory.create(), "V0", "V2" );
        graph.addEdge( edgeFactory.create(), "V1", "V4" );
        graph.addEdge( edgeFactory.create(), "V2", "V3" );
        graph.addEdge( edgeFactory.create(), "V2", "V5" );
        graph.addEdge( edgeFactory.create(), "V4", "V6" );
        graph.addEdge( edgeFactory.create(), "V4", "V7" );
        graph.addEdge( edgeFactory.create(), "V3", "V8" );
        graph.addEdge( edgeFactory.create(), "V6", "V9" );
        graph.addEdge( edgeFactory.create(), "V4", "V10" );

        graph.addVertex( "A0" );
        graph.addEdge( edgeFactory.create(), "A0", "A1" );
        graph.addEdge( edgeFactory.create(), "A0", "A2" );
        graph.addEdge( edgeFactory.create(), "A0", "A3" );

        graph.addVertex( "B0" );
        graph.addEdge( edgeFactory.create(), "B0", "B1" );
        graph.addEdge( edgeFactory.create(), "B0", "B2" );
        graph.addEdge( edgeFactory.create(), "B1", "B4" );
        graph.addEdge( edgeFactory.create(), "B2", "B3" );
        graph.addEdge( edgeFactory.create(), "B2", "B5" );
        graph.addEdge( edgeFactory.create(), "B4", "B6" );
        graph.addEdge( edgeFactory.create(), "B4", "B7" );
        graph.addEdge( edgeFactory.create(), "B3", "B8" );
        graph.addEdge( edgeFactory.create(), "B6", "B9" );

    }

    @SuppressWarnings( "unchecked" )
    final public void displayGraphContent() {

        // create a simple graph for the demo
        graph = new DelegateForest<>();

        createTree();

        layout = new TreeLayout<>( graph );
        collapser = new TreeCollapser();

        radialLayout = new RadialTreeLayout<>( graph );
        radialLayout.setSize( new Dimension( 600, 600 ) );
        vv = new VisualizationViewer<>( layout, new Dimension( 600, 600 ) );
        vv.setBackground( Color.white );
        vv.getRenderContext().setEdgeShapeTransformer( new EdgeShape.Line<>() );
        vv.getRenderContext().setVertexLabelTransformer( new ToStringLabeller() );
        vv.getRenderContext().setVertexShapeTransformer( new ClusterVertexShapeFunction() );
        // add a listener for ToolTips
        vv.setVertexToolTipTransformer( new ToStringLabeller() );
        vv.getRenderContext().setArrowFillPaintTransformer( new ConstantTransformer( Color.lightGray ) );
        rings = new Rings( graph, radialLayout, vv );

        Container content = this;

        final GraphZoomScrollPane panel = new GraphZoomScrollPane( vv );
        content.add( panel );

        final DefaultModalGraphMouse<String, Integer> graphMouse = new DefaultModalGraphMouse<>();

        vv.setGraphMouse( graphMouse );

        JComboBox<Mode> modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener( graphMouse.getModeListener() );
        graphMouse.setMode( ModalGraphMouse.Mode.TRANSFORMING );

        final ScalingControl scaler = new CrossoverScalingControl();

        JButton plus = new JButton( "+" );
        plus.addActionListener( ( ActionEvent e ) -> {
            scaler.scale( vv, 1.1f, vv.getCenter() );
        } );
        JButton minus = new JButton( "-" );
        minus.addActionListener( ( ActionEvent e ) -> {
            scaler.scale( vv, 1 / 1.1f, vv.getCenter() );
        } );

        JToggleButton radial = new JToggleButton( "Radial" );
        radial.addItemListener( ( ItemEvent e ) -> {
            if( e.getStateChange() == ItemEvent.SELECTED ) {
//					layout.setRadial(true);
                vv.setGraphLayout( radialLayout );
                vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
                vv.addPreRenderPaintable( rings );
            } else {
//					layout.setRadial(false);
                vv.setGraphLayout( layout );
                vv.getRenderContext().getMultiLayerTransformer().setToIdentity();
                vv.removePreRenderPaintable( rings );
            }
            vv.repaint();
        } );

        JButton collapse = new JButton( "Collapse" );
        collapse.addActionListener( ( ActionEvent e ) -> {
            Collection<String> picked = new HashSet<>( vv.getPickedVertexState().getPicked() );
            if( picked.size() == 1 ) {
                Object root1 = picked.iterator().next();
                Forest<String, Integer> inGraph = (Forest) layout.getGraph();
                try {
                    collapser.collapse( vv.getGraphLayout(), inGraph, root1 );
                } catch( InstantiationException e1 ) {
                    // TODO Auto-generated catch block
                    LOGGER.log( Level.SEVERE, "InstantiationException", e1 );
                } catch( IllegalAccessException e1 ) {
                    // TODO Auto-generated catch block
                    LOGGER.log( Level.SEVERE, "IllegalAccessException", e1 );
                }
                vv.getPickedVertexState().clear();
                vv.repaint();
            }
        } );

        JButton expand = new JButton( "Expand" );
        expand.addActionListener( ( ActionEvent e ) -> {
            Collection picked = vv.getPickedVertexState().getPicked();
            for( Object v : picked ) {
                if( v instanceof Forest ) {
                    Forest inGraph = (Forest) layout.getGraph();
                    collapser.expand( inGraph, (Forest) v );
                }
                vv.getPickedVertexState().clear();
                vv.repaint();
            }
        } );

        JPanel scaleGrid = new JPanel( new GridLayout( 1, 0 ) );
        scaleGrid.setBorder( BorderFactory.createTitledBorder( "Zoom" ) );

        JPanel controls = new JPanel();
        scaleGrid.add( plus );
        scaleGrid.add( minus );
        controls.add( radial );
        controls.add( scaleGrid );
        controls.add( modeBox );
        controls.add( collapse );
        controls.add( expand );
        content.add( controls, BorderLayout.SOUTH );
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDebugGraphicsOptions(DebugGraphics.BUFFERED_OPTION | DebugGraphics.FLASH_OPTION | DebugGraphics.LOG_OPTION);
        setMinimumSize(new java.awt.Dimension(400, 300));
        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
