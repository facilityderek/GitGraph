/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.jgitgraph.model;

import edu.uci.ics.jung.graph.Graph;
import org.apache.commons.collections15.Transformer;

    /**
     * A demo class that will make vertices larger if they represent
     * a collapsed collection of original vertices
     *
     * @author Tom Nelson
     *
     * @param <V>
     */
    public class ClusterVertexSizeFunction<V> implements Transformer<V, Integer> {

        int size;

        public ClusterVertexSizeFunction( Integer size ) {
            this.size = size;
        }

        @Override
        public Integer transform( V v ) {
            if( v instanceof Graph ) {
                return 30;
            }
            return size;
        }
    }
