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
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.subLayout.TreeCollapser;
import ie.jgitgraph.model.ClusterVertexShapeFunction;
import ie.jgitgraph.model.Rings;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.ConstantTransformer;

/**
 *
 * @author dfitzsimons
 */
public class LogGraphForm extends javax.swing.JPanel {
    private static final long serialVersionUID = 1L;

    /**
     * Logger
     */
    private static final Logger LOGGER = Logger.getLogger( LogGraphForm.class.getName() );
    
    final ScalingControl scaler = new CrossoverScalingControl();
final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
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
    FRLayout layout1;

    TreeCollapser collapser;

    RadialTreeLayout<String, Integer> radialLayout;

    /**
     * Creates new form LogGraphForm
     */
    public LogGraphForm() {
        
                // create a simple graph for the demo
        graph = new DelegateForest<>();

        createTree();

        layout = new TreeLayout<>( graph );
        collapser = new TreeCollapser();

        radialLayout = new RadialTreeLayout<>( graph );
        radialLayout.setSize( new Dimension( 600, 600 ) );
        vv = new VisualizationViewer<>( layout, new Dimension( 600, 600 ) );
        vv.setBackground( Color.white );
        vv.getRenderContext().setEdgeShapeTransformer( new EdgeShape.Line() );
        vv.getRenderContext().setVertexLabelTransformer( new ToStringLabeller() );
        vv.getRenderContext().setVertexShapeTransformer( new ClusterVertexShapeFunction() );
        // add a listener for ToolTips
        vv.setVertexToolTipTransformer( new ToStringLabeller() );
        vv.getRenderContext().setArrowFillPaintTransformer( new ConstantTransformer( Color.lightGray ) );
        rings = new Rings( graph, radialLayout,vv);
               
        
        initComponents();
    }
    
    public void displayGraphContent(){
        
    }

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

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        content = content = new GraphZoomScrollPane( vv );
        controls = new javax.swing.JPanel();
        scaleGrid = new javax.swing.JPanel();
        plus = new javax.swing.JButton();
        minus = new javax.swing.JButton();
        radial = new javax.swing.JToggleButton();
        modeBox = modeBox = graphMouse.getModeComboBox();
        modeBox.addItemListener( graphMouse.getModeListener() );
        graphMouse.setMode( ModalGraphMouse.Mode.TRANSFORMING );
        collapse = new javax.swing.JButton();
        expand = new javax.swing.JButton();

        setLayout(new java.awt.GridLayout(2, 0));
        add(content);

        controls.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        controls.setMinimumSize(new java.awt.Dimension(400, 75));
        controls.setOpaque(false);
        controls.setPreferredSize(new java.awt.Dimension(400, 75));

        scaleGrid.setBorder(javax.swing.BorderFactory.createTitledBorder("Zoom"));
        scaleGrid.setLayout(new java.awt.GridLayout(1, 0));

        plus.setText("+");
        plus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusActionPerformed(evt);
            }
        });
        scaleGrid.add(plus);

        minus.setText("-");
        minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusActionPerformed(evt);
            }
        });
        scaleGrid.add(minus);

        radial.setText("Radial");
        radial.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                radialItemStateChanged(evt);
            }
        });

        modeBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        collapse.setText("Collapse");
        collapse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collapseActionPerformed(evt);
            }
        });

        expand.setText("Expand");
        expand.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expandActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlsLayout = new javax.swing.GroupLayout(controls);
        controls.setLayout(controlsLayout);
        controlsLayout.setHorizontalGroup(
            controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(radial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(modeBox, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scaleGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(collapse)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(expand)
                .addContainerGap(290, Short.MAX_VALUE))
        );
        controlsLayout.setVerticalGroup(
            controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(controlsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(radial)
                    .addComponent(modeBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scaleGrid, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(collapse)
                    .addComponent(expand))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        add(controls);
    }// </editor-fold>//GEN-END:initComponents

    private void radialItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_radialItemStateChanged
        if( evt.getStateChange() == ItemEvent.SELECTED ) {
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

    }//GEN-LAST:event_radialItemStateChanged

    private void plusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusActionPerformed
        scaler.scale( vv, 1.1f, vv.getCenter() );
    }//GEN-LAST:event_plusActionPerformed

    private void minusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusActionPerformed
        scaler.scale( vv, 1 / 1.1f, vv.getCenter() );
    }//GEN-LAST:event_minusActionPerformed

    private void collapseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collapseActionPerformed
        Collection<String> picked = new HashSet<>( vv.getPickedVertexState().getPicked() );
                if( picked.size() == 1 ) {
                    Object root = picked.iterator().next();
                    Forest inGraph = (Forest) layout.getGraph();

                    try {
                        collapser.collapse( vv.getGraphLayout(), inGraph, root );
                    } catch( InstantiationException e1 ) {
                        // TODO Auto-generated catch block
                        LOGGER.log(Level.SEVERE,"InstantiationException",e1);
                    } catch( IllegalAccessException e1 ) {
                        // TODO Auto-generated catch block
                        LOGGER.log(Level.SEVERE,"IllegalAccessException",e1);
                    }

                    vv.getPickedVertexState().clear();
                    vv.repaint();
                }
    }//GEN-LAST:event_collapseActionPerformed

    private void expandActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expandActionPerformed
        Collection picked = vv.getPickedVertexState().getPicked();
                for( Object v : picked ) {
                    if( v instanceof Forest ) {
                        Forest inGraph = (Forest) layout.getGraph();
                        collapser.expand( inGraph, (Forest) v );
                    }
                    vv.getPickedVertexState().clear();
                    vv.repaint();
                }
    }//GEN-LAST:event_expandActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton collapse;
    private javax.swing.JPanel content;
    private javax.swing.JPanel controls;
    private javax.swing.JButton expand;
    private javax.swing.JButton minus;
    private javax.swing.JComboBox modeBox;
    private javax.swing.JButton plus;
    private javax.swing.JToggleButton radial;
    private javax.swing.JPanel scaleGrid;
    // End of variables declaration//GEN-END:variables
}
