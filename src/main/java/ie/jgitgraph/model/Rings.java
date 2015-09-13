/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.jgitgraph.model;

import edu.uci.ics.jung.algorithms.layout.PolarPoint;
import edu.uci.ics.jung.algorithms.layout.RadialTreeLayout;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.visualization.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.*;

/**
 *
 * @author dfitzsimons
 */
public class Rings implements VisualizationServer.Paintable {

    /**
     * the graph
     */
    private Forest<String, Integer> graph;

    Collection<Double> depths;

    /**
     * the visual component and renderer for the graph
     */
    private VisualizationViewer<String, Integer> vv;

    private RadialTreeLayout<String, Integer> radialLayout;

    public Rings( final Forest<String, Integer> graph,final RadialTreeLayout<String, Integer> radialLayout, final VisualizationViewer<String, Integer> vv) {
        this.graph = graph;        
        this.radialLayout = radialLayout;
        this.depths = getDepths();
        this.vv = vv;
    }

    private Collection<Double> getDepths() {
        depths = new HashSet<>();
        Map<String, PolarPoint> polarLocations = radialLayout.getPolarLocations();
        for( String v : graph.getVertices() ) {
            PolarPoint pp = polarLocations.get( v );
            depths.add( pp.getRadius() );
        }
        return depths;
    }

    @Override
    public void paint( Graphics g ) {
        g.setColor( Color.lightGray );

        Graphics2D g2d = (Graphics2D) g;
        Point2D center = radialLayout.getCenter();

        Ellipse2D ellipse = new Ellipse2D.Double();
        for( double d : depths ) {
            ellipse.setFrameFromDiagonal( center.getX() - d, center.getY() - d,
                    center.getX() + d, center.getY() + d );
            Shape shape = vv.getRenderContext().
                    getMultiLayerTransformer().getTransformer( Layer.LAYOUT ).transform( ellipse );
            g2d.draw( shape );
        }
    }

    @Override
    public boolean useTransform() {
        return true;
    }
}
