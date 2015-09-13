/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.jgitgraph.model;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import java.awt.Shape;

    /**
     * a demo class that will create a vertex shape that is either a
     * polygon or star. The number of sides corresponds to the number
     * of vertices that were collapsed into the vertex represented by
     * this shape.
     *
     * @author Tom Nelson
     *
     * @param <V>
     */
    public class ClusterVertexShapeFunction<V> extends EllipseVertexShapeTransformer<V> {

        public ClusterVertexShapeFunction() {
            setSizeTransformer( new ClusterVertexSizeFunction<>( 20 ) );
        }

        @SuppressWarnings( "unchecked" )
        @Override
        public Shape transform( V v ) {
            if( v instanceof Graph ) {
                int size = ( (Graph) v ).getVertexCount();
                if( size < 8 ) {
                    int sides = Math.max( size, 3 );
                    return factory.getRegularPolygon( v, sides );
                } else {
                    return factory.getRegularStar( v, size );
                }
            }
            return super.transform( v );
        }
    }
